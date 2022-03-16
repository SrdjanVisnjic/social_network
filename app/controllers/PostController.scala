package controllers

import models.{Post, User}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.PostService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PostController @Inject()(val controllerComponents: ControllerComponents, val postService : PostService) extends BaseController{
  def create: Action[AnyContent]= Action.async{ implicit request => request.body.asJson.get.validate[Post] match {
    case JsSuccess(post,_) => postService.createPost(post)
      Future(Ok(Json.toJson(post)))
    case JsError(err) => Future(BadRequest("Invalid data"))
  }}
}
