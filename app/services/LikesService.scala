package services

import models.Likes
import repositories.LikesRepo

import javax.inject.Inject

class LikesService @Inject()(likesRepo: LikesRepo){

  def like(userId:Long, postId:Long)={
    likesRepo.insert(userId, postId)
  }

  def unlike(userId:Long, postId:Long) ={
    likesRepo.delete(userId, postId)
  }
  def getPostLikeNum(postId: Long) ={
    likesRepo.findPostLikes(postId)
  }
}
