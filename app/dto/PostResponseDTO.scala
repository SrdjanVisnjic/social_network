package dto

import play.api.libs.json.Json

case class PostResponseDTO( postId: Long, message: String,
                            createdAt: String, editedAt: String,
                            userId: Long, username: String, name: String,
                            lastname: String,
                            likeCount: Int, liked: Boolean)
object PostResponseDTO{
  implicit val postResponseDtoFormat = Json.format[PostResponseDTO]
}

