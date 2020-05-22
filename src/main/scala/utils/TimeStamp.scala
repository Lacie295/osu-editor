package utils

/**
 * a timestamp implementation
 * @param t
 */
class TimeStamp(t: Int) extends Comparable[TimeStamp] {
  private var _time: Int = t

  // getters and setters

  def time: Int = _time

  def time_=(t: Int): Unit = _time = t

  def canEqual(a: Any): Boolean = a.isInstanceOf[TimeStamp]

  override def equals(that: Any): Boolean = {
    that match {
      case that: TimeStamp => that.canEqual(this) && this.time == that.time
      case _ => false
    }
  }

  override def compareTo(t: TimeStamp): Int = this - t

  def >(t: TimeStamp): Boolean = this.time > t.time
  def <(t: TimeStamp): Boolean = this.time < t.time
  def >=(t: TimeStamp): Boolean = this.time >= t.time
  def <=(t: TimeStamp): Boolean = this.time <= t.time

  override def toString: String = time.toString + "ms"
}

/**
 * conversion to/from ints
 */
object TimeStamp {
  implicit def intToTimeStamp(t: Int): TimeStamp = new TimeStamp(t)

  implicit def timeStampToInt(t: TimeStamp): Int = t.time
}