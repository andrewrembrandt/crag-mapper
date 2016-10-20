package org.github.andrewrembrandt.cragmapper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model.Element


trait Climb {
  val name: String
  val ukcId: Int
  val grade: String

  val TradGradRegex = """^[MDHVSE]{1,3}[0-9]?\s*[3-7][a-c]$"""

  def applyFromGrade(grade: String, name: String, ukcId: Int) {
    match grade {
      case grade
    }
  }
}

class TradClimb extends Climb {
  val name: String = _
  val ukcId: Int = _
  val grade: String = _
}


/**
  * Created by Andrew on 19/10/2016.
  */
object UkcClimbSearchScaper{

  def extractClimbs(cragId: Int) {
    val cragDoc = JsoupBrowser().get(s"http://www.ukclimbing.com/logbook/crag.php?id=$cragId")

    val climbs = (cragDoc >> elements(".climb")).map(e => new Climb(e)) toList
  }

  def extractClimb(climbRowEl: Element) = {
    val name = climbRowEl.select(".name").head.text
    val id = climbRowEl.attr("data-id").toInt
    val grade =climbRowEl.select("td[nowrap]").head.text.trim())
  }
}
