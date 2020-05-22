package components

import utils.{Addition, Hitsound, Position, TimeStamp}

import collection.mutable.ArrayBuffer

/**
 * an implementation of a slider
 * @param p: its position on screen
 * @param t: the start time for the object
 * @param et: the end time for the object
 * @param v: the slider's speed
 * @param r: the repeat count
 * @param hs: its associated hitsound
 */
class Slider(p: Position, t: TimeStamp, et: TimeStamp, v: Double = 1.0, r: Int = 0, hs: Hitsound = (0,0)) extends HeldObject(p, t, et, hs) {
  private val _nodes = new ArrayBuffer[Node]()

  private var _repeats = r  // actual repeat count, means 0 if slider consists only of head + tail

  private var _repeatHitsounds: Array[(Hitsound, Array[Addition])] = Array.fill(r+1){(new Hitsound(), Array(new Addition(), new Addition(), new Addition()))}

  private var _velocity = v

  def canEqual(a: Any): Boolean = a.isInstanceOf[Slider]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Slider => that.canEqual(this) && this.time == that.time && this.endTime == that.endTime && this.position == that.position
      case _ => false
    }
  }

  // node functions

  def addNode(i: Int, p: Position, t: Int): Slider = {
    require(i > 0 && i < size)
    _nodes.insert(i - 1, new Node(p, t))
    this
  }

  def addNode(p: Position, t: Int): Slider = {
    _nodes += new Node(p, t)
    this
  }

  def addNode(i: Int, p: Position): Slider = {
    addNode(i, p, 0)
  }

  def addNode(p: Position): Slider = {
    addNode(p, 0)
  }

  def addNode(i: Int, x: Int, y: Int, t: Int): Slider = {
    addNode(i, (x, y), t)
  }

  def addNode(x: Int, y: Int, t: Int): Slider = {
    addNode((x, y), t)
  }

  def addNode(x: Int, y: Int): Slider = {
    addNode(x, y, 0)
  }

  def getNode(i: Int): Node = {
    require(i >= 0 && i < size)
    if (i == 0)
      new Node(position, 1)
    else
      _nodes(i - 1)
  }

  def apply(i: Int): Node = getNode(i)

  def removeNode(i: Int): Node = {
    require(i > 0 && i < size - 1)
    _nodes.remove(i - 1)
  }

  // getters and setters

  def nodes: List[Node] = {
    new Node(position, 1) +: _nodes.toList
  }

  def repeats: Int = _repeats

  def repeats_=(r: Int): Unit = _repeats = r

  def repeatHitsounds: Array[(Hitsound, Array[Addition])] = _repeatHitsounds

  def repeatHitsounds_=(rh: Array[(Hitsound, Array[Addition])]): Unit = _repeatHitsounds = rh

  def velocity: Double = _velocity

  def velocity_=(v: Double): Unit = _velocity = v

  def size: Int = _nodes.size + 1
}

/**
 * a node of a slider
 * @param p: the node's position
 * @param t: the node's type
 */
class Node(p: Position, t: Int) {
  private var _nodeType: Int = t // t = 0 for grey, = 1 for red
  private var _position: Position = p

  require(_nodeType == 0 || _nodeType == 1, () => "Type can only be gray or red (0 or 1)")

  // getters and setters

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

/**
 * implicit conversions
 */
object Node {
  implicit def nodeToPosition(node: Node): Position = node._position

  implicit def nodeToTuple(node: Node): (Position, Int) = (node._position, node.nodeType)
}