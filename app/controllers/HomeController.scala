package controllers

import dto.Tables
import dto.Tables.{Labelmemo, _}
import javax.inject._
import play.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{Action, _}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext
import scala.util.Random

@Singleton
class HomeController @Inject()(
                                protected val dbConfigProvider: DatabaseConfigProvider,
                                cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc)
    with HasDatabaseConfigProvider[PostgresProfile] {

  def index(): Action[AnyContent] = Action.async { implicit request =>
    val action = User.result
    db.run(action)
      .map(
        users => Ok(users.toList.mkString(","))
      )
      .recover {
        case exception: Exception => InternalServerError(exception.getMessage)
      }
  }

  def indexWithRelatedTable(): Action[AnyContent] = Action.async {
    implicit request =>

      val labels = Labelmemo join Label on (_.tagId === _.id)
      val labelsAndMemos = Memo joinLeft labels on (_.id === _._1.memoId)
      val action = User joinLeft labelsAndMemos on (_.id === _._1.userId)

      db.run(action.result).map {
        result => {
          Ok(
            Json.obj(
              "users" -> (
                for {
                  (user, memos) <- result.groupBy(_._1)

                  memoAndLabels = for {
                    (maybeMemo, labels) <- memos.groupBy(_._2.map(_._1))
                  } yield for {
                    memo <- maybeMemo
                  } yield Json.obj(
                    "memoId" -> memo.id,
                    "memoContent" -> memo.content,
                    "labels" -> labels.map {
                      case (_, maybeMemoAndLabel) =>
                        maybeMemoAndLabel.flatMap(_._2.map {
                          case (_, label) => Json.obj(
                            "labelId" -> label.id,
                            "labelName" -> label.name
                          )
                        })
                    }
                  )

                } yield Json.obj(
                  "userId" -> user.id,
                  "userName" -> user.name,
                  "memo" -> memoAndLabels.flatten.toSeq
                )).toSeq
            )
          )
        }
      }
  }

  def count(): Action[AnyContent] = Action.async {
    val action = User.result
    db.run(action)
      .map(
        users => Ok(users.size.toString)
      )
      .recover {
        case exception: Exception => InternalServerError(exception.getMessage)
      }
  }

  def add(): Action[AnyContent] = Action.async {
    val action = User returning User
      .map(user => (user.id, user.name)) += UserRow(
      0,
      Random.alphanumeric.take(7).mkString)

    db.run(action)
      .map(
        tuple =>
          Ok("ID:" + tuple._1.toString + "ユーザー名:" + tuple._2 + "のユーザーが作成されました。")
      )
      .recover {
        case exception: Exception => InternalServerError(exception.getMessage)
      }
  }

  def addMemoAndLabel(userId: Int): Action[AnyContent] = Action.async {
    val action =
      for {
        memo <- Memo returning Memo.map(memo => memo.id) into (
          (memo,
           id) => memo.copy(id = id)) += MemoRow(
          0,
          "todo:" + Random.alphanumeric.take(7).mkString,
          userId)
        label <- Label returning Label.map(label => label.id) into (
          (label,
           id) => label.copy(id = id)) += LabelRow(
          0,
          "label:" + Random.alphanumeric.take(7).mkString)
        _ <- Labelmemo += LabelmemoRow(0, memo.id, label.id)
      } yield (memo, label)

    db.run(action.transactionally)
      .map(
        tuple =>
          Ok(
            "ID:" + tuple._1.id.toString + " ユーザーID:" + tuple._1.userId.toString + " コンテンツ:" + tuple._1.content + "のメモが作成されました。" + "\n" + "ID:" + tuple._2.id.toString + " コンテンツ:" + tuple._2.name + "のラベルが作成されました。")
      )
      .recover {
        case exception: Exception => InternalServerError(exception.getMessage)
      }
  }

  def delete(userId: Int): Action[AnyContent] = Action.async {
    implicit request =>
      //削除するユーザーを取得
      val deleteUser = User.filter(_.id === userId.bind)
      //削除するユーザーに関連するメモを取得
      val deleteMemo  = Memo.filter(_.userId in deleteUser.map(_.id))

      //削除するユーザーに関連するメモに関連するラベルメモ(中間テーブル)を取得
      val deleteLabelMemos  = Labelmemo.filter(_.memoId in deleteMemo.map(_.id)).result

      db.run(
        //削除するラベルメモをもとに、LabelとLabelMemoテーブルを削除
        // 一旦resultからmapしないと先にlabelMemoが削除されて関連するラベルが削除されない。
        deleteLabelMemos.flatMap(
          //取得したラベルメモをもとに、LabelとLabelMemoテーブルを削除
          result =>(Labelmemo.filter(_.id inSet result.map(_.id)).delete >> Label.filter(_.id  inSet result.map(_.tagId)).delete)
        ) >>
        deleteMemo.delete >> //これはAndThen構文。順番に実行し最後の結果のみ返す。
        deleteUser.delete
      ).map(_ => Ok("ユーザーを削除しました。"))

  }
}
