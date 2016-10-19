package org.github.andrewrembrandt.cragmapper

import org.scalatest._

import org.github.andrewrembrandt.cragmapper._

/**
  * Created by Andrew on 19/10/2016.
  */
class UkcClimbSearchScraperSpec extends FlatSpec {
  "A Climb Scraper" should "extract climbs for this real crag" in {
    val ukcClimbSearchScaper = new UkcClimbSearchScaper(2375)

    print(ukcClimbSearchScaper.climbs)
  }
}
