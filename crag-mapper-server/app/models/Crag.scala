package org.github.andrewrembrandt.cragmapper
package models

import play.api.libs.json.Json


/**
  * Created by andrew on 28/12/16.
  */
case class Crag(name: String, lat: Double, lng: Double) { }

object Crag {

  implicit val cragFormat = Json.format[Crag]

  val crags = Set(
    Crag("Stanage Popular", 53.346,-1.632),
    Crag("Stanage Plantation", 53.353, -1.639),
    Crag("Carrhead Rocks", 53.342, -1.641)
  )

  def findAll = crags.toList.sortBy(_.name)
}