package repositories

import mapping.TableMapping
import models.UserFriend
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class UserFriendRepo @Inject()(tableMapping: TableMapping, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._
  private val friends = tableMapping.userfriends
  private val users = tableMapping.users

  def sendRequest(userFriend: UserFriend) ={
    db.run((friends returning friends.map(_.id)).insertOrUpdate(userFriend))
  }
  def getFriendRequests(userId: Long) ={
    db.run((for (friendship <- friends if friendship.targetId===userId &&friendship.status===0) yield friendship).result)
  }
  def acceptRequest(friendshipId: Long) ={
    val query = for(friend <- friends if friend.id === friendshipId && friend.status===0) yield friend.status
    db.run(query.update(1)) map{_ > 0}
  }
  def rejectRequest(friendshipId: Long)={
    db.run(friends.filter(_.id===friendshipId).delete) map{_>0}
  }
  def getFriends(userId: Long)={
    db.run((for{
      friendship <- friends if friendship.status > 0 && (friendship.sourceId === userId || friendship.targetId ===userId)
      user <- users if(!(user.id === userId) && ((user.id ===friendship.sourceId) || (user.id === friendship.targetId)))
    } yield user).result)
  }
}
