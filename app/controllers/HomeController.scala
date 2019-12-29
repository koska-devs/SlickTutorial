package controllers

import dto.Tables._
import javax.inject._
import play.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{Action, _}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext
import scala.util.Random


@Singleton
class HomeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)
                              (implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[PostgresProfile] {

  def index(): Action[AnyContent] = Action.async { implicit request =>
    val action = User.result
    db.run(action)
      .map(
        users => Ok(users.toList.mkString(","))
      )
      .recover{
        case exception: Exception => InternalServerError(exception.getMessage)
      }
  }

  def count(): Action[AnyContent] = Action.async {
    val action = User.result
    db.run(action)
      .map(
        users => Ok(users.size.toString)
      )
      .recover{
        case exception: Exception => InternalServerError(exception.getMessage)
      }
  }

  def add(): Action[AnyContent] = Action.async{
    val action = User returning User.map(user => (user.id,user.name)) += UserRow(0, Random.alphanumeric.take(7).mkString)

    db.run(action).map(
      tuple=> Ok("ID:"+tuple._1.toString+"ユーザー名:"+tuple._2+"のユーザーが作成されました。")
    ).recover{
      case exception: Exception => InternalServerError(exception.getMessage)
    }
  }

  def addMemoAndLabel(userId:Int): Action[AnyContent] = Action.async{
    val action=
      for{
        memo <-Memo returning Memo.map(memo => memo.id) into((memo,id)=> memo.copy(id = id)) += MemoRow(0,"todo:"+Random.alphanumeric.take(7).mkString, userId)
        label <- Label returning Label.map(label =>label.id) into((label,id)=>label.copy(id=id)) += LabelRow(0,"label:"+Random.alphanumeric.take(7).mkString)
        _ <- Labelmemo += LabelmemoRow(0,memo.id,label.id)
      } yield (memo,label)

    db.run(action.transactionally).map(
      tuple=> Ok("ID:"+tuple._1.id.toString+" ユーザーID:"+tuple._1.userId.toString+" コンテンツ:"+tuple._1.content+"のメモが作成されました。"+"\n"+"ID:"+tuple._2.id.toString+" コンテンツ:"+tuple._2.name+"のラベルが作成されました。")
    ).recover{
      case exception: Exception => InternalServerError(exception.getMessage)
    }
  }
}
