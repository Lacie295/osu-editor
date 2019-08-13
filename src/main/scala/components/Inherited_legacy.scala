package components

import utils.TimeStamp

class Inherited_legacy(t: TimeStamp, multi: Float, ss: Int, si: Int, vol: Int, ki: Boolean) extends TimingPoint_legacy(t, ss, si, vol, ki){
  private var _svMultiplier: Float = multi

  def svMultiplier: Float = _svMultiplier

  def svMultiplier_=(multi: Float): Unit = _svMultiplier = multi
}
