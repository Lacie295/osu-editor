package componentsTest

import components.Uninherited_legacy
import coreTest.BaseTest

class Uninherited_legacyTest extends BaseTest {
  "An uninherited timing point" should "always return its values correctly" in {
    val uninhTimingPoint = new Uninherited_legacy(100, 200, 4, 0, 0, 100, true)

    assert(uninhTimingPoint.time == 100)
    assert(uninhTimingPoint.BPM == 200)
    assert(uninhTimingPoint.meter == 4)
    assert(uninhTimingPoint.sampleSet == 0)
    assert(uninhTimingPoint.sampleIndex == 0)
    assert(uninhTimingPoint.volume == 100)
    assert(uninhTimingPoint.kiai)

    uninhTimingPoint.time = 105
    assert(uninhTimingPoint.time == 105)

    uninhTimingPoint.BPM = 205
    assert(uninhTimingPoint.BPM == 205)

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
    val uninhTimingPoint = new Uninherited_legacy(100, 200, 4, 0, 0, 100, true)   //comparer
    val uninhTimingPoint2 = new Uninherited_legacy(100, 200, 4, 0, 0, 100, true)  //equal
    val uninhTimingPoint3 = new Uninherited_legacy(101, 200, 4, 0, 0, 100, true)  //different in time
    val uninhTimingPoint4 = new Uninherited_legacy(100, 205, 4, 0, 0, 100, true)  //different in bpm
    val uninhTimingPoint5= new Uninherited_legacy(100, 200, 6, 0, 0, 100, true)   //different in meter
    val uninhTimingPoint6 = new Uninherited_legacy(100, 200, 4, 1, 0, 100, true)  //different in sampleset
    val uninhTimingPoint7 = new Uninherited_legacy(100, 200, 4, 0, 2, 100, true)  //different in sampleindex
    val uninhTimingPoint8 = new Uninherited_legacy(100, 200, 4, 0, 0, 95, true)   //different in volume
    val uninhTimingPoint9 = new Uninherited_legacy(100, 200, 4, 0, 0, 100, false) //different in kiai

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
