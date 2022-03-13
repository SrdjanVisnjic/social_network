package repositories
import mapping.TableMapping
import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class UserRepo @Inject()(tableMapping: TableMapping, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  val users = tableMapping.users
  import profile.api._
  def insert(user: User) = {
   db.run(
     users += user
   )
  }
  def findById(id: Long): Unit ={
    db.run((for (user <- users if user.id ===id) yield  user).result.headOption)
  }
  def delete(id: Long) {
    db.run(users.filter(_.id === id).delete) map {
      _ > 0
    }
  }

  def updateProfilePicture(id: Long , profilePicture: String): Unit ={
    val query = for (user <- users if user.id === id) yield user.profilePicture
    db.run(query.update(profilePicture)) map {_ > 0}
  }
  def updateAbout(id: Long , about: String): Unit ={
    val query = for (user <- users if user.id === id) yield user.about
    db.run(query.update(about)) map {_ > 0}
  }
}
