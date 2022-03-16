package services

import models.Post
import repositories.PostRepo

import javax.inject.Inject

class PostService @Inject()(postRepo: PostRepo){
  def createPost(post : Post) = {
    postRepo.insert(post)
  }
}
