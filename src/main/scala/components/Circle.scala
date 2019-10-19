package components

import utils.{Hitsound, Position, TimeStamp}

class Circle(p: Position, t: TimeStamp, hs: Hitsound = (0,0)) extends HitObject(p, t, hs) {
  def canEqual(a: Any): Boolean = a.isInstanceOf[Circle]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Circle => that.canEqual(this) && this.time == that.time && this.position == that.position
      case _ => false
    }
  }
}
