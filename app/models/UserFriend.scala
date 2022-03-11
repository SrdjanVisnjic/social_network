package models

case class UserFriend(id: Long, sourceId: Long, targetId: Long, createdAt: String, status: Int)
