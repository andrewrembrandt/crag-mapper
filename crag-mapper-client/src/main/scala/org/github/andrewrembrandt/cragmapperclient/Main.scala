package org.github.andrewrembrandt.cragmapperclient

import org.scalajs.dom
import shared.SharedMessages

import org.scalajs.dom
import org.scalajs.dom.{ XMLHttpRequest, document }
import org.scalajs.jquery.{ JQuery, jQuery, JQueryAjaxSettings, JQueryXHR }
import org.scalajs.dom.raw.HTMLElement


import scala.scalajs.js
import scala.scalajs.js.Dynamic.{ global => g, literal => lit, newInstance => jsnew }
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.{ Array, Date, JSON }

object Main extends js.JSApp {
  def main(): Unit = {
    initMap()
    dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
  }

  def initMap() = {
    val lat = -25.363
    val long = 131.044

    val map_canvas = dom.document.getElementById("map_canvas")
    val map_options = lit(center = (jsnew(g.google.maps.LatLng))(lat, long), zoom = 4, mapTypeId = g.google.maps.MapTypeId.ROADMAP)
    val gogleMap = (jsnew(g.google.maps.Map))(map_canvas, map_options)
    val marker = (jsnew(g.google.maps.Marker))(lit(map = gogleMap, position = (jsnew(g.google.maps.LatLng)(lat, long))))
  }
}