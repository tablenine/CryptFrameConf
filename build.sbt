
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.11.8",
      version      := "0.1.1-SNAPSHOT"
    )),
    name := "CryptFrameConf",
    libraryDependencies ++= Seq (
    )
  )
