package components

import utils.{Position, TimeStamp}

abstract class HeldObject(p: Position, ep: Position, t: TimeStamp, et: TimeStamp) extends HitObject(p, t) {
  private var endTime: TimeStamp = et
  private var endPos: Position = ep

  def getEndTimeStamp: TimeStamp = endTime

  def getEndTime: Int = endTime.getTime

  def getEndPosition: Position = endPos

  def getEndX: Int = endPos.getX

  def getEndY: Int = endPos.getY

  def setEndTimeStamp(et: TimeStamp): Unit = endTime = et

  def setEndTime(et: Int): Unit = endTime.setTime(et)

  def setEndPosition(ep: Position): Unit = endPos = ep

  def setEndX(x: Int): Unit = endPos.setX(x)

  def setEndY(y: Int): Unit = endPos.setY(y)
}
