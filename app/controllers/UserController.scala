package controllers
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import models.User
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._
import play.api.mvc.Results.Ok
import services.UserService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserController @Inject()(val controllerComponents: ControllerComponents, val userService: UserService) extends BaseController {
  def create: Action[AnyContent]= Action.async{ implicit request => request.body.asJson.get.validate[User] match {
    case JsSuccess(user,_) => userService.createUser(user)
      Future(Ok(Json.toJson(user)))
    case JsError(err) => Future(BadRequest("Invalid data"))
  }}
}
