package core

import components.{AbstractTimingPoint, Circle, Component, HeldObject, HitObject, Inherited_legacy}
import utils.{BinarySearchTree, TimeStamp}

class Map {
  private val objects = BinarySearchTree[TimeStamp, HitObject]()
  private val timingPoints = BinarySearchTree[TimeStamp, AbstractTimingPoint]()

  def addObject(o: HitObject): Unit = objects.insert(o.timeStamp, o)

  def getObject(t: TimeStamp): List[HitObject] = objects(t).filter(_.overlaps(t))

  def deleteObject(o: HitObject): List[HitObject] = objects.delete(o.timeStamp, o)

  def addTimingPoint(t: AbstractTimingPoint): Unit  = timingPoints.insert(t.timeStamp, t)

  def getTimingPoint(t: TimeStamp): List[AbstractTimingPoint] = timingPoints(t)

  def deleteTimingPoint(t: AbstractTimingPoint): List[AbstractTimingPoint] = timingPoints.delete(t.timeStamp, t)
}