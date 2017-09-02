
lazy val scalaV = "2.12.3"

lazy val scalajs_gmaps_212fork = RootProject(uri("https://github.com/cfraz89/scalajs-google-maps.git"))

lazy val frontend = (project in file("frontend"))
  .settings(
  scalaVersion := scalaV,
  scalaJSProjects := Seq(frontend_browser),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile <<= (compile in Compile) dependsOn scalaJSPipeline,
  routesGenerator := InjectedRoutesGenerator,
  libraryDependencies ++= Seq(
    guice,
    "com.vmunier" %% "scalajs-scripts" % "1.1.1",
    "com.typesafe.play" %% "play-json" % "2.6.3",
    "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3" exclude("org.webjars", "jquery"),
    "org.webjars" % "bootstrap" % "3.3.7-1" exclude("org.webjars", "jquery"),
    "org.webjars" % "Snap.svg" % "0.3.0",
    specs2 % Test
  )).enablePlugins(PlayScala).
  dependsOn(sharedJvm)

lazy val frontend_browser = (project in file("frontend_browser"))
  .settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.2",
    "be.doeraene" %%% "scalajs-jquery" % "0.9.2",
    "com.typesafe.play" %%% "play-json" % "2.6.3",
  ),
  jsDependencies +=
    "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
  jsDependencies += RuntimeDOM
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs, scalajs_gmaps_212fork)


lazy val cragmapper = (project in file("."))
  .aggregate(frontend, frontend_browser)


lazy val frontend_shared = (crossProject.crossType(CrossType.Pure) in file("frontend_shared")).
  settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.6.3",
      "com.typesafe.play" %%% "play-json" % "2.6.3"
    )).
  jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedJvm = frontend_shared.jvm
lazy val sharedJs = frontend_shared.js

//resolvers in ThisBuild += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

// loads the server project at sbt startup
onLoad in Global := (Command.process("project frontend", _: State)) compose (onLoad in Global).value

libraryDependencies ++= Seq(
  "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3"
)

routesGenerator in ThisBuild := InjectedRoutesGenerator