enablePlugins(ScalaJSPlugin)

name:= "crag-mapper"
version:= "0.1"
scalaVersion:= "2.11.8"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test"
libraryDependencies +=  "org.specs2" %% "specs2-core" % "3.7.2" % "test"

//resolvers += Resolver.jcenterRepo
//libraryDependencies += "com.hazelcast" %% "hazelcast-scala" % "latest-integration" withSources()
//
//resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
//libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0"

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "1.1.0"