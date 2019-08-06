package components

import utils.TimeStamp

abstract class Component (t: TimeStamp) {
  private var time: TimeStamp = t

  def getTime : TimeStamp = time

  def setTime(set_time: TimeStamp) : Unit = time = set_time
}
