package core

import components.{AbstractTimingPoint, Component, HitObject}
import utils.{ProfileMap, TimeStamp}

class Map {
  private val objects = ProfileMap[HitObject]()
  private val timingPoints = ProfileMap[AbstractTimingPoint]()

  def addObject(o: HitObject): Unit = objects += o

  def getObject(t: TimeStamp): Option[HitObject] = {
    val res = objects(t)
    if (res.isEmpty) {
      None
    } else {
      Some(res(0))
    }
  }

  def deleteObject(o: HitObject): Unit = objects -= o

  def getOverlapObject(o: Component): List[HitObject] = objects(o)

  def getOverlapObject(t: TimeStamp): List[HitObject] = objects(t)

  def addTimingPoint(t: AbstractTimingPoint): Unit  = timingPoints += t

  def getTimingPoint(t: TimeStamp): AbstractTimingPoint = timingPoints.search(t)

  def deleteTimingPoint(t: AbstractTimingPoint): Unit = timingPoints -= t

  def getOverlapTimingPoint(o: Component): List[AbstractTimingPoint] = timingPoints(o)

  def getOverlapTimingPoint(t: TimeStamp): List[AbstractTimingPoint] = timingPoints(t)

  def allObjects: List[HitObject] = objects.toList

  def allTimingPoints: List[AbstractTimingPoint] = timingPoints.toList
}