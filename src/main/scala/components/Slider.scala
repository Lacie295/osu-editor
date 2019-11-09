package components

import utils.{Hitsound, Position, TimeStamp}

import collection.mutable.ArrayBuffer

class Slider(p: Position, ep: Position, t: TimeStamp, et: TimeStamp, v: Double, r: Int = 0, hs: Hitsound = (0,0)) extends HeldObject(p, ep, t, et, hs) {
  private val _nodes = new ArrayBuffer[Node]()

  private var _repeats = r

  private var _velocity = v

  def canEqual(a: Any): Boolean = a.isInstanceOf[Slider]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Slider => that.canEqual(this) && this.time == that.time && this.endTime == that.endTime && this.position == that.position && this.endPosition == that.endPosition
      case _ => false
    }
  }

  def addNode(i: Int, p: Position, t: Int): Unit = {
    require(i > 0 && i < size - 1)
    _nodes.insert(i - 1, new Node(p, t))
  }

  def addNode(p: Position, t: Int): Unit = {
    _nodes += new Node(p, t)
  }

  def getNode(i: Int): Node = {
    require(i >= 0 && i < size)
    if (i == 0)
      new Node(position, 1)
    else if (i == size - 1)
      new Node(endPosition, 1)
    else
      _nodes(i - 1)
  }

  def apply(i: Int): Node = getNode(i)

  def removeNode(i: Int): Node = {
    require(i > 0 && i < size - 1)
    _nodes.remove(i - 1)
  }

  def nodes: List[Node] = {
    new Node(position, 1) +: _nodes.toList :+ new Node(endPosition, 1)
  }

  def repeats: Int = _repeats

  def repeats_=(r: Int): Unit = _repeats = r

  def velocity_=(v: Double): Unit = _velocity = v

  def velocity: Double = _velocity

  def size: Int = _nodes.size + 2
}

class Node(p: Position, t: Int) {
  private var _nodeType: Int = t // t = 0 for grey, = 1 for red
  private var _position: Position = p

  require(_nodeType == 0 || _nodeType == 1, () => "Type can only be gray or red (0 or 1)")

  def nodeType: Int = _nodeType

  def nodeType_=(value: Int): Unit = {
    require(_nodeType == 0 || _nodeType == 1, () => "Type can only be gray or red (0 or 1)")
    _nodeType = value
  }

  def position: Position = _position

  def position_=(p: Position): Unit = _position = p

  def canEqual(a: Any): Boolean = a.isInstanceOf[Node]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Node => this.canEqual(that) && that.position == this.position && that.nodeType == this.nodeType
      case _ => false
    }
  }

  override def toString: String = "(" + position + "," + nodeType + ")"
}

object Node {
  implicit def nodeToPosition(node: Node): Position = node._position

  implicit def nodeToTuple(node: Node): (Position, Int) = (node._position, node.nodeType)
}