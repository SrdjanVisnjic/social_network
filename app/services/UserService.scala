package services

import models.User
import play.api.libs.json._
import repositories.UserRepo

import javax.inject.Inject

class UserService @Inject()(userRepo: UserRepo) {
  def createUser(user: User): Unit ={
    val json : JsValue
    json.validate[User]match{
      case JsSuccess(user ,_)=>{
        val _: User = user
        userRepo.insert(user)
      }
      case e: JsError => {
        println("Error in user input")
      }
    }
  }
}
