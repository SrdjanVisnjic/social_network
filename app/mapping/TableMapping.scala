 package mapping
 import models.{Likes, Post, User, UserFriend}

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
   import profile.api._
   private class UserTable(tag:Tag) extends Table[User](tag, "user"){


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

    def userIndex = index("idx_post_user", userId, false)

    def * =(id,userId,messsage,createdAt,editedAt)<>(Post.tupled, Post.unapply)
  }
 val posts = TableQuery[PostTable]

 private class LikesTable(tag: Tag) extends Table[Likes](tag, "likes"){
   def userId = column[Long]("userId")
   def postId = column[Long]("postId")

   def userFk = foreignKey("fk_likes_user", userId, users)(_.id)
   def postFk = foreignKey("fk_likes_post", postId, posts)(_.id)

   def userIndex = index("idx_like_user", userId, false)
   def postIndex = index("idx_like_post",postId, false)

   def * = (userId,postId)<>(Likes.tupled, Likes.unapply)
 }
 val likes = TableQuery[LikesTable]

 private class UserFriendTable(tag: Tag) extends Table[UserFriend](tag, "userFriend"){
   def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
   def sourceId = column[Long]("sourceId")
   def targetId = column[Long]("targetId")
   def createdAt = column[String]("createdAt")
   def status = column[Int]("status")

   def sourceFk = foreignKey("fk_friend_source", sourceId, users)(_.id)
   def targetFk = foreignKey("fk_friend_target", targetId, users)(_.id)

   def sourceIndex = index("idx_friend_source", sourceId, false)
   def targetIndex = index("idx_friend_target", targetId, false)

   def * = (id,sourceId,targetId,createdAt,status)<>(UserFriend.tupled, UserFriend.unapply)
 }
 val userfriends = TableQuery[UserFriendTable]
}

