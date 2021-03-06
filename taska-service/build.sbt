import sbt.Keys.scalaVersion

name := "taska-service"

val AkkaVersion = "2.6.10"
val AkkaPersistenceJdbc = "4.0.0"
val AkkaProjectionVersion = "1.0.0"
val LogbackVersion = "1.2.3"
val SlickVersion = "3.3.3"
val PostgresqlVersion = "42.2.18"
val SpringVersion = "5.3.2"
val ScalaTestVersion = "3.2.3"
val ScalaCheckVersion = "1.15.2"
val FlywayVersion = "7.4.0"
val H2Version = "1.4.200"
val ScalaLoggingVersion = "3.9.2"

enablePlugins(JavaServerAppPackaging)

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value / "scalapb"
)

PB.protoSources in Compile := Seq(baseDirectory.value / "../taska-proto")
mainClass in Compile := Some("taska.TaskaApp")

dockerBaseImage := "openjdk:jre-slim"

inThisBuild(
  List(
    fork := true,
    version := "1.0",
    scalaVersion := "2.13.4",
    scalacOptions ++= Seq(
      "-Xfatal-warnings",
      "-deprecation",
      "-Ywarn-unused"
    ),
    semanticdbEnabled := true,
    semanticdbEnabled := true,
    scalafixScalaBinaryVersion := "2.13",
    scalafixOnCompile := true,
    scalafmtOnCompile := true
  )
)

libraryDependencies ++= Seq(
  // Akka
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-serialization-jackson" % AkkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
  // Akka Persistence
  "com.typesafe.akka" %% "akka-persistence-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query" % AkkaVersion,
  "com.lightbend.akka" %% "akka-persistence-jdbc" % AkkaPersistenceJdbc,
  // Akka Projection
  "com.lightbend.akka" %% "akka-projection-eventsourced" % AkkaProjectionVersion,
  "com.lightbend.akka" %% "akka-projection-core" % AkkaProjectionVersion,
  "com.lightbend.akka" %% "akka-projection-slick" % AkkaProjectionVersion,
  // DB
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
  "org.postgresql" % "postgresql" % PostgresqlVersion,
  "org.flywaydb" % "flyway-core" % FlywayVersion,
  "com.h2database" % "h2" % H2Version,
  // Spring
  "org.springframework" % "spring-context" % SpringVersion,
  // Logging
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % LogbackVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % ScalaLoggingVersion,
  // Proto
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  // Grpc
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "io.grpc" % "grpc-services" % scalapb.compiler.Version.grpcJavaVersion,
  // Test
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-persistence-testkit" % AkkaVersion % Test,
  "org.scalatest" %% "scalatest" % ScalaTestVersion % Test,
  "org.scalacheck" %% "scalacheck" % ScalaCheckVersion % Test
)
