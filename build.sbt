lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.11.12"
)

val flinkVersion = "1.6.2"
val flinkGroupId = "org.apache.flink"
val flinkScala = flinkGroupId %% "flink-scala" % flinkVersion
val flinkStreamingScala = flinkGroupId %% "flink-streaming-scala" % flinkVersion

val dependencies = Seq(flinkScala, flinkStreamingScala)

lazy val `flink-ex` = (project in file("."))
  .settings(
    commonSettings,
    libraryDependencies ++= dependencies
  )
