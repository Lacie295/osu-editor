package utils

class Position(x: Int, y: Int) {
  private var pos_x: Int = x
  private var pos_y: Int = y

  def getX : Int = pos_x
  def getY : Int = pos_y

  def setX(x: Int) : Unit = pos_x = x
  def setY(y: Int) : Unit = pos_y = y
}
