package org.github.andrewrembrandt.cragmapper
package client

import org.scalajs.dom

import scala.scalajs.js
import upickle.default._
import google.maps.Data.Feature
import google.maps.{LatLng, MarkerImage, Size}
import org.github.andrewrembrandt.cragmapper.shared.models._

import scala.scalajs.js.Dynamic.{global => g, literal => l, newInstance => jsn}
import akauppi.scalajs.snapsvg._

import scala.scalajs.js.annotation.JSExport

object Main extends js.JSApp {
  def main(): Unit = {
    initMap()
    initSvg()
  }

  val tmpSvg: SnapPaper = Snap("#svg")

  def initSvg() = {
    Snap.load("/assets/images/camiconp2.svg", (f: SnapFragment) => onSvgLoad(f))
  }

  def onSvgLoad(frag: SnapFragment): Unit = {
    val text = frag.select("#badge")
    text.attr(l(text = "16"))
    tmpSvg.append(frag)

  }

  def initMap() = {

    val crags = read[List[Crag]](dom.document.getElementById("crags").innerHTML)

    val mapCanvas = dom.document.getElementById("map_canvas")

    val mapOpts = google.maps.MapOptions(
      center = new LatLng(crags(0).lat, crags(0).lng),
      zoom = 13,
      mapTypeId = google.maps.MapTypeId.ROADMAP
    )

    val gmap = new google.maps.Map(mapCanvas, mapOpts)

    val markers = crags.foreach(c =>
      {
        val marker = new google.maps.Marker(google.maps.MarkerOptions(
          map = gmap,
          position = new google.maps.LatLng(c.lat, c.lng),
          title = c.name
        ))
        marker.setIcon(MarkerImage(
          url = "/assets/images/camiconp2.svg",
          size = new Size(52,89),
          scaledSize = new Size(26, 45)))

        def genInfoText(crag: Crag) = s"""<div id="content">
          <h2 id="firstHeading" class="firstHeading">${c.name}</h2>
          <div id="bodyContent">""" +
          (if(c.sport.nonEmpty) s"""<p><b>${c.sport.size} Sport Climbs</b></p>""" else "") +
          (if(c.trad.nonEmpty) s"""<p><b>${c.trad.size} Trad Climbs</b></p>""" else "") +
          (if(c.boulder.nonEmpty) s"""<p><b>${c.boulder.size} Boulder Problems</b></p>""" else "") +
          """</div></div>"""

        val infowindow = new google.maps.InfoWindow(google.maps.InfoWindowOptions(
          content=genInfoText(c)
        ))

        google.maps.event.addListener(marker, "click", () => infowindow.open(gmap,marker) )
      })
  }
}
