package org.github.andrewrembrandt.cragmapper
package controllers

import play.api.mvc._
import shared.SharedMessages
import shared.models._
import upickle.default._

/** Main application controller */
class Application extends Controller {

  def index = Action {
    Ok(views.html.index(
        SharedMessages.itWorks,
        SharedMessages.gmapsKey,
        upickle.default.write[List[Crag]](Crag.findAll)))
  }

}
