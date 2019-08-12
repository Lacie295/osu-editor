package components

import utils._

class Spinner(t: TimeStamp, et: TimeStamp) extends HeldObject((0, 0), (0, 0), t, et) {
  def canEqual(a: Any): Boolean = a.isInstanceOf[Spinner]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Spinner => that.canEqual(this) && this.time == that.time && this.endTime == that.endTime
      case _ => false
    }
  }
}
