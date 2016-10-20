package org.github.andrewrembrandt.cragmapper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser

import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model.Element
import scala.util.matching.Regex


case class Crag (name: String, ukcCragId: Int, climbs: List[Climb])

/**
  * Created by andrew on 11/10/16.
  */
class UkcCragSearchScraper(val searchLoc: String) {

  val browser = JsoupBrowser()
  val cragSearchDoc = browser.get(s"http://www.ukclimbing.com/logbook/map/liveresults.php?g=0&loc=$searchLoc&dist=50&km=1&q=")

  def ukcCragIdFromResults(e: Element) = """id=([0-9]+)""".r.findFirstMatchIn(e.attr("onclick")).get.group(1).toInt

  val crags = cragSearchDoc >> elements(".panel") map {
    e => val cragId = ukcCragIdFromResults(e); Crag(
      e >> text(".panel-heading"), cragId, (new UkcClimbSearchScaper(cragId)).climbs.toList
      )
  } toList
}
