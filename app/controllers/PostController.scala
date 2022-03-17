package controllers

import dto.PostDTO
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
    }
  }

  def updatePost(postId: Long): Action[AnyContent] = Action.async{
    implicit  request =>  request.body.asJson.get.validate[PostDTO] match {
      case JsSuccess(message,_) => postService.updatePost(postId, message)
        Future(Ok("Post updated"))
      case JsError(err)=> Future(BadRequest("Invalid message"))
    }
  }

  def deletePost(postId: Long) = Action.async{
    implicit request => postService.deletePost(postId).map{
      case true => Ok("Post deleted")
      case _ => BadRequest("Error deleting post")
    }
  }

  def allPostsByUser(userId: Long) = Action.async{
    implicit request => val listOfPosts = postService.getPostsByUser(userId)
      listOfPosts.map(seq => Ok(Json.toJson(seq))
      )

  }
}
