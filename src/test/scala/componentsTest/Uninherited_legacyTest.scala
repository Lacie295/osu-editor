package componentsTest

import core.ObjectHandler._
import coreTest.BaseTest

class Uninherited_legacyTest extends BaseTest {
  "An uninherited timing point" should "always return its values correctly" in {
    timestamp = 100
    bpm = 200
    meterA = 4
    sampleset = 0
    sampleindex = 0
    volume = 100
    kiai = true

    val uninhTimingPoint = MakeUninherited

    assert(uninhTimingPoint.time == 100)
    assert(uninhTimingPoint.bpm == 200)
    assert(uninhTimingPoint.meter == 4)
    assert(uninhTimingPoint.sampleSet == 0)
    assert(uninhTimingPoint.sampleIndex == 0)
    assert(uninhTimingPoint.volume == 100)
    assert(uninhTimingPoint.kiai)

    uninhTimingPoint.time = 105
    assert(uninhTimingPoint.time == 105)

    uninhTimingPoint.bpm = 205
    assert(uninhTimingPoint.bpm == 205)

    uninhTimingPoint.meter = 6
    assert(uninhTimingPoint.meter == 6)

    uninhTimingPoint.sampleSet = 2
    assert(uninhTimingPoint.sampleSet == 2)

    uninhTimingPoint.sampleIndex = 1
    assert(uninhTimingPoint.sampleIndex == 1)

    uninhTimingPoint.volume = 95
    assert(uninhTimingPoint.volume == 95)

    uninhTimingPoint.kiai = false
    assert(!uninhTimingPoint.kiai)
  }

  it should "always compare correctly to other uninherited timing points" in {
    timestamp = 100
    bpm = 200
    meterA = 4
    sampleset = 0
    sampleindex = 0
    volume = 100
    kiai = true
    val uninhTimingPoint = MakeUninherited //comparer
    val uninhTimingPoint2 = MakeUninherited //equal

    timestamp = 101
    bpm = 200
    meterA = 4
    sampleset = 0
    sampleindex = 0
    volume = 100
    kiai = true
    val uninhTimingPoint3 = MakeUninherited //different in time

    timestamp = 100
    bpm = 205
    meterA = 4
    sampleset = 0
    sampleindex = 0
    volume = 100
    kiai = true
    val uninhTimingPoint4 = MakeUninherited //different in bpm

    timestamp = 100
    bpm = 200
    meterA = 6
    sampleset = 0
    sampleindex = 0
    volume = 100
    kiai = true
    val uninhTimingPoint5 = MakeUninherited //different in meter

    timestamp = 100
    bpm = 200
    meterA = 4
    sampleset = 1
    sampleindex = 0
    volume = 100
    kiai = true
    val uninhTimingPoint6 = MakeUninherited //different in sampleset

    timestamp = 100
    bpm = 200
    meterA = 4
    sampleset = 0
    sampleindex = 2
    volume = 100
    kiai = true
    val uninhTimingPoint7 = MakeUninherited //different in sampleindex

    timestamp = 100
    bpm = 200
    meterA = 4
    sampleset = 0
    sampleindex = 0
    volume = 95
    kiai = true
    val uninhTimingPoint8 = MakeUninherited //different in volume

    timestamp = 100
    bpm = 200
    meterA = 4
    sampleset = 0
    sampleindex = 0
    volume = 100
    kiai = false
    val uninhTimingPoint9 = MakeUninherited //different in kiai

    assert(uninhTimingPoint == uninhTimingPoint2)
    assert(uninhTimingPoint != uninhTimingPoint3)
    assert(uninhTimingPoint != uninhTimingPoint4)
    assert(uninhTimingPoint != uninhTimingPoint5)
    assert(uninhTimingPoint != uninhTimingPoint6)
    assert(uninhTimingPoint != uninhTimingPoint7)
    assert(uninhTimingPoint != uninhTimingPoint8)
    assert(uninhTimingPoint != uninhTimingPoint9)
  }
}
