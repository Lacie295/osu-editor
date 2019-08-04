package utils

class TimeStamp(t: Int) extends Comparable[TimeStamp] {
  private var time: Int = t

  def getTime: Int = time

  def setTime(t: Int): Unit = time = t

  def compareTo(t: TimeStamp): Int = getTime - t.getTime
}
