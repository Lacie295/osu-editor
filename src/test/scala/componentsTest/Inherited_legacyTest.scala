package componentsTest

import components.Inherited_legacy
import coreTest.BaseTest

class Inherited_legacyTest extends BaseTest{
  "An inherited timing point" should "always return its values correctly" in {
    val inhTimingPoint = new Inherited_legacy(100, 2.0, 0, 0, 100, true)

    assert(inhTimingPoint.time == 100)
    assert(inhTimingPoint.svMultiplier == 2.0)
    assert(inhTimingPoint.sampleSet == 0)
    assert(inhTimingPoint.sampleIndex == 0)
    assert(inhTimingPoint.volume == 100)
    assert(inhTimingPoint.kiai)

    inhTimingPoint.time = 105
    assert(inhTimingPoint.time == 105)

    inhTimingPoint.svMultiplier = 3.0
    assert(inhTimingPoint.svMultiplier == 3.0)

    inhTimingPoint.sampleSet = 4
    assert(inhTimingPoint.sampleSet == 4)

    inhTimingPoint.sampleIndex = 3
    assert(inhTimingPoint.sampleIndex == 3)

    inhTimingPoint.volume = 95
    assert(inhTimingPoint.volume == 95)

    inhTimingPoint.kiai = false
    assert(!inhTimingPoint.kiai)
  }

  it should "compare itself correctly to other inherited timing points" in {
    val inhTimingPoint = new Inherited_legacy(100, 2.0, 0, 0, 100, true)    // comparer
    val inhTimingPoint2 = new Inherited_legacy(100, 2.0, 0, 0, 100, true)   // equal
    val inhTimingPoint3 = new Inherited_legacy(101, 2.0, 0, 0, 100, true)   // different in time
    val inhTimingPoint4 = new Inherited_legacy(100, 2.1, 0, 0, 100, true)   // different in multiplier
    val inhTimingPoint5 = new Inherited_legacy(100, 2.0, 1, 0, 100, true)   // different in sampleset
    val inhTimingPoint6 = new Inherited_legacy(100, 2.0, 0, 1, 100, true)   // different in sampleindex
    val inhTimingPoint7 = new Inherited_legacy(100, 2.0, 0, 0, 99, true)    // different in volume
    val inhTimingPoint8 = new Inherited_legacy(100, 2.0, 0, 0, 100, false)  // different in kiai

    assert(inhTimingPoint == inhTimingPoint2)
    assert(inhTimingPoint != inhTimingPoint3)
    assert(inhTimingPoint != inhTimingPoint4)
    assert(inhTimingPoint != inhTimingPoint5)
    assert(inhTimingPoint != inhTimingPoint6)
    assert(inhTimingPoint != inhTimingPoint7)
    assert(inhTimingPoint != inhTimingPoint8)
  }
}
