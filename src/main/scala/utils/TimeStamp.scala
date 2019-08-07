package utils

class TimeStamp(t: Int) {
  private var time: Int = t

  def getTime: Int = time

  def setTime(t: Int): Unit = time = t

  def canEqual(a: Any): Boolean = a.isInstanceOf[TimeStamp]

  override def equals(that: Any): Boolean = {
    that match {
      case that: TimeStamp => that.canEqual(this) && this.time == that.time
      case _ => false
    }
  }
}

object TimeStamp {
  implicit def intToTimeStamp(t: Int): TimeStamp = new TimeStamp(t)

  implicit def timeStampToInt(t: TimeStamp): Int = t.getTime
}