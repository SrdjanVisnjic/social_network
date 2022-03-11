package models

case class User(id: Long, username: String, password: String, email: String, name: String, lastname: String, dateOfBirth: String, about: String, profilePicture: String)
