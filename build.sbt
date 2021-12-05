scalacOptions += "-Ypartial-unification"

lazy val monocoleVersion = "2.0.4"
lazy val catsVersion = "2.7.0"
lazy val shapelessVersion = "2.4.0-M1"

lazy val root = (project in file(".")).settings(
  inThisBuild(List(organization := "micmorris", scalaVersion := "2.12.15")),
  name := "advent-of-code-2021-scala",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %% "ammonite-ops" % "2.4.0",
    "com.lihaoyi" %% "mainargs" % "0.2.1",

    //Monocole modules:
    // newer: https://index.scala-lang.org/optics-dev/monocle/monocle-unsafe/2.1.0?target=_2.12
    // older: https://index.scala-lang.org/julien-truffaut/monocle/monocle-core/2.0.4?target=_2.12
    "com.github.julien-truffaut" %% "monocle-core" % monocoleVersion,
    "com.github.julien-truffaut" %% "monocle-macro" % monocoleVersion,

    "org.typelevel" %% "cats-core" % catsVersion,

    "com.chuusai" %% "shapeless" % "2.4.0-M1"
  )
)
