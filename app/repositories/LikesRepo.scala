package repositories

import mapping.TableMapping
import models.Likes
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.sql.SQLIntegrityConstraintViolationException
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject

class LikesRepo @Inject()(tableMapping: TableMapping, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{
  import  profile.api._
  private val likes = tableMapping.likes
  private val users = tableMapping.users



  def insert(userId:Long, postId:Long) ={
    db.run(likes += Likes(userId,postId)).map(_=> "Post liked").recover{
      case _: SQLIntegrityConstraintViolationException => throw new Exception("Already liked")
      case ex: Exception => throw ex
    }
  }
  def delete(userId:Long, postId:Long) ={
    db.run(likes.filter(like => like.userId===userId &&like.postId===postId).delete)
      .map(result => {if (result == 1) "Post disliked" else throw new Exception("This post wasnt liked")}).recover{
      case ex: Exception => throw ex
    }
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
