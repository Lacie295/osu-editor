package components

import utils.{Position, TimeStamp}

abstract class HitObject(p: Position, t: TimeStamp) extends Component(t) {
  private var pos: Position = p

  def getPosition: Position = pos

  def setPosition(set_pos: Position): Unit = pos = set_pos

  def overlaps(o: HitObject): Boolean = this.getTimeStamp == o.getTimeStamp
}

object HitObject {
  implicit def objectToPosition(o: HitObject): Position = o.getPosition
}
