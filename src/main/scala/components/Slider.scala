package components

import utils.{Position, TimeStamp}

class Slider(p: Position, ep: Position, t: TimeStamp, et: TimeStamp) extends HeldObject(p, ep, t, et) {
  def canEqual(a: Any): Boolean = a.isInstanceOf[Slider]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Slider => that.canEqual(this) && this.t == that.getTimeStamp && this.et == that.getEndTimeStamp && this.p == that.getPosition && this.ep == that.getEndPosition
      case _ => false
    }
  }
}
