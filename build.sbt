name := """Social_network"""
organization := "novalite"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.28"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.25"
libraryDependencies +=  "org.webjars.npm" % "github-com-mui-org-material-ui" % "3.9.3"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "novalite.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "novalite.binders._"
