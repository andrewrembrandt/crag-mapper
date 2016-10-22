name:= "crag-mapper"
version in ThisBuild:= "0.1"
scalaVersion in ThisBuild:= "2.11.8"

resolvers in ThisBuild += "Artima Maven Repository" at "http://repo.artima.com/releases"
resolvers in ThisBuild += Resolver.jcenterRepo
resolvers in ThisBuild += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

lazy val cragmapper = (project in file("crag-mapper")).settings(
  libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test",
    "org.specs2" %% "specs2-core" % "3.7.2" % "test",
	"com.hazelcast" %% "hazelcast-scala" % "latest-integration",
	"com.typesafe.akka" % "akka-actor" % "2.0",
	"net.ruippeixotog" %% "scala-scraper" % "1.1.0"
  )
)

lazy val cragmapperclient = (project in file("crag-mapper-client")).settings(
  libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test",
    "org.specs2" %% "specs2-core" % "3.7.2" % "test"
  )
).enablePlugins(ScalaJSPlugin)
