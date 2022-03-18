package models

import play.api.libs.json.Json

case class Likes(userId: Long, postId: Long)
object Likes{
  implicit val likesFormat = Json.format[Likes]
}
