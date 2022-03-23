package services

import models.UserFriend
import repositories.UserFriendRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class UserFriendService @Inject()(userFriendRepo: UserFriendRepo){

  def sendRequest(userFriend: UserFriend) ={
    userFriendRepo.checkIfFriendshipExists(userFriend).flatMap{
      case Some(_) => throw new Exception("Already friends")
      case _ =>  userFriendRepo.sendRequest(userFriend)
    }

  }
  def getFriendRequests(userId:Long)={
    userFriendRepo.getFriendRequests(userId)
  }
  def acceptRequest(friendshipId:Long)={
    userFriendRepo.acceptRequest(friendshipId)
  }
  def rejectRequest(friendshipId:Long)={
    userFriendRepo.rejectRequest(friendshipId)
  }
  def getFriends(userId:Long)={
    userFriendRepo.getFriends(userId)
  }
}
