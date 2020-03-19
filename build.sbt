import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "dev.oschrenk"
ThisBuild / organizationName := "oschrenk"

lazy val root = (project in file("."))
  .settings(
    name := "rampart.scala",
    libraryDependencies ++= Seq(
      catsCore,
      scalaTest % Test
    )
  )

licenses += ("ISC", url("http://opensource.org/licenses/ISC"))
