package org.github.andrewrembrandt.cragmapper

object Main {
  def main(args: Array[String]): Unit = {
    val searchRes = new UkcCragSearchScraper("Tenerife")
    print(searchRes.crags)
  }
}
