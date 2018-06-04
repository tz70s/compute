/*
 * Copyright 2018 Tzu-Chiao Yeh.
 * Licensed under MIT license.
 *
 * Project build up for compute apps.
 * `streams` for flink-based, stream processing apps
 */

name := "compute"

lazy val flink = Seq("org.apache.flink" %% "flink-scala" % "1.5.0",
                     "org.apache.flink" %% "flink-streaming-scala" % "1.5.0")

lazy val commonSettings = Seq(
  organization := "com.tz70s",
  version := "0.1",
  scalaVersion := "2.11.12"
)

lazy val gate = (project in file("streams/gate"))
  .settings(
    commonSettings,
    libraryDependencies ++= flink,
    assemblyJarName in assembly := "streams-gate.jar"
  )
