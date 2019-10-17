package utils

  // A Hitsound instance represents the base hitsound of an object that is (ideally) always played on-hit, meaning on active
  // hit of a circle or slider and the finish of a spinner. sampleSet saves (normal, soft, drum) and the index the custom index.

class  Hitsound(s: Int, i: Int) {
  private var _sampleSet: Int = s                               // is normal, soft or drum
  private var _sampleIndex: Int = i                             // is [sampleSet]-hitnormal{index}

  def sampleSet_=(s: Int): Unit = _sampleSet = s

  def sampleSet: Int = _sampleSet

  def sampleIndex_=(i: Int): Unit = _sampleIndex = i

  def sampleIndex: Int = _sampleIndex
}

object Hitsound {
  implicit def tupleToHitsound(hs: (Int, Int)): Hitsound = new Hitsound(hs._1, hs._2)
}
