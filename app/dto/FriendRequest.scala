package dto

import play.api.libs.json.Json

case class FriendRequest(id: Long, name: String, lastname: String, username:String, profilePicture: String)
object FriendRequest{
  implicit val frFormat = Json.format[FriendRequest]
}
