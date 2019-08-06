package utils

class Position(pos: (Int, Int)) {
  private var pos_x: Int = pos._1
  private var pos_y: Int = pos._2

  def getX: Int = pos_x

  def getY: Int = pos_y

  def getPos: (Int, Int) = (pos_x, pos_y)

  def setX(x: Int): Unit = pos_x = x

  def setY(y: Int): Unit = pos_y = y
}

object Position {
  implicit def tupleToPosition(pos: (Int, Int)): Position = new Position(pos)

  implicit def positionToTuple(pos: Position): (Int, Int) = pos.getPos
}