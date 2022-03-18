package models

import play.api.libs.json.Json

case class UserFriend(id: Long, sourceId: Long, targetId: Long, createdAt: String, status: Int)
object UserFriend{
  implicit val friendFormat = Json.format[UserFriend]
}
