package services

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
}
