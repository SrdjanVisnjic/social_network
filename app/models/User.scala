package models

import play.api.libs.json.Json

case class User(id: Long, username: String, password: String, email: String, name: String, lastname: String, dateOfBirth: String, about: String, profilePicture: String)
object User {
  implicit val userFormat= Json.format[User]
}
