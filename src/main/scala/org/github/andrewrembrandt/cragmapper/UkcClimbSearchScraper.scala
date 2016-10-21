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
}

object Climb {
  val TradGradeRegex = """^([MDHVSE]{1,3}[0-9]?\s*[3-7][a-c])$""".r
  val FrenchGradeRegex = """^F?([3-9][a-c][\+-]?)$""".r

  def apply(name: String, ukcId: Int, grade: String): Option[Climb] = {
    grade match {
      case TradGradeRegex(grade) => Some(TradClimb(name, ukcId, grade))
      case FrenchGradeRegex(grade) => Some(SportClimb(name, ukcId, grade))
      case _ => None
    }
  }
}

case class TradClimb(name: String, ukcId: Int, grade: String) extends Climb
case class SportClimb (name: String, ukcId: Int, grade: String) extends Climb



/**
  * Created by Andrew on 19/10/2016.
  */
object UkcClimbSearchScaper{

  def extractClimbs(cragId: Int): List[Climb] = {
    val cragDoc = JsoupBrowser().get(s"http://www.ukclimbing.com/logbook/crag.php?id=$cragId")

    return ((cragDoc >> elements(".climb")).map(e =>extractClimb(e)) toList).flatten
  }

  def extractClimb(climbRowEl: Element) = {
    Climb.apply(
      climbRowEl.select(".name").head.text,
      climbRowEl.attr("data-id").toInt,
      climbRowEl.select("td[nowrap]").head.text.trim())
  }
}
