package utils

class  Hitsound(s: Int, i: Int) {
  private var _sampleSet: Int = s
  private var _sampleIndex: Int = i

  def sampleSet_=(s: Int): Unit = _sampleSet = s

  def sampleSet: Int = _sampleSet

  def sampleIndex_=(i: Int): Unit = _sampleIndex = i

  def sampleIndex: Int = _sampleIndex
}

object Hitsound {
  implicit def tupleToHitsound(hs: (Int, Int)): Hitsound = new Hitsound(hs._1, hs._2)
}
