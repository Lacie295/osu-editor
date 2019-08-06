package utils

class TimeStamp(t: Int) {
  private var time: Int = t

  def getTime: Int = time

  def setTime(t: Int): Unit = time = t
}

object TimeStamp {
  implicit def intToTimeStamp(t: Int): TimeStamp = new TimeStamp(t)

  implicit def timeStampToInt(t: TimeStamp): Int = t.getTime
}