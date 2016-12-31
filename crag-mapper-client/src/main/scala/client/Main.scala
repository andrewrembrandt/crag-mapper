package org.github.andrewrembrandt.cragmapper
package client

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g, literal => lit, newInstance => jsnew}

import upickle.default._

case class Crag(name: String, lat: Double, lng: Double)

object Main extends js.JSApp {
  def main(): Unit = {
    initMap()
  }

  def initMap() = {

    val crags = read[List[Crag]](dom.document.getElementById("crags").innerHTML)

    val map_canvas = dom.document.getElementById("map_canvas")
    val map_options = lit(
                center = (jsnew(g.google.maps.LatLng))(crags(0).lat, crags(0).lng),
                zoom = 13,
                mapTypeId = g.google.maps.MapTypeId.ROADMAP)

    val googleMap = (jsnew(g.google.maps.Map))(map_canvas, map_options)

    val markers = crags.foreach(c => (jsnew(g.google.maps.Marker))(
      lit(
        map = googleMap,
        position = (jsnew(g.google.maps.LatLng)(c.lat, c.lng)),
        title = c.name
      )))
  }
}