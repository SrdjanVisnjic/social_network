package models

import play.api.libs.json.Json


case class UserDTO(username: Option[String], email: Option[String],name: Option[String],lastname: Option[String],dateOfBirth: Option[String],about: Option[String])
object UserDTO{
  implicit  val dtoFormat = Json.format[UserDTO]
}
