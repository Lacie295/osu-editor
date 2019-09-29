package components

import utils.{Hitsound, Position, TimeStamp}

abstract class HitObject(p: Position, t: TimeStamp, hs: Hitsound = (0, 0)) extends Component(t) {
  private var _pos: Position = p
  private var _hitsound: Hitsound = hs

  def position: Position = _pos

  def position_=(set_pos: Position): Unit = _pos = set_pos

  def hitsound: Hitsound = _hitsound

  def hitsound_=(hs: Hitsound): Unit = _hitsound = hs

  def overlaps(o: HitObject): Boolean = {
    o match {
      case o: HeldObject => o.overlaps(this)
      case _ => overlaps(o.timeStamp)
    }
  }

  def overlaps(t: TimeStamp): Boolean = {
    this.timeStamp == t
  }
}

object HitObject {
  implicit def objectToPosition(o: HitObject): Position = o.position
}
