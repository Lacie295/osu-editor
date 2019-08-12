package components

import utils.{Position, TimeStamp}

class Slider(p: Position, ep: Position, t: TimeStamp, et: TimeStamp) extends HeldObject(p, ep, t, et) {
  def canEqual(a: Any): Boolean = a.isInstanceOf[Slider]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Slider => that.canEqual(this) && this.time == that.time && this.endTime == that.endTime && this.position == that.position && this.endPosition == that.endPosition
      case _ => false
    }
  }
}

private class Node(p: Position, t: Int) {
  private var _nodeType: Int = t
  private val position: Position = p

  require(_nodeType < 0 || _nodeType > 1, () => "Type can only be gray or red (0 or 1)")

  private def nodeType: Int = _nodeType

  private def nodeType_=(value: Int): Unit = {
    require(_nodeType < 0 || _nodeType > 1, () => "Type can only be gray or red (0 or 1)")
    _nodeType = value
  }
}

object Node {
  implicit def nodeToPosition(node: Node): Position = node.position
}