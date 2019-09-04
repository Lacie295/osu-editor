package core

import components.{AbstractTimingPoint, Circle, Component, ComponentTimeListener, HitObject, Inherited_legacy}
import utils.TimeStamp

class Map extends ComponentTimeListener {
  def addObject(o: HitObject): Unit = ???

  def getObject(t: TimeStamp): Option[HitObject] = ???

  def deleteObject(t: TimeStamp): HitObject = ???

  def deleteObject(o: HitObject): TimeStamp = ???

  def addTimingPoint(t: AbstractTimingPoint): Unit  = ???

  def getTimingPoint(t: TimeStamp): Option[AbstractTimingPoint] = ???

  def deleteTimingPoint(t: TimeStamp): Option[AbstractTimingPoint] = ???

  override def onComponentTimeChange(t: Component): Unit = ???
}