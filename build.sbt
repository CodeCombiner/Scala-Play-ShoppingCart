name := """PlayShoppingCartWithMySqlAndRedis"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "org.slf4j"           % "slf4j-nop"  % "1.6.4",
  "net.debasishg" %% "redisclient" % "3.0",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "mysql" % "mysql-connector-java" % "5.1.21",
  "com.zaxxer" % "HikariCP" % "2.4.1"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

EclipseKeys.skipParents in ThisBuild := false

EclipseKeys.preTasks := Seq(compile in Compile)

fork in run := false