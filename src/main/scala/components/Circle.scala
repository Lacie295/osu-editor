package components

import utils.{Position, TimeStamp}

class Circle(p: Position, t: TimeStamp) extends HitObject(p, t) {
  def canEqual(a: Any): Boolean = a.isInstanceOf[Circle]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Circle => that.canEqual(this) && this.t == that.getTimeStamp && this.p == that.getPosition
      case _ => false
    }
  }
}
