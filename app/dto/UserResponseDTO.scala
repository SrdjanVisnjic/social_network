package dto

import play.api.libs.json.Json

case class UserResponseDTO(userId : Long,username: String, email: String,name: String,lastname: String,dateOfBirth: String,about: String, profilePicture: String,)
object UserResponseDTO{
  implicit  val userResponseDtoFormat = Json.format[UserResponseDTO]

}