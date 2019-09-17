package controllers

import javax.inject._
import play.api._
import play.api.mvc._


@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("")
  }

  def explore() = Action { implicit request: Request[AnyContent] =>
    Ok("")
  }

  def tutorial() = Action { implicit request: Request[AnyContent] =>
    Ok("")
  }

}
