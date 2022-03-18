package repositories

import mapping.TableMapping
import models.Likes
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global



import javax.inject.Inject

class LikesRepo @Inject()(tableMapping: TableMapping, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{
  import  profile.api._
  private val likes = tableMapping.likes
  private val users = tableMapping.users



  def insert(like: Likes) ={
    db.run(likes += like)
  }
  def delete(like: Likes) ={
    db.run(likes.filter(_.userId === like.userId ).filter(_.postId === like.postId).delete) map{ _ > 0}
  }
  def findPostLikes(postId: Long) ={
    likes.filter(_.postId === postId).length
  }
  def findPostLikers(postId: Long) ={
    db.run((for{like <- likes if like.postId ===postId
               user <- users if user.id === like.userId}
    yield (user.username, user.name, user.lastname)).result)
  }
}
