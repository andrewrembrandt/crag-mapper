package org.github.andrewrembrandt.cragmapper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model.Element


case class Climb (name: String, ukcId: Int, grade: String) {
  def this(climbRowEl: Element) = this(
    climbRowEl.select(".name").head.text,
    climbRowEl.attr("data-id").toInt,
    climbRowEl.select("td[nowrap]").head.text.trim())
}

/**
  * Created by Andrew on 19/10/2016.
  */
class UkcClimbSearchScaper(cragId: Int) {
  val cragDoc = JsoupBrowser().get(s"http://www.ukclimbing.com/logbook/crag.php?id=$cragId")

  val climbs = (cragDoc >> elements(".climb")).map(e => new Climb(e))
}
