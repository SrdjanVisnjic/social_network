package controllers

import models.{Likes, UserFriend}
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc.{BaseController, ControllerComponents}
import services.LikesService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LikeController @Inject()(val controllerComponents: ControllerComponents, val likesService : LikesService) extends BaseController {

  def like = Action.async {
    implicit request =>
      request.body.asJson.get.validate[Likes] match {
        case JsSuccess(like, _) => likesService.like(like)
          Future(Ok("Post liked"))
        case JsError(errors) => Future(BadRequest("Could not like post"))
      }
  }
  def unlike = Action.async{
    implicit request =>
      request.body.asJson.get.validate[Likes] match {
        case JsSuccess(like, _) => likesService.unlike(like)
          Future(Ok("Post unliked"))
        case JsError(errors) => Future(BadRequest("Could not unlike post"))
      }
  }

}