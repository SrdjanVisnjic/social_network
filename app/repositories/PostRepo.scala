package repositories

import mapping.TableMapping
import models.Post
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import scala.concurrent.Future

class PostRepo @Inject()(tableMapping: TableMapping, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{
  import  profile.api._
  private val posts = tableMapping.posts


  def insert(post : Post) ={
    db.run((posts returning posts.map(_.id)).insertOrUpdate(post))
  }

  def findById(id: Long): Unit ={
    db.run((for (post <- posts if post.id === id) yield  post).result.headOption)
  }

  def delete(id: Long) {
    db.run(posts.filter(_.id === id).delete) map {_ > 0}
  }

  def edit(id: Long, message: String, editedAt: String): Unit ={
    val query = for(post <- posts if post.id === id) yield (post.messsage,post.editedAt)
    db.run(query.update(message,editedAt)) map {_>0}
  }
}
