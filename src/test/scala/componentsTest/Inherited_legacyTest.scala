package componentsTest

import core.ObjectHandler._
import coreTest.BaseTest

class Inherited_legacyTest extends BaseTest {
  "An inherited timing point" should "always return its values correctly" in {
    timestamp = 100
    multiplier = 2
    sampleset = 0
    sampleindex = 0
    volume = 100
    kiai = true

    val inhTimingPoint = MakeInherited

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
    timestamp = 100
    multiplier = 2
    sampleset = 0
    sampleindex = 0
    volume = 100
    kiai = true

    val inhTimingPoint = MakeInherited // comparer
    val inhTimingPoint2 = MakeInherited // equal

    timestamp = 101
    val inhTimingPoint3 = MakeInherited // different in time

    timestamp = 100
    multiplier = 2.1
    val inhTimingPoint4 = MakeInherited // different in multiplier

    multiplier = 2.0
    sampleset = 1
    val inhTimingPoint5 = MakeInherited // different in sampleset

    sampleset = 0
    sampleindex = 1
    val inhTimingPoint6 = MakeInherited // different in sampleindex

    sampleindex = 0
    volume = 99
    val inhTimingPoint7 = MakeInherited // different in volume

    volume = 100
    kiai = false
    val inhTimingPoint8 = MakeInherited // different in kiai

    assert(inhTimingPoint == inhTimingPoint2)
    assert(inhTimingPoint != inhTimingPoint3)
    assert(inhTimingPoint != inhTimingPoint4)
    assert(inhTimingPoint != inhTimingPoint5)
    assert(inhTimingPoint != inhTimingPoint6)
    assert(inhTimingPoint != inhTimingPoint7)
    assert(inhTimingPoint != inhTimingPoint8)
  }
}
