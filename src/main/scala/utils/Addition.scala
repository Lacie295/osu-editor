package utils

class Addition(s: Int, i: Int, b: Boolean) extends Hitsound(s, i){
  private var _active: Boolean = b

  def active_=(b: Boolean): Unit = _active = b

  def active: Boolean = _active
}
