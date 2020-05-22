package coreTest

import core.Map
import core.ObjectHandler._
import core.MapHandler._

class MapHandlerTest extends BaseTest {
  "A handler" should "properly translate to/from a map and add/remove objects properly" in {
    val m = new Map()

    timestamp = 10
    position = (1, 0)
    val circle = MakeCircle
    m += circle

    assert(circle.timeStamp == MakeTimeStamp (10))
    assert(circle.position == MakePosition (1, 0))
    assert(circle == (m getCircle 10))

    timestamp = 12
    endtimestamp = 25
    val slider = MakeSlider
    m += slider

    assert(slider == (m getSlider 12))

    val spinner = MakeSpinner
    m += spinner

    assert(spinner == (m getSpinner 12))

    val timingPoint = MakeTimingPoint
    m += timingPoint

    assert(timingPoint == (m getTimingPoint 12))
  }

  it should "throw errors when accessing nonexistent elements"  in {
    timestamp = 0
    val m = new Map()
    assertThrows[IndexOutOfBoundsException] {
      m getCircle 0
    }
    assertThrows[IndexOutOfBoundsException] {
      m getSlider 0
    }
    assertThrows[IndexOutOfBoundsException] {
      m getSpinner 0
    }

    val circle = MakeCircle
    m += circle
    assert((m getCircle 0) == circle)
    assertThrows[IndexOutOfBoundsException] {
      m getSlider 0
    }
    assertThrows[IndexOutOfBoundsException] {
      m getSpinner 0
    }
    m -= circle

    val slider = MakeSlider
    m += slider
    assertThrows[IndexOutOfBoundsException] {
      m getCircle 0
    }
    assert((m getSlider 0) == slider)
    assertThrows[IndexOutOfBoundsException] {
      m getSpinner 0
    }
    m -= slider

    val spinner = MakeSpinner
    m += spinner
    assertThrows[IndexOutOfBoundsException] {
      m getCircle 0
    }
    assertThrows[IndexOutOfBoundsException] {
      m getSlider 0
    }
    assert((m getSpinner 0) == spinner)
    m -= spinner
  }
}
