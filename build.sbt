
lazy val scalaV = "2.12.3"

lazy val server = (project in file("crag-mapper-server"))
  .settings(
  scalaVersion := scalaV,
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile <<= (compile in Compile) dependsOn scalaJSPipeline,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.0.0",
    "com.lihaoyi" %%% "upickle" % "0.4.3",
    "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3" exclude("org.webjars", "jquery"),
    "org.webjars" % "Snap.svg" % "0.3.0",
    specs2 % Test
  )).enablePlugins(PlayScala).
  dependsOn(sharedJvm)

lazy val client = (project in file("crag-mapper-client"))
  .settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.2",
    "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
    "com.lihaoyi" %%% "upickle" % "0.4.3",
    "io.surfkit" %%% "scalajs-google-maps" % "0.0.2-SNAPSHOT",
    "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3" exclude("org.webjars", "jquery")
  ),
  jsDependencies +=
    "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
  jsDependencies += RuntimeDOM
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)

lazy val root = (project in file("."))
  .aggregate(server, client)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(scalaVersion := scalaV).
  jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

//resolvers in ThisBuild += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

// loads the server project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value

//libraryDependencies ++= Seq(
//  "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3"
//)
