lazy val commonSettings = Seq(
  scalaVersion := "2.11.6",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.5" % Test
  )
)

lazy val exchange = project.
  in(file("exchange")).
  settings(commonSettings: _*)

lazy val maze = project.
  in(file("maze")).
  settings(commonSettings: _*).
  settings(Seq(
    libraryDependencies ++= Seq(
      "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test
    )
  ))

lazy val `prime-numbers` = project.
  in(file("prime-numbers")).
  settings(commonSettings: _*)

lazy val `river-crossing` = project.
  in(file("river-crossing")).
  settings(commonSettings: _*)

lazy val `rock-paper-scissors` = (project in file("rock-paper-scissors")).
  settings(commonSettings: _*).
  settings(Seq(
    version := "1.0.0",
    libraryDependencies ++= Seq(
      "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test
    )
  ))

lazy val `top-ranker` = (project in file("top-ranker")).
  settings(commonSettings: _*).
  settings(Seq(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.4.0",

      "com.typesafe.akka" %% "akka-actor-tests" % "2.3.12" % Test,
      "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test
    )
  ))
