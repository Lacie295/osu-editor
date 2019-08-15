package components

import utils.TimeStamp

class Inherited_legacy(t: TimeStamp, multi: Double, ss: Int, si: Int, vol: Int, ki: Boolean) extends TimingPoint_legacy(t, ss, si, vol, ki){
  private var _svMultiplier: Double = multi

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

object Inherited_legacy {
  implicit def inherited_legacyToTimeStamp(a: Inherited_legacy): TimeStamp = a.timeStamp
}
