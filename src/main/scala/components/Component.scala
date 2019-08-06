package components

import utils.TimeStamp

abstract class Component(t: TimeStamp) {
  private var time: TimeStamp = t

  def getTimeStamp: TimeStamp = time
}

object Component {
  implicit def componentToTimeStamp(c: Component): TimeStamp = c.getTimeStamp
}