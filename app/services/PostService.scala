package services

import dto.{PostDTO, PostResponseDTO}
import models.Post
import org.joda.time.DateTime
import repositories.PostRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class PostService @Inject()(postRepo: PostRepo){
  def createPost(post : Post) = {
    postRepo.insert(post)
  }
  def updatePost(id: Long, postDto: PostDTO) ={
    postRepo.edit(id,postDto,DateTime.now().toString("yyyy-MM-dd"))
  }
  def deletePost(id: Long) ={
    postRepo.delete(id)
  }
  def getPostsByUser(userId: Long)= {
    postRepo.getPostsByUser(userId).map{
      seq => seq.map {
        case (postId, message, createdAt, editedAt, userId, username, name, lastname,likeCount,liked) => PostResponseDTO(postId,message,createdAt,editedAt, userId, username, name, lastname,likeCount,liked)
      }


    }
  }
}
