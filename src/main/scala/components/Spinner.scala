package components

import utils._

/**
 * implementation of a spinner
 * @param t: the start time for the object
 * @param et: the end time for the object
 * @param hs: its associated hitsound
 */
class Spinner(t: TimeStamp, et: TimeStamp, hs: Hitsound = (0,0)) extends HeldObject((0, 0), t, et, hs) {
  def canEqual(a: Any): Boolean = a.isInstanceOf[Spinner]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Spinner => that.canEqual(this) && this.time == that.time && this.endTime == that.endTime
      case _ => false
    }
  }
}
