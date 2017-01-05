package org.github.andrewrembrandt.cragmapper
package shared.models

/**
  * Created by andrew on 02/01/17.
  */
sealed trait ClimbType { def name: String }
case object Sport extends ClimbType {val name = "Sport"}
case object Trad extends ClimbType {val name = "Trad"}
case object Boulder extends ClimbType {val name = "Boulder"}
case class UnknownClimbType(name: String) extends ClimbType


case class Climb(name: String, grade: String, climbType: ClimbType)
case class Crag(name: String, lat: Double, lng: Double, climbs: List[Climb])
{
  def sport = climbs.filter(c => c.climbType == Sport)
  def trad = climbs.filter(c => c.climbType == Trad)
  def boulder = climbs.filter(c => c.climbType == Boulder)
}

object Crag {

  val crags = Set(
    Crag("Stanage Popular", 53.346,-1.632,
      Climb("Bumbler's Arete", "M", Trad)
      :: Climb("A Black Ying", "M", Trad) :: Nil),
    Crag("Stanage Plantation", 53.353, -1.639, Nil),
    Crag("Carrhead Rocks", 53.342, -1.641, Nil)
  )

  def findAll = crags.toList.sortBy(_.name)
}