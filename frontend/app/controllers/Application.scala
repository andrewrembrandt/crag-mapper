package org.github.andrewrembrandt.cragmapper
package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._
import shared.SharedMessages
import shared.models._


@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(
        SharedMessages.gmapsKey,
        Json.toJson(Crag.findAll).toString()))
  }

}
