package repositories

import mapping.TableMapping
import models.Likes
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global


import javax.inject.Inject

class LikesRepo @Inject()(tableMapping: TableMapping, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{
  import  profile.api._
  val likes = tableMapping.likes


  def insert(like: Likes): Unit ={
    db.run(likes += like)
  }
  def delete(userId: Long, postId: Long): Unit ={
    db.run(likes.filter(_.userId === userId ).filter(_.postId === postId).delete) map{ _ > 0}
  }
}
