package components

import utils.{TimeStamp}

abstract class Component(t: TimeStamp) {
  private var _time: TimeStamp = t

  def timeStamp: TimeStamp = _time

  def timeStamp_=(t: TimeStamp): Unit = _time = t

  def time: Int = timeStamp.time

  def time_=(t: Int): Unit = timeStamp.time = t
}

object Component {
  implicit def componentToTimeStamp(c: Component): TimeStamp = c.timeStamp
}