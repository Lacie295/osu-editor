package components

import utils.{Position, TimeStamp}

abstract class HitObject(p: Position, t: TimeStamp) extends Component(t) {
  private var _pos: Position = p

  def position: Position = _pos

  def position_=(set_pos: Position): Unit = _pos = set_pos

  def overlaps(o: HitObject): Boolean = {
    o match {
      case o: HeldObject => o.overlaps(this)
      case _ => this.time == o.time
    }
  }
}

object HitObject {
  implicit def objectToPosition(o: HitObject): Position = o.position
}
