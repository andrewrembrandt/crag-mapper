package org.github.andrewrembrandt.cragmapper

import org.scalatest._

/**
  * Created by Andrew on 19/10/2016.
  */
class UkcClimbSearchScraperSpec extends FlatSpec with Matchers {
  "A Climb Scraper" should "extract climbs for this real crag" in {
    val ukcClimbSearchScaper = new UkcClimbSearchScaper(2375)

    ukcClimbSearchScaper.climbs.length should be > 1

    print(ukcClimbSearchScaper.climbs)
  }
}
