package components

import utils.TimeStamp

/**
 * A component that is part of a map.
 * @param t: the component's timestamp
 */
abstract class Component(t: TimeStamp) extends Comparable[Component] {
  private var _time: TimeStamp = t

  // getters and setters
  def timeStamp: TimeStamp = _time

  def timeStamp_=(t: TimeStamp): Unit = _time = t

  def time: Int = timeStamp.time

  def time_=(t: Int): Unit = timeStamp.time = t

  override def compareTo(t: Component): Int = this.timeStamp.compareTo(t.timeStamp)

  // overlap check, if long object, use its overlap instead
  def overlaps(o: Component): Boolean = {
    o match {
      case o: HeldObject => o.overlaps(this)
      case _ => overlaps(o.timeStamp)
    }
  }

  def overlaps(t: TimeStamp): Boolean = {
    this.timeStamp == t
  }
}

/**
 * implicit conversion to timestamp
 */
object Component {
  implicit def componentToTimeStamp(c: Component): TimeStamp = c.timeStamp
}