package components

import utils.TimeStamp

class Uninherited_legacy(t: TimeStamp, bpm: Float, m: Int, ss: Int, si: Int, vol: Int, ki: Boolean) extends TimingPoint_legacy(t, ss, si, vol, ki) {
  private var _BPM: Float = bpm
  private var _meter: Int = m

  def BPM: Float = _BPM

  def BPM_=(bpm: Float): Unit = _BPM = bpm

  def meter: Int = _meter

  def meter_=(m: Int): Unit = _meter = m
}
