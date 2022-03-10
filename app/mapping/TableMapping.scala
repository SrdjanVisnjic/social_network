 package mapping
 import javax.inject.Inject
 import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
 import slick.jdbc.JdbcProfile
 import scala.concurrent.ExecutionContext
 import slick.sql.SqlProfile.ColumnOption.SqlType


 class TableMapping @Inject()(
                              protected val dbConfigProvider: DatabaseConfigProvider
                            )(
                              implicit executionContext: ExecutionContext
                            ) extends HasDatabaseConfigProvider[JdbcProfile]{

  private class UserTable(tag:Tag) extends Table[User](tag, "user"){

    import profile.api._

    def id = column[Long]( "id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("userName")
    def password = column[String]("password")
    def email = column[String]("email")
    def name = column[String]("firstName")
    def lastname = column[String]("lastName")
    def dateOfBirth = column[String]("dateOfBirth")
    def about = column[String]("about")
    def profilePicture = column[String]("profilePicture")

    def userNameIndex = index("uq_userName", username, true)
    def emailIndex = index("uq_email", email, true)

    def * =(id,username,password,email,name,lastname,dateOfBirth,about,profilePicture)<> (User.tupled, User.unapply)
  }
  val users = TableQuery[UserTable]

  private class PostTable(tag: Tag) extends  Table[Post](tag, "post"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def messsage = column[String]("message")
    def createdAt = column[String]("createdAt")
    def editedAt = column[String]("editedAt")
    def userId = column[Long]("userId")
    def userFk = foreignKey("fk_post_user", userId, users)(_.id)
  }
}

