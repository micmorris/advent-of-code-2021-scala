lazy val root = (project in file(".")).settings(
  inThisBuild(List(organization := "micmorris", scalaVersion := "2.12.15")),
  name := "advent-of-code-2021-scala",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %% "ammonite-ops" % "2.4.0",
    "com.lihaoyi" %% "mainargs" % "0.2.1"
  )
)
