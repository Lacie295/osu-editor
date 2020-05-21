package utils

class TimeStamp(t: Int) extends Comparable[TimeStamp] {
  private var _time: Int = t

  def time: Int = _time

  def time_=(t: Int): Unit = _time = t

  def canEqual(a: Any): Boolean = a.isInstanceOf[TimeStamp]

  override def equals(that: Any): Boolean = {
    that match {
      case that: TimeStamp => that.canEqual(this) && this.time == that.time
      case _ => false
    }
  }

  override def compareTo(t: TimeStamp): Int = (this - t).time

  def -(t: TimeStamp): TimeStamp = new TimeStamp(this.time - t.time)

  def +(t: TimeStamp): TimeStamp = new TimeStamp(this.time + t.time)

  def <(t: TimeStamp): Boolean = this.time < t.time

  def >(t: TimeStamp): Boolean = this.time > t.time

  def <=(t: TimeStamp): Boolean = this.time <= t.time

  def >=(t: TimeStamp): Boolean = this.time >= t.time
}

object TimeStamp {
  implicit def intToTimeStamp(t: Int): TimeStamp = new TimeStamp(t)
}