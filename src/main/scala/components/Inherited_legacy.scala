package components

import utils.TimeStamp

/**
 * the base osu inherited timestamp
 * @param t: it's timestamp
 * @param multi: the slider speed multiplier
 * @param ss: the sample set
 * @param si: the sample index
 * @param vol: the volume
 * @param ki: kiai
 */
class Inherited_legacy(t: TimeStamp, multi: Double, ss: Int, si: Int, vol: Int, ki: Boolean) extends TimingPoint_legacy(t, ss, si, vol, ki){
  private var _svMultiplier: Double = multi

  // getters and setters
  def svMultiplier: Double = _svMultiplier

  def svMultiplier_=(multi: Double): Unit = _svMultiplier = multi

  def canEqual(a: Any): Boolean = a.isInstanceOf[Inherited_legacy]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Inherited_legacy => that.canEqual(this) && this.time == that.time && this.svMultiplier == that.svMultiplier && this.sampleIndex == that.sampleIndex && this.sampleSet == that.sampleSet && this.volume == that.volume && this.kiai == that.kiai
      case _ => false
    }
  }
}

/**
 * Conversion to modern timing point
 */
object Inherited_legacy {
  // TODO
  implicit def inherited_legacyToTimingPoint(t: Uninherited_legacy): TimingPoint = new TimingPoint(0, 0)
}
