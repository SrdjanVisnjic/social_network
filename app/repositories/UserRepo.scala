package repositories
import akka.actor.Status.Success
import dto.UserDTO
import mapping.TableMapping
import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class UserRepo @Inject()(tableMapping: TableMapping, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import  profile.api._
  private val users = tableMapping.users

  def insert(user: User) = {
    val insertQuery = (users returning users.map(_.id)).insertOrUpdate(user)
    dbConfig.db.run(insertQuery)
  }
  def findById(id: Long) ={
    db.run((for (user <- users if user.id ===id) yield  user).result.headOption)
  }
  def delete(id: Long)={
    db.run(users.filter(_.id === id).delete) map {
      _ > 0
    }
  }
  def searchUser(name: String, lastname: String)={
    db.run((for(user <- users if user.name === name && user.lastname===lastname ) yield user).result)
  }

  def updateProfilePicture(id: Long , profilePicture: String) ={
    val query = for (user <- users if user.id === id) yield user.profilePicture
    db.run(query.update(profilePicture)) map {_ > 0}
  }
  def updateInfo(id: Long , userDto: UserDTO) ={
    db.run(users.filter(user => user.id === id)
      .map(u=>(u.username,u.email,u.name,u.lastname,u.dateOfBirth,u.about))
      .update(userDto.username, userDto.email,userDto.name,userDto.lastname, userDto.dateOfBirth,userDto.about))
  }
}
