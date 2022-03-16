package controllers
import models.{User, UserDTO}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._
import services.UserService

import java.nio.file.Paths
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.Configuration

class UserController @Inject()(val controllerComponents: ControllerComponents, val userService: UserService) extends BaseController {

  def create: Action[AnyContent]= Action.async{ implicit request => request.body.asJson.get.validate[User] match {
    case JsSuccess(user,_) => userService.createUser(user)
      Future(Ok(Json.toJson(user)))
    case JsError(err) => Future(BadRequest("Invalid data"))
  }}

  def getById(userId: Long) = Action.async {
    val foundUser = userService.findById(userId)
    foundUser.map {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound
    }
  }
  def update(userId: Long) = Action.async {
  implicit request =>
     request.body.asJson.get.validate[UserDTO] match{
       case JsSuccess(userDto,_)=>userService.update(userId,userDto)
        Future(Ok("User updated"))
       case JsError(err) => Future(BadRequest("Error updating user"))
     }
  }

    def updateProfilePicture(userId:Long) = Action.async(parse.multipartFormData){
      implicit request => request.body.file("profilePicture").map{ picture =>
        val filename = Paths.get(picture.filename).getFileName
        val fileSize = picture.fileSize
        val contentType = picture.contentType
        picture.ref.copyTo(Paths.get(s"/tmp/picture/$filename"), replace = true)
        userService.updateProfilePic(userId,picture.filename).map{
          case true => Ok("File uploaded")
          case _ => BadRequest("File not found")
        }

      }.getOrElse{
        Future(BadRequest("File not found"))
      }
    }

}
