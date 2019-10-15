package components

import utils.{Position, TimeStamp}

abstract class HeldObject(p: Position, ep: Position, t: TimeStamp, et: TimeStamp) extends HitObject(p, t) {
  private var _endTime: TimeStamp = et
  private var _endPos: Position = ep

  require(timeStamp < endTimeStamp, () => "End time must be after start time")

  def endTimeStamp: TimeStamp = _endTime

  def endTime: Int = endTimeStamp.time

  def endPosition: Position = _endPos

  def endX: Int = endPosition.x

  def endY: Int = endPosition.y

  def endTimeStamp_=(et: TimeStamp): Unit = {
    require(timeStamp < et, () => "End time must be after start time")
    _endTime = et
  }

  override def timeStamp_=(t: TimeStamp): Unit = {
    require(t < endTimeStamp, () => "End time must be after start time")
    super.timeStamp_=(t)
  }

  def endTime_=(et: Int): Unit = {
    require(timeStamp < et, () => "End time must be after start time")
    endTimeStamp.time = et
  }

  override def time_=(t: Int): Unit = {
    require(t < endTimeStamp, () => "End time must be after start time")
    super.time_=(t)
  }

  def endPosition_=(ep: Position): Unit = _endPos = ep

  def endX_=(x: Int): Unit = _endPos.x = x

  def endY_=(y: Int): Unit = _endPos.y = y

  override def overlaps(o: HitObject): Boolean = {
    o match {
      case o: HeldObject => {
        def between(a: Int, b: Int, t: Int): Boolean = a <= t && t <= b

        val t = this.time
        val et = this.endTime
        val ot = o.time
        val eot = o.endTime

        between(t, et, ot) || between(t, et, eot) || between(ot, eot, t) || between(ot, eot, et)
      }
      case _ => overlaps(o.timeStamp)
    }
  }

  override def overlaps(T: TimeStamp): Boolean = {
    this.time <= t && this.endTime >= t
  }
}
