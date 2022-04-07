package services

import dto.{UserDTO, UserResponseDTO}
import models.User
import play.api.libs.json._
import repositories.UserRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

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
  def delete(id: Long)= {
    userRepo.delete(id)
  }
  def updateProfilePic(id: Long, pfp: String) ={
    userRepo.updateProfilePicture(id,pfp)
  }

  def searchUser(name:String,lastname:String)={
    userRepo.searchUser(name, lastname).map{
      seq => seq.map{
        case user => UserResponseDTO(user.id,user.username,
          user.email,user.name,user.lastname,
          user.dateOfBirth,user.about,user.profilePicture)
      }
    }
  }
}
