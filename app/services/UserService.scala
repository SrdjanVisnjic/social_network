package services

import dto.UserDTO
import models.User
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
    userRepo.updateInfo(id, about)
  }
  def updateProfilePic(id: Long, pfp: String) ={
    userRepo.updateProfilePicture(id,pfp)
  }
  def searchUser(name:String,lastname:String)={
    userRepo.searchUser(name, lastname)
  }
}
