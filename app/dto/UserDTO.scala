package dto

import play.api.libs.json.Json


case class UserDTO(username: String, email: String,
                   name: String,lastname: String,
                   dateOfBirth: String,about: String)
object UserDTO{
  implicit  val dtoFormat = Json.format[UserDTO]
}
