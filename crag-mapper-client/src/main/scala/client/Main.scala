package org.github.andrewrembrandt.cragmapper
package client

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g, literal => lit, newInstance => jsnew}

import upickle.default._

import google.maps.Data.Feature
import google.maps.LatLng

case class Crag(name: String, lat: Double, lng: Double)

object Main extends js.JSApp {
  def main(): Unit = {
    initMap()
  }

  def initMap() = {

    val crags = read[List[Crag]](dom.document.getElementById("crags").innerHTML)

    val mapCanvas = dom.document.getElementById("map_canvas")

    val mapOpts = google.maps.MapOptions(
      center = new LatLng(crags(0).lat, crags(0).lng),
      zoom = 13,
      mapTypeId = google.maps.MapTypeId.ROADMAP
    )

    val googleMap = new google.maps.Map(mapCanvas, mapOpts)

    val markers = crags.foreach(c => new google.maps.Marker(google.maps.MarkerOptions(
        map = googleMap,
        position = new google.maps.LatLng(c.lat, c.lng),
        title = c.name
      )))
  }
}