package controllers

import models.{Likes, UserFriend}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{BaseController, ControllerComponents, Result}
import services.LikesService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LikeController @Inject()(val controllerComponents: ControllerComponents, val likesService : LikesService) extends BaseController {

  def like(userId: Long, postId: Long) = Action.async {
    implicit request =>
      likesService.like(userId, postId).map(_ => Ok)
        .recover{ case _: Exception => BadRequest}
    }
  def unlike(userId: Long, postId: Long) = Action.async{
    implicit request =>
      likesService.unlike(userId, postId).map(_ => Ok)
        .recover{ case _: Exception => BadRequest}
  }

}