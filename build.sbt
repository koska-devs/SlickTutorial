import slick.codegen.SourceCodeGenerator

name := """slick_tutorial"""
organization := "com.example"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.8"



libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test


resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"


libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.0",
  "org.postgresql" % "postgresql" % "42.2.5"
)


//以下追加
lazy val codegen = taskKey[Unit]("generate slick table code")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    codegen := {
      SourceCodeGenerator.main(
        Array(
          "slick.jdbc.PostgresProfile",
          "org.postgresql.Driver",
          "jdbc:postgresql://localhost:5432/slick_db_development",
          "app/infrastructure",
          "dto",
          "postgres",
          "password"
        )
      )
    }
  )