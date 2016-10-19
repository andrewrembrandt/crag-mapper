package org.github.andrewrembrandt.cragmapper

import org.scalatest._

/**
  * Created by Andrew on 19/10/2016.
  */
class UkcCragSearchScraperSpec extends FlatSpec {
  "A Crag Scraper" should "extract Crags for this real crag" in {
    val ukcCragSearchScaper = new UkcCragSearchScraper("Tenerife")

    print(ukcCragSearchScaper.crags)
  }
}
