package utils

class Addition(s: Int, si: Int, as: Int) extends Hitsound(s, si) {
  private var _additionSample = as

  def sample_=(as: Int): Unit = _additionSample = as

  def sample: Int = _additionSample
}
