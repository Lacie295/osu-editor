package componentsTest

import core.ObjectHandler._
import coreTest.BaseTest

class TimingPointTest extends BaseTest {
  "A timing point" should "always return its values correctly" in {
    timestamp = 0
    bpm = 180
    meterA = 5
    meterB = 4

    val timingPoint = MakeTimingPoint

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
    timestamp = 0
    bpm = 180
    meterA = 4
    meterB = 4
    val timingPoint = MakeTimingPoint
    val timingPoint2 = MakeTimingPoint // equal to timingPoint

    timestamp = 5
    bpm = 180
    meterA = 4
    meterB = 4
    val timingPoint3 = MakeTimingPoint // different in time

    timestamp = 0
    bpm = 188
    meterA = 4
    meterB = 4
    val timingPoint4 = MakeTimingPoint // different in bpm

    timestamp = 0
    bpm = 180
    meterA = 5
    meterB = 4
    val timingPoint5 = MakeTimingPoint // different in meterA

    timestamp = 0
    bpm = 180
    meterA = 4
    meterB = 8
    val timingPoint6 = MakeTimingPoint // different in meterB

    assert(timingPoint == timingPoint2)
    assert(timingPoint != timingPoint3)
    assert(timingPoint != timingPoint4)
    assert(timingPoint != timingPoint5)
    assert(timingPoint != timingPoint6)

  }

  it should "detect errors in meters" in {
    assertThrows[IllegalArgumentException] {
      timestamp = 50
      bpm = 133.5
      meterA = 0
      meterB = 4

      MakeTimingPoint
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 50
      bpm = 133.5
      meterA = 2
      meterB = 0

      MakeTimingPoint
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 50
      bpm = 133.5
      meterA = 2
      meterB = 4

      val timingPoint = MakeTimingPoint
      timingPoint.meterA = 0
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 50
      bpm = 133.5
      meterA = 0
      meterB = 4

      val timingPoint = MakeTimingPoint
      timingPoint.meterB = 0
    }
  }

  it should "detect BPM errors" in {
    assertThrows[IllegalArgumentException] {
      timestamp = 50
      bpm = 0
      meterA = 0
      meterB = 4

      MakeTimingPoint
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 50
      bpm = 133.5
      meterA = 2
      meterB = 4

      val timingPoint = MakeTimingPoint
      timingPoint.bpm = 0
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 50
      bpm = -50
      meterA = 0
      meterB = 4

      MakeTimingPoint
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 50
      bpm = 133.5
      meterA = 2
      meterB = 4

      val timingPoint = MakeTimingPoint
      timingPoint.bpm = -50
    }
  }
}
