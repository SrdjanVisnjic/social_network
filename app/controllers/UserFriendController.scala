package controllers

import models.UserFriend
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{BaseController, ControllerComponents}
import services.UserFriendService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class UserFriendController @Inject()(val controllerComponents: ControllerComponents, val userFriendService : UserFriendService) extends BaseController{

  def create= Action.async{
    implicit request =>
      request.body.asJson.get.validate[UserFriend] match {
    case JsSuccess(friendship, _) =>
      userFriendService.sendRequest(friendship).map{
        case _ => Ok("Request sent")
      }.recover{case ex => BadRequest("Users are already friends")}
    case JsError(errors) => Future(BadRequest("Request not sent"))
  }}
  def accept(friendshipId : Long) = Action.async{
    implicit request =>
      userFriendService.acceptRequest(friendshipId).map{
      case true => Ok("Request accepted")
      case _ => BadRequest("Could not accept request")
    }
  }
  def reject(friendshipId : Long) = Action.async{
    implicit request =>
      userFriendService.rejectRequest(friendshipId).map{
      case true => Ok("Request rejected")
      case _ => BadRequest("Could not reject request")
    }
  }
  def getFriendRequests(userId: Long)= Action.async{
    implicit request =>
      val listOfReqs = userFriendService.getFriendRequests(userId)
      listOfReqs.map(seq => Ok(Json.toJson(seq)))
  }
  def getFriends(userId: Long)= Action.async{
    implicit request =>
      val listOfFriends = userFriendService.getFriends(userId)
      listOfFriends.map(seq => Ok(Json.toJson(seq)))
  }
}
