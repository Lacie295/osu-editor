package utilsTest

import coreTest.BaseTest
import core.ObjectHandler._

class HitsoundTest extends BaseTest {
  "A Hitsound" should "always return all its values correctly" in {
    val hitsound = MakeHitsound

    assert(hitsound.sampleIndex == 0)
    assert(hitsound.sampleSet == 0)

    val hitsound2 = MakeHitsound (1)

    assert(hitsound2.sampleIndex == 0)
    assert(hitsound2.sampleSet == 1)

    val hitsound3 = MakeHitsound (2, 3)

    assert(hitsound3.sampleIndex == 3)
    assert(hitsound3.sampleSet == 2)

    hitsound.sampleIndex = 2
    assert(hitsound.sampleIndex == 2)

    hitsound.sampleSet = 3
    assert(hitsound.sampleSet == 3)
  }

  "A Hitsound" should "always compare itself correctly to other Hitsounds" in {
    val hitsound = MakeHitsound
    val hitsound2 = MakeHitsound (0, 0)
    val hitsound3 = MakeHitsound (3, 2)
    val hitsound4 = MakeHitsound (2, 3)

    assert(hitsound == hitsound2)
    assert(hitsound != hitsound3)
    assert(hitsound != hitsound4)
    assert(hitsound3 != hitsound4)
  }
}
