package repositories

import dto.PostDTO
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
  private val userfriends = tableMapping.userfriends
  private val users = tableMapping.users
  private val likes = tableMapping.likes

  def insert(post : Post) ={
    db.run((posts returning posts.map(_.id)).insertOrUpdate(post))
  }

  def findById(id: Long) ={
    db.run((for (post <- posts if post.id === id) yield  post).result.headOption)
  }

  def delete(id: Long) ={
    db.run(posts.filter(_.id === id).delete) map {_ > 0}
  }

  def edit(id: Long, postdto: PostDTO, editedAt: String) ={
    val query = for(post <- posts if post.id === id) yield (post.messsage,post.editedAt)
    db.run(query.update(postdto.message,editedAt)) map {_>0}
  }
  def getPostsByUser(userId : Long) ={
    db.run((for{
      friendship <- userfriends if friendship.status > 0 && (friendship.sourceId === userId || friendship.targetId ===userId)
      user <- users if(!(user.id === userId) && ((user.id ===friendship.sourceId) || (user.id === friendship.targetId)))
      post <- posts if post.userId === user.id
      likeCount = likes.filter(_.postId===post.id).length
    } yield(post.id, post.messsage, post.createdAt, post.editedAt,user.username, user.name, user.lastname, user.id,likeCount)).result)
  }
}
