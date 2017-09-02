package org.github.andrewrembrandt.cragmapper
package controllers

import play.api.libs.json.Json
import play.api.mvc._
import shared.SharedMessages
import shared.models._


class Application extends Controller {

  def index = Action {
    Ok(views.html.index(
        SharedMessages.gmapsKey,
        Json.toJson(Crag.findAll).toString()))
  }

}
