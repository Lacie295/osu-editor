package coreTest

import core.ObjectHandler._

class ObjectHandlerTest extends BaseTest {
  "A handler" should "properly store data" in {
    timestamp = 15
    endtimestamp = 25
    position = (1, 1)
    repeat = 1
    BPM = 120
    multiplier = 2
    sampleset = 4
    sampleindex = 3

    assert(hitsound == MakeHitsound(4, 3))

    volume = 70
    kiai = false
    meterA = 5
    meterB = 8
    hitsound = (1, 5)

    assert(timestamp == MakeTimeStamp(15))
    assert(endtimestamp == MakeTimeStamp(25))
    assert(position == MakePosition(1, 1))
    assert(repeat == 1)
    assert(BPM == 120)
    assert(multiplier == 2)
    assert(sampleset == 1)
    assert(sampleindex == 5)
    assert(volume == 70)
    assert(!kiai)
    assert(meterA == 5)
    assert(meterB == 8)
    assert(hitsound == MakeHitsound(1, 5))
  }

  it should "properly create objects" in {
    timestamp = 15
    endtimestamp = 25
    position = (1, 1)
    repeat = 1
    BPM = 120
    multiplier = 2
    volume = 70
    kiai = false
    meterA = 5
    meterB = 8
    hitsound = (1, 5)

    val c = MakeCircle
    assert(c.timeStamp == MakeTimeStamp(15))
    assert(c.position == MakePosition(1, 1))
    assert(c.hitsound == MakeHitsound(1, 5))

    timestamp = 20
    endtimestamp = 25
    position = (25, 25)

    val s = MakeSlider
    s.addNode(26, 27).nodeType = 0
    s.addNode(50, 10).nodeType = 1

    assert(s.timeStamp == MakeTimeStamp(20))
    assert(s.endTimeStamp == MakeTimeStamp(25))
    assert(s.position == MakePosition(25, 25))
    assert(s.velocity == 2)
    assert(s.repeats == 1)
    assert(s(0).position == MakePosition(25, 25))
    assert(s(1).position == MakePosition(26, 27))
    assert(s(1).nodeType == 0)
    assert(s(2).position == MakePosition(50, 10))
    assert(s(2).nodeType == 1)
    assert(s.hitsound == c.hitsound)

    val s2 = MakeSpinner

    assert(s2.timeStamp == s.timeStamp)
    assert(s2.endTimeStamp == s.endTimeStamp)
    assert(s2.hitsound == s.hitsound)

    val t = MakeTimingPoint

    assert(t.timeStamp == MakeTimeStamp(20))
    assert(t.bpm == 120)
    assert(t.meterA == 5)
    assert(t.meterB == 8)

    val t1 = MakeInherited

    assert(t1.timeStamp == t.timeStamp)
    assert(t1.svMultiplier == s.velocity)
    assert(t1.sampleSet == 1)
    assert(t1.sampleIndex == 5)
    assert(t1.volume == 70)
    assert(!t1.kiai)

    val t2 = MakeUninherited

    assert(t2.timeStamp == t.timeStamp)
    assert(t2.bpm == t.bpm)
    assert(t2.meter == t.meterA)
    assert(t1.sampleSet == 1)
    assert(t1.sampleIndex == 5)
    assert(t1.volume == 70)
    assert(!t1.kiai)
  }
}
