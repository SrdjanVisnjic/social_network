package models

import play.api.libs.json.Json

case class Post (id: Long, userId: Long,
                 message: String, createdAt: String,
                 editedAt: String)
object Post {
  implicit val postFormat= Json.format[Post]
}


