package utils

class Position(pos: (Int, Int)) {
  private var pos_x: Int = pos._1
  private var pos_y: Int = pos._2

  def getX: Int = pos_x

  def getY: Int = pos_y

  def getPos: (Int, Int) = (pos_x, pos_y)

  def setX(x: Int): Unit = pos_x = x

  def setY(y: Int): Unit = pos_y = y

  def canEqual(a: Any): Boolean = a.isInstanceOf[Position]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Position => that.canEqual(this) && this.pos_x == that.pos_x && this.pos_y == that.pos_y
      case _ => false
    }
  }
}

object Position {
  implicit def tupleToPosition(pos: (Int, Int)): Position = new Position(pos)

  implicit def positionToTuple(pos: Position): (Int, Int) = pos.getPos
}