// Versions
lazy val scalaV = "2.12.3"
lazy val playV = "2.6.3"
lazy val scalaJsV = "0.9.2"

lazy val cragmapper = (project in file("."))
  .aggregate(frontend, frontendBrowser)



lazy val frontend = (project in file("frontend"))
  .settings(
  scalaVersion := scalaV,
  scalaJSProjects := Seq(frontendBrowser),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  routesGenerator := InjectedRoutesGenerator,
  libraryDependencies ++= Seq(
    guice,
    "com.vmunier" %% "scalajs-scripts" % "1.1.1",
    "com.typesafe.play" %% "play-json" % playV,
    "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3" exclude("org.webjars", "jquery"),
    "org.webjars" % "bootstrap" % "3.3.7-1" exclude("org.webjars", "jquery"),
    "org.webjars" % "Snap.svg" % "0.3.0",
    specs2 % Test
  )).enablePlugins(PlayScala).
  dependsOn(frontendSharedJvm)



lazy val scalajs_gmaps_212fork = RootProject(uri("https://github.com/cfraz89/scalajs-google-maps.git"))
lazy val frontendBrowser = (project in file("frontendBrowser"))
  .settings(
  scalaVersion := scalaV,
  scalaJSUseMainModuleInitializer := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % scalaJsV,
    "be.doeraene" %%% "scalajs-jquery" % scalaJsV,
    "com.typesafe.play" %%% "play-json" % playV
  ),
  jsDependencies +=
    "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
  jsDependencies += RuntimeDOM
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(frontendSharedJs, scalajs_gmaps_212fork)



lazy val frontendShared = (crossProject.crossType(CrossType.Pure) in file("frontendShared")).
  settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % playV,
      "com.typesafe.play" %%% "play-json" % playV
    )).
  jsConfigure(_ enablePlugins ScalaJSWeb)
lazy val frontendSharedJvm = frontendShared.jvm
lazy val frontendSharedJs = frontendShared.js



resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

// loads the server project at sbt startup
onLoad in Global := (Command.process("project frontend", _: State)) compose (onLoad in Global).value
