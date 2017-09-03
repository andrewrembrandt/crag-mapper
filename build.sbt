// Versions
lazy val scalaV = "2.12.3"
lazy val playV = "2.6.3"
lazy val scalaJsV = "0.9.2"
lazy val akkaV = "2.5.4"
lazy val akkaHttpV = "10.0.10"

lazy val mountainTripper = (project in file("."))
  .aggregate(aggregateWeather, frontend, frontendBrowser)

val dockerAppPath = "/app/"

lazy val aggregateWeather = (project in file("aggregateWeather")).settings(
  name := "aggregateWeather",
  scalaVersion := scalaV,
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor"  % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-stream-kafka" % "0.17",
    "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test
  )
).dependsOn(shared).enablePlugins(_root_.sbtdocker.DockerPlugin).settings(
  dockerfile in docker := {
    new Dockerfile {
      val mainClassString = (mainClass in Compile).value.get
      val classpath = (fullClasspath in Compile).value
      from("java")
      add(classpath.files, dockerAppPath)
      entryPoint("java", "-cp", s"$dockerAppPath:$dockerAppPath/*", s"$mainClassString")
    }
  },
  imageNames in docker := Seq(
    ImageName("mtaggregateweather/basic:stable"),
    ImageName(namespace = Some(organization.value),
      repository = name.value,
      tag = Some("v" + version.value))
  )
)


lazy val frontend = (project in file("frontend")).settings(
  name := "frontend",
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
  )
).dependsOn(shared).enablePlugins(PlayScala, _root_.sbtdocker.DockerPlugin).settings(
  dockerfile in docker := {
    new Dockerfile {
      val mainClassString = (mainClass in Compile).value.get
      val classpath = (fullClasspath in Compile).value
      from("java")
      add(classpath.files, dockerAppPath)
      entryPoint("java", "-cp", s"$dockerAppPath:$dockerAppPath/*", s"$mainClassString")
    }
  },
  imageNames in docker := Seq(
    ImageName("mtfrontend/basic:stable"),
    ImageName(namespace = Some(organization.value),
      repository = name.value,
      tag = Some("v" + version.value))
  )
)



lazy val scalajs_gmaps_212fork = RootProject(uri("https://github.com/cfraz89/scalajs-google-maps.git"))
lazy val frontendBrowser = (project in file("frontendBrowser")).settings(
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
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).dependsOn(sharedJs, scalajs_gmaps_212fork)



lazy val sharedCross = (crossProject.crossType(CrossType.Pure) in file("shared")).settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % playV,
      "com.typesafe.play" %%% "play-json" % playV
    )).
  jsConfigure(_ enablePlugins ScalaJSWeb)
lazy val shared = sharedCross.jvm
lazy val sharedJs = sharedCross.js



resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

// loads the server project at sbt startup
onLoad in Global := (Command.process("project frontend", _: State)) compose (onLoad in Global).value


def checkImage(imageName: ImageName, expectedOut: String) {
  val process = Process("docker", Seq("run", "--rm", imageName.name))
  val out = process.!!
  if (out.trim != expectedOut) sys.error(s"Unexpected output (${imageName.name}): $out")
}

val check = taskKey[Unit]("Check")

check := {
  checkImage((imageNames in docker in aggregateWeather).value.head, "aggregateWeather")
  checkImage((imageNames in docker in frontend).value.head, "frontend")
}