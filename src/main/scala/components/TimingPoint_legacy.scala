package components

import utils.TimeStamp

abstract class TimingPoint_legacy(t: TimeStamp, ss: Int, si: Int, vol: Int, ki: Boolean) extends AbstractTimingPoint(t) {
  private var _sampleSet: Int = ss
  private var _sampleIndex: Int = si
  private var _vol: Int = vol
  private var _kiai: Boolean = ki

  def sampleSet: Int = _sampleSet

  def sampleSet_=(ss: Int): Unit = _sampleSet = ss

  def sampleIndex: Int = _sampleIndex

  def sampleIndex_=(si: Int): Unit = _sampleIndex = si

  def volume: Int = _vol

  def volume_=(vol: Int): Unit = _vol = vol

  def kiai: Boolean = _kiai

  def kiai_=(ki: Boolean): Unit = _kiai = ki

  abstract def toTimingPoint: TimingPoint
}
