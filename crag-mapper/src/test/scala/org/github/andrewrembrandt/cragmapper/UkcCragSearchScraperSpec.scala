package org.github.andrewrembrandt.cragmapper

import org.scalatest._

/**
  * Created by Andrew on 19/10/2016.
  */
class UkcCragSearchScraperSpec extends FlatSpec with Matchers {
  "A Crag Scraper" should "extract Crags for this real crag" in {
    val ukcCragSearchScraper = new UkcCragSearchScraper("Tenerife")

    ukcCragSearchScraper.crags.length should be > 0
    print(ukcCragSearchScraper.crags)
  }
}
