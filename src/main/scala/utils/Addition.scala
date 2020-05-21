package utils

/**
 * Additions work similarly to Hitsounds. They are handled on-object to allow for interchanging them across greenlines.
 * The Addition instance saves the sample (normal, soft, drum), the custom index and whether it is active or not. since
 * greenlines change the behavior of additions even if they aren't set (=active), their sample and index properties get set
 * independently from their active status. setting an addition when editing only sets it to active/inactive and their sample/index
 * status will be changed independently from activity.
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
