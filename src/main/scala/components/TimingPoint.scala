package components

import utils.TimeStamp

class TimingPoint(t: TimeStamp, BPM: Double, m1: Int = 4, m2: Int = 4) extends AbstractTimingPoint(t) {
  private var _bpm = BPM

  require(_bpm > 0, () => "BPM Must be positive")

    //meters: A / B
  private var _meterA = m1
  private var _meterB = m2

  require(_meterA > 0 && _meterB > 0, () => "meter cannot have 0 values")

  def bpm: Double = _bpm

  def bpm_=(BPM: Double) = {
    require(BPM > 0, () => "BPM Must be positive")
    _bpm = BPM
  }

  def meterA: Int = _meterA

  def meterA_=(m1: Int): Unit = {
    require(m1 > 0, () => "meter cannot have 0 values")
    _meterA = m1
  }

  def meterB: Int = _meterB

  def meterB_=(m2: Int): Unit = {
    require(m2 > 0, () => "meter cannot have 0 values")
    _meterB = m2
  }

  def canEqual(a: Any): Boolean = a.isInstanceOf[TimingPoint]

  override def equals(that: Any): Boolean = {
    that match {
      case that: TimingPoint => that.canEqual(this) && this.time == that.time && this.bpm == that.bpm && this.meterA == that.meterA && this.meterB == that.meterB
      case _ => false
    }
  }

}
