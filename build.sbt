
lazy val scalaV = "2.11.8"

lazy val server = (project in file("crag-mapper-server"))
    .settings(viewSettings: _*)
  .settings(
  scalaVersion := scalaV,
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile <<= (compile in Compile) dependsOn scalaJSPipeline,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.0.0" withJavadoc(),
    "com.lihaoyi" %%% "upickle" % "0.4.3" withJavadoc(),
    "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B3" exclude("org.webjars", "jquery"),
    specs2 % Test
  ),
  // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
  EclipseKeys.preTasks := Seq(compile in Compile)
).enablePlugins(PlayScala).
  dependsOn(sharedJvm)

lazy val client = (project in file("crag-mapper-client"))
  .settings(viewSettings: _*)
  .settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1" withJavadoc(),
    "be.doeraene" %%% "scalajs-jquery" % "0.9.0" withJavadoc(),
    "com.lihaoyi" %%% "upickle" % "0.4.3" withJavadoc(),
    "io.surfkit" %%% "scalajs-google-maps" % "0.0.2-SNAPSHOT" withJavadoc(),
    "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B3" exclude("org.webjars", "jquery")
  ),
  jsDependencies +=
    "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
  jsDependencies +=
    "org.webjars" % "Snap.svg" % "0.4.1" / "0.4.1/snap.svg-min.js",
  jsDependencies += RuntimeDOM
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)

lazy val root = (project in file("."))
  .settings(viewSettings: _*)
  .aggregate(server, client)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(scalaVersion := scalaV).
  jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

resolvers in ThisBuild += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

// loads the server project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value

libraryDependencies ++= Seq(
  "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B3"
)