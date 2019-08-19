package componentsTest

import components.TimingPoint
import coreTest.BaseTest

class TimingPointTest extends BaseTest{
  "A timing point" should "always return its values correctly" in {
    val timingPoint = new TimingPoint(0, 180, 5, 4)

    assert(timingPoint.time == 0)
    assert(timingPoint.bpm == 180)
    assert(timingPoint.meterA == 5)
    assert(timingPoint.meterB == 4)

    timingPoint.bpm = 160
    assert(timingPoint.bpm == 160)

    timingPoint.time = 235
    assert(timingPoint.time == 235)

    timingPoint.meterA = 7
    assert(timingPoint.meterA == 7)

    timingPoint.meterB = 8
    assert(timingPoint.meterB == 8)
  }

  it should "compare itself properly to other TimeStamps" in {
    val timingPoint = new TimingPoint(0, 180, 4, 4)
    val timingPoint2 = new TimingPoint(0, 180, 4, 4) // equal to timingPoint
    val timingPoint3 = new TimingPoint(5, 180, 4, 4) // different in time
    val timingPoint4 = new TimingPoint(0, 188, 4, 4) // different in bpm
    val timingPoint5 = new TimingPoint(0, 180, 5, 4) // different in meterA
    val timingPoint6 = new TimingPoint(0, 180, 4, 8) // different in meterB

    assert(timingPoint == timingPoint2)
    assert(timingPoint != timingPoint3)
    assert(timingPoint != timingPoint4)
    assert(timingPoint != timingPoint5)
    assert(timingPoint != timingPoint6)

  }

  it should "detect errors in meters" in {
    assertThrows[IllegalArgumentException] {
      new TimingPoint(50, 133.5, 0, 4)
    }
    assertThrows[IllegalArgumentException] {
      new TimingPoint(50, 133.5, 2, 0)
    }
    assertThrows[IllegalArgumentException] {
      val timingPoint = new TimingPoint(50, 133.5, 2, 4)
      timingPoint.meterA = 0
    }
    assertThrows[IllegalArgumentException] {
      val timingPoint = new TimingPoint(50, 133.5, 2, 4)
      timingPoint.meterB = 0
    }
  }

  it should "detect BPM errors" in {
    assertThrows[IllegalArgumentException] {
      new TimingPoint(50, 0, 2, 4)
    }
    assertThrows[IllegalArgumentException] {
      val timingPoint = new TimingPoint(50, 133.5, 2, 4)
      timingPoint.bpm = 0
    }
    assertThrows[IllegalArgumentException] {
      new TimingPoint(50, -50, 2, 4)
    }
    assertThrows[IllegalArgumentException] {
      val timingPoint = new TimingPoint(50, 133.5, 2, 4)
      timingPoint.bpm = -50
    }
  }
}
