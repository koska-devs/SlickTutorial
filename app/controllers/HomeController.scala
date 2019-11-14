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

  def add() = Action.async{
    val action = User returning User.map(user => (user.id,user.name)) += UserRow(0, Random.alphanumeric.take(7).mkString)

    db.run(action).map(
      tuple=> Ok("ID:"+tuple._1.toString+"ユーザー名:"+tuple._2+"のユーザーが作成されました。")
    ).recover{
      case exception: Exception => InternalServerError(exception.getMessage)
    }
  }
}
