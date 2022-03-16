package services

import models.{User, UserDTO}
import play.api.libs.json._
import repositories.UserRepo

import javax.inject.Inject

class UserService @Inject()(userRepo: UserRepo) {
  def createUser(user: User) = {
    userRepo.insert(user)
  }
  def findById(id : Long) = {
    userRepo.findById(id)
  }
  def update(id: Long, about: UserDTO)= {
    //userRepo.update(id, about)
  }
  def updateProfilePic(id: Long, pfp: String) ={
    userRepo.updateProfilePicture(id,pfp)
  }
}
