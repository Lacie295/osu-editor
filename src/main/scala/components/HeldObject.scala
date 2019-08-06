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

  override def overlaps(o: HitObject): Boolean = {
    o match {
      case o: HeldObject => {
        def between(a: Int, b: Int, t: Int): Boolean = a <= t && t <= b

        val t = this.getTime
        val et = this.getEndTime
        val ot = o.getTime
        val eot = o.getEndTime

        between(t, et, ot) || between(t, et, eot) || between(ot, eot, t) || between(ot, eot, et)
      }
      case _ => (this.getTime <= o.getTime && this.getEndTime >= o.getTime)
    }
  }
}
