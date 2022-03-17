package services

import dto.PostDTO
import models.Post
import org.joda.time.DateTime
import repositories.PostRepo

import javax.inject.Inject

class PostService @Inject()(postRepo: PostRepo){
  def createPost(post : Post) = {
    postRepo.insert(post)
  }
  def updatePost(id: Long, postDto: PostDTO) ={
    postRepo.edit(id,postDto,DateTime.now().toString("yyyy-MM-dd"))
  }
}
