package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._
import shared.Constants
import shared.models._


@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index())
  }

  def mapBrowser = Action {
    Ok(views.html.mapBrowser(
      Constants.gmapsKey,
      Json.toJson(Crag.findAll).toString()))
  }

  def weather = Action {
    Ok(views.html.weather())
  }
}
