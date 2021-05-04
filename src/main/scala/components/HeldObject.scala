package components

import utils.{Hitsound, Position, TimeStamp}

/**
 * a hitobject that has a longer activation time
 * @param p: its position on screen
 * @param t: the start time for the object
 * @param et: the end time for the object
 * @param hs: its associated hitsound
 */
abstract class HeldObject(p: Position, t: TimeStamp, et: TimeStamp, hs: Hitsound = (0,0), nc: Boolean = false) extends HitObject(p, t, hs, nc) {
  private var _endTime: TimeStamp = et

  require(timeStamp < endTimeStamp, () => "End time must be after start time")

  // getters and setters
  def endTimeStamp: TimeStamp = _endTime

  def endTime: Int = endTimeStamp.time

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

  // overlap check
  override def overlaps(o: Component): Boolean = {
    o match {
      case o: HeldObject =>
        def between(a: Int, b: Int, t: Int): Boolean = a <= t && t <= b

        val t = this.time
        val et = this.endTime
        val ot = o.time
        val eot = o.endTime

        between(t, et, ot) || between(t, et, eot) || between(ot, eot, t) || between(ot, eot, et)
      case _ => overlaps(o.timeStamp)
    }
  }

  override def overlaps(t: TimeStamp): Boolean = {
    this.time <= t && this.endTime >= t
  }
}
