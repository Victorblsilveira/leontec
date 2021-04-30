name := "leontec"

version := "0.1"

scalaVersion := "2.13.5"

val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3"

libraryDependencies ++= Seq(logback, scalaLogging)