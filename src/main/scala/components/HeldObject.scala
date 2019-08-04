package components

import utils.{Position, TimeStamp}

abstract class HeldObject(p: Position, ep: Position, t: TimeStamp, et: TimeStamp) extends HitObject(p, t) {
  private var endTime: TimeStamp = et
  private var endPos: Position = ep
}
