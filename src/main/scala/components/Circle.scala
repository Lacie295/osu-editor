package components

import utils.{Hitsound, Position, TimeStamp}

/**
 * a hitcircle
 * @param p: the pixel position on screen
 * @param t: the timestamp at which the object is active
 * @param hs: the hitsound associated to it
 */
class Circle(p: Position, t: TimeStamp, hs: Hitsound = (0,0), nc: Boolean = false) extends HitObject(p, t, hs, nc) {
  def canEqual(a: Any): Boolean = a.isInstanceOf[Circle]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Circle => that.canEqual(this) &&
        this.time == that.time &&
        this.position == that.position &&
        this.hitsound == that.hitsound &&
        (this.additions sameElements that.additions) &&
        this.additionsHitsound == that.additionsHitsound
      case _ => false
    }
  }
}
