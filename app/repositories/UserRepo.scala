package repositories
import mapping.TableMapping
import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.Database
import slick.dbio.DBIO
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

import javax.inject.Inject

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
}
