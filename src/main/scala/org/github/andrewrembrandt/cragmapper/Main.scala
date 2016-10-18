package org.github.andrewrembrandt.cragmapper

//import org.github.andrewrembrandt.cragmapper.UkcSearchScraper

object Main {
  def main(args: Array[String]): Unit = {
    val searchRes = new UkcSearchScraper("Tenerife")
    print(searchRes.crags)
  }
}
