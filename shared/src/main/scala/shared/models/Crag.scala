package shared.models

import play.api.libs.json._
import shared.helpers.EnumUtils
import shared.models.ClimbType.ClimbType

object ClimbType extends Enumeration {
  type ClimbType = Value
  val Sport, Trad, Boulder, Unknown = Value

  implicit val climbTypeFormat = EnumUtils.enumFormat(ClimbType)
}

case class Climb(name: String, grade: String, climbType: ClimbType)

object Climb {
  implicit val climbFormat = Json.format[Climb]
}

case class Crag(name: String, lat: Double, lng: Double, climbs: List[Climb])
{
  def sport = climbs.filter(c => c.climbType == ClimbType.Sport)
  def trad = climbs.filter(c => c.climbType == ClimbType.Trad)
  def boulder = climbs.filter(c => c.climbType == ClimbType.Boulder)
}

object Crag {

  val crags = Set(
    Crag("Stanage Popular", 53.346,-1.632,
      Climb("Bumbler's Arete", "M", ClimbType.Trad)
      :: Climb("A Black Ying", "M", ClimbType.Trad) :: Nil),
    Crag("Stanage Plantation", 53.353, -1.639, Nil),
    Crag("Carrhead Rocks", 53.342, -1.641, Nil)
  )

  def findAll = crags.toList.sortBy(_.name)

  implicit val cragFormat = Json.format[Crag]
}