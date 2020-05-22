package utils

/**
 * Additions work similarly to Hitsounds. They are handled on-object to allow for interchanging them across greenlines.
 * The Addition instance saves the sample (normal, soft, drum), the custom index and whether it is active or not. since
 * greenlines change the behavior of additions even if they aren't set (=active), their sample and index properties get set
 * independently from their active status. setting an addition when editing only sets it to active/inactive and their sample/index
 * status will be changed independently from activity.
 * @param s: the sample set for the Hitsound (0-default, 1-normal, 2-soft, 3-drum)
 * @param i: the custom sample index
 * @param b: if the Addition is set or not
 */
class Addition(s: Int = 0, i: Int = 0, b: Boolean = false) extends Hitsound(s, i){
  private var _active: Boolean = b

  // getters and setters

  def active_=(b: Boolean): Unit = _active = b

  def active: Boolean = _active

  override def canEqual(a: Any): Boolean = a.isInstanceOf[Addition]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Addition => that.canEqual(this) && this.sampleSet == that.sampleSet && this.sampleIndex == that.sampleIndex && this.active == that.active
      case _ => false
    }
  }
}
