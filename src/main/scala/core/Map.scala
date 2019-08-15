package core

import components.{AbstractTimingPoint, Circle, HitObject, Inherited_legacy}
import utils.TimeStamp

class Map {
  def addObject(o: HitObject): Unit = {

  }

  def getObject(t: TimeStamp): HitObject = {
    new Circle((0, 0), 0)
  }

  def deleteObject(t: TimeStamp): HitObject = {
    new Circle((0, 0), 0)
  }

  def deleteObject(o: HitObject): TimeStamp = {
    0
  }

  def addTimingPoint(t: AbstractTimingPoint): Unit  = {

  }

  def getTimingPoints(t: TimeStamp): List[AbstractTimingPoint] = {
    List(new Inherited_legacy(0, 0, 0, 0, 0, true))
  }

  def deleteTimingPoints(t: TimeStamp): List[AbstractTimingPoint] = {
    List(new Inherited_legacy(0, 0, 0, 0, 0, true))
  }
}
