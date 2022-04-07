package repositories
import akka.actor.Status.Success
import dto.{UserDTO, UserResponseDTO}
import mapping.TableMapping
import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.sql.SQLIntegrityConstraintViolationException
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class UserRepo @Inject()(tableMapping: TableMapping,
                         protected val dbConfigProvider: DatabaseConfigProvider)
                          extends HasDatabaseConfigProvider[JdbcProfile] {
  import  profile.api._
  private val users = tableMapping.users

  def insert(user: User) = {
    db.run(users += user)
      .recover{
      case _: SQLIntegrityConstraintViolationException => throw new Exception("Username/email not unique")
      case ex: Exception => throw ex
    }
  }
  def findById(id: Long) ={
    db.run((for (user <- users if user.id ===id) yield  user).result.headOption)
  }

  def searchUser(name: String, lastname: String)={
    db.run((for(user <- users if user.name === name && user.lastname===lastname ) yield user).result)
  }

  def delete(id: Long)={
    db.run(users.filter(_.id === id).delete) map {
      _ > 0
    }
  }

  def updateProfilePicture(id: Long , profilePicture: String) ={
    val query = for (user <- users if user.id === id) yield user.profilePicture
    db.run(query.update(profilePicture)) map {_ > 0}
  }
  def updateInfo(id: Long , userDto: UserDTO) ={
    db.run(users.filter(user => user.id === id)
      .map(u=>(u.username,u.email,u.name,u.lastname,u.dateOfBirth,u.about))
      .update(userDto.username, userDto.email,userDto.name,userDto.lastname, userDto.dateOfBirth,userDto.about))
      .recover{
        case _: SQLIntegrityConstraintViolationException => throw new Exception("Username/email not unique")
        case ex: Exception => throw ex
      }
  }
}
