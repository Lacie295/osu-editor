package utilsTest

import coreTest.BaseTest
import utils.Hitsound

class HitsoundTest extends BaseTest {
  "A Hitsound" should "always return all its values correctly" in {
    val hitsound = new Hitsound()

    assert(hitsound.sampleIndex == 0)
    assert(hitsound.sampleSet == 0)

    val hitsound2 = new Hitsound(1)

    assert(hitsound2.sampleIndex == 0)
    assert(hitsound2.sampleSet == 1)

    val hitsound3 = new Hitsound(2, 3)

    assert(hitsound3.sampleIndex == 3)
    assert(hitsound3.sampleSet == 2)

    hitsound.sampleIndex = 2
    assert(hitsound.sampleIndex == 2)

    hitsound.sampleSet = 3
    assert(hitsound.sampleSet == 3)
  }

  "A Hitsound" should "always compare itself correctly to other Hitsounds" in {
    val hitsound = new Hitsound()
    val hitsound2 = new Hitsound(0, 0)
    val hitsound3 = new Hitsound(3, 2)
    val hitsound4 = new Hitsound(2, 3)

    assert(hitsound == hitsound2)
    assert(hitsound != hitsound3)
    assert(hitsound != hitsound4)
    assert(hitsound3 != hitsound4)
  }
}
