package utils

class TimeStamp(t: Int) {
  private var _time: Int = t

  def time: Int = _time

  private var _listeners: List[TimeStampListener] = List()

  def time_=(t: Int): Unit = {
    _time = t
    _listeners.foreach(_.onTimeStampChange(this))
  }

  def canEqual(a: Any): Boolean = a.isInstanceOf[TimeStamp]

  override def equals(that: Any): Boolean = {
    that match {
      case that: TimeStamp => that.canEqual(this) && this.time == that.time
      case _ => false
    }
  }

  def addTimeStampListener(l: TimeStampListener): Unit = {
    _listeners = _listeners :+ l
  }

  def removeTimeStampListener(l: TimeStampListener): Unit = {
    _listeners = _listeners.filter(_ != l)
  }

  def alertListeners(): Unit = {
    _listeners.foreach(_.onTimeStampChange(this))
  }
}

trait TimeStampListener {
  def onTimeStampChange(t: TimeStamp): Unit
}

object TimeStamp {
  implicit def intToTimeStamp(t: Int): TimeStamp = new TimeStamp(t)

  implicit def timeStampToInt(t: TimeStamp): Int = t.time
}