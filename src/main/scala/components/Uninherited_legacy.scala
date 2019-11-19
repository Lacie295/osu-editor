package components

import utils.TimeStamp

class Uninherited_legacy(t: TimeStamp, bpm: Double, m: Int, ss: Int, si: Int, vol: Int, ki: Boolean) extends TimingPoint_legacy(t, ss, si, vol, ki) {
  private var _BPM: Double = bpm
  private var _meter: Int = m

  def BPM: Double = _BPM

  def BPM_=(bpm: Double): Unit = _BPM = bpm

  def meter: Int = _meter

  def meter_=(m: Int): Unit = _meter = m

  def canEqual(a: Any): Boolean = a.isInstanceOf[Uninherited_legacy]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Uninherited_legacy => that.canEqual(this) && this.time == that.time && this.BPM == that.BPM && this.meter == that.meter && this.sampleIndex == that.sampleIndex && this.sampleSet == that.sampleSet && this.volume == that.volume && this.kiai == that.kiai
      case _ => false
    }
  }
}

object Uninherited_legacy {
  implicit def uninherited_legacyToTimingPoint(t: Uninherited_legacy): TimingPoint = new TimingPoint(t.time, t.bpm, t.meter, 4)
}
