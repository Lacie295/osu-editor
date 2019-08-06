package components

import utils.{Position, TimeStamp}

abstract class HeldObject(p: Position, ep: Position, t: TimeStamp, et: TimeStamp) extends HitObject(p, t) {
  private var endTime: TimeStamp = et
  private var endPos: Position = ep

  def getEndTime: TimeStamp = endTime
  def getEndPosition: Position = endPos

  def setEndTime(set_endTime: TimeStamp) : Unit = endTime = set_endTime
  def setEndPosition(set_endPos: Position) : Unit = endPos = set_endPos

}
