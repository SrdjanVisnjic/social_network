package dto

import play.api.libs.json.Json

case class PostDTO(message: String)
object PostDTO{
  implicit  val dtoFormat = Json.format[PostDTO]
}
