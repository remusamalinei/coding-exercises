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
  settings(commonSettings: _*).
  settings(Seq(
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % "2.4.1",
      "org.scalatra" %% "scalatra-scalate" % "2.4.1",
      "org.scalatra" %% "scalatra-json" % "2.4.1",
      "org.json4s" %% "json4s-jackson" % "3.4.2",

      "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test,
      "org.scalatra" %% "scalatra-scalatest" % "2.4.1" % Test,
      "org.eclipse.jetty" % "jetty-webapp" % "9.3.14.v20161028" % Test,
      "org.slf4j" % "slf4j-log4j12" % "1.7.21" % Test,

      "javax.servlet" % "javax.servlet-api" % "3.1.0" % Provided
    )
  ))

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
