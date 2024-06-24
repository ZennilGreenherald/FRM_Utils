val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "FRM Utils",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    // Compile / mainClass := Some("Profiler"),

    javacOptions ++= Seq("-source", "11", "-target", "11")

    // libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
