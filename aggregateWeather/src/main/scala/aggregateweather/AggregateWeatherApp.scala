package aggregateweather

import akka.actor.ActorSystem

object AggregateWeatherApp extends App {
  implicit val system = ActorSystem("aggregateweather")

  println("Hello World")
}
