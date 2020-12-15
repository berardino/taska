name := "taska"

version := "1.0"

scalaVersion := "2.13.4"

val AkkaVersion = "2.6.10"
val AkkaPersistenceJdbc = "4.0.0"
val AkkaProjectionVersion = "1.0.0"
val LogbackVersion = "1.2.3"
val SlickVersion = "3.3.3"
val PostgresqlVersion = "42.2.18"
val SpringVersion = "5.3.2"
val ScalaTestVersion = "3.1.0"
val ScalaCheckVersion = "1.15.1"

enablePlugins(AkkaGrpcPlugin, JavaServerAppPackaging)

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-deprecation",
  "-Ywarn-unused"
)

mainClass in Compile := Some("taska.TaskaApp")

dockerBaseImage := "openjdk:jre-slim"

inThisBuild(
  List(
    fork := true,
    semanticdbEnabled := true,
    semanticdbEnabled := true,
    scalafixScalaBinaryVersion := "2.13",
    scalafixOnCompile := true,
    scalafmtOnCompile := true
  )
)

//semanticdbVersion := scalafixSemanticdb.revision

libraryDependencies ++= Seq(
  // Akka
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-serialization-jackson" % AkkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
  // Akka Persistence
  "com.typesafe.akka" %% "akka-persistence-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query" % AkkaVersion,
  "com.lightbend.akka" %% "akka-persistence-jdbc" % AkkaPersistenceJdbc,
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
  "org.postgresql" % "postgresql" % PostgresqlVersion,
  // Akka Projection
  "com.lightbend.akka" %% "akka-projection-eventsourced" % AkkaProjectionVersion,
  "com.lightbend.akka" %% "akka-projection-cassandra" % AkkaProjectionVersion,
  // Spring
  "org.springframework" % "spring-context" % SpringVersion,
  // Logging
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % LogbackVersion,
  // Test
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-persistence-testkit" % AkkaVersion % Test,
  "org.scalatest" %% "scalatest" % ScalaTestVersion % Test,
  "org.scalacheck" %% "scalacheck" % ScalaCheckVersion % Test
)
