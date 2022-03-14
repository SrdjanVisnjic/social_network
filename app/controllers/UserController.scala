package controllers
import models.User
import play._
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc.BaseController
import play.api.libs.json._
import play.api.mvc.Results.BadRequest
import play.mvc._
import services.UserService

class UserController extends BaseController {
  def create () = Action { implicit request => request.body.asJson.validate[User] match {
    case JsSuccess(user: User) => UserService.createUser(user)
    case JsError => BadRequest("Bad user input")
  }}
}
