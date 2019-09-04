package components

import utils.{TimeStamp, TimeStampListener}

abstract class Component(t: TimeStamp) extends TimeStampListener {
  private var _time: TimeStamp = t
  _time.addTimeStampListener(this)

  private var _timeStampListeners: List[ComponentListener] = List()

  def timeStamp: TimeStamp = _time

  def timeStamp_=(t: TimeStamp): Unit = {
    _time.removeTimeStampListener(this)
    _time = t
    _time.addTimeStampListener(this)
  }

  def time: Int = timeStamp.time

  def time_=(t: Int): Unit = timeStamp.time = t

  override def onTimeStampChange(t: TimeStamp): Unit = {
    alertListeners()
  }

  def alertListeners(): Unit = {
    _timeStampListeners.foreach(_.onComponentTimeChange(this))
  }
}

trait ComponentListener {
  def onComponentTimeChange(t: Component): Unit
}

object Component {
  implicit def componentToTimeStamp(c: Component): TimeStamp = c.timeStamp
}