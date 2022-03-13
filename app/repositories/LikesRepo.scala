package repositories

import mapping.TableMapping
import models.Likes
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global


import javax.inject.Inject

class LikesRepo @Inject()(tableMapping: TableMapping, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{
  val likes = tableMapping.likes
  import  profile.api._

  def insert(like: Likes): Unit ={
    db.run(likes += like)
  }
  def delete(userId: Long, postId: Long): Unit ={
    db.run(likes.filter(_.userFk == userId ).filter(_.postFk == postId).delete) map{ _ > 0}
  }
}
