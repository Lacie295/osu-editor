package core

import components.{Circle, Slider, Spinner}
import utils.TimeStamp

import scala.collection.mutable

class MapHandler private (m: Map) {
  def getCircle(t: TimeStamp): Circle = {
    getCircles(t)(0)
  }

  def getCircles(t: TimeStamp): List[Circle] = {
    m.getOverlapObject(t).filter(_.isInstanceOf[Circle]).map {_.asInstanceOf[Circle]}
  }

  def getSlider(t: TimeStamp): Slider = {
    getSliders(t)(0)
  }

  def getSliders(t: TimeStamp): List[Slider] = {
    m.getOverlapObject(t).filter(_.isInstanceOf[Slider]).map {_.asInstanceOf[Slider]}
  }

  def getSpinner(t: TimeStamp): Spinner = {
    getSpinners(t)(0)
  }

  def getSpinners(t: TimeStamp): List[Spinner] = {
    m.getOverlapObject(t).filter(_.isInstanceOf[Spinner]).map {_.asInstanceOf[Spinner]}
  }
}

object MapHandler {
  val handlers: mutable.Map[Map, MapHandler] = new mutable.HashMap()

  implicit def MapToMapHandler(m: Map): MapHandler = {
    if (handlers.contains(m))
      handlers(m)
    else {
      val handler = new MapHandler(m)
      handlers.put(m, handler)
      handler
    }
  }

  implicit def load(file: String): Map = {
    val parser = new Parser_legacy(file)
    val map = parser.readMap()
    handlers.put(map, new MapHandler(map))
    map
  }

  implicit def unload(m: Map): Unit = handlers.remove(m)
}