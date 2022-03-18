package services

import models.Likes
import repositories.LikesRepo

import javax.inject.Inject

class LikesService @Inject()(likesRepo: LikesRepo){

  def like(like : Likes)={
    likesRepo.insert(like)
  }

  def unlike(like: Likes) ={
    likesRepo.delete(like)
  }
  def getPostLikeNum(postId: Long) ={
    likesRepo.findPostLikes(postId)
  }
}
