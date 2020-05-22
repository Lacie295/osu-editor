package componentsTest

import core.ObjectHandler._
import coreTest.BaseTest

class SliderTest extends BaseTest {
  "A slider" should "keep the timestamp for beginning and end correctly" in {
    timestamp = 3
    endtimestamp = 4
    position = (0, 0)

    val s = MakeSlider

    assert(s.time == 3)
    assert(s.endTime == 4)
    s.time = 1
    assert(s.time == 1)
    s.endTime = 5
    assert(s.endTime == 5)
  }

  it should "never end before it begins" in {
    assertThrows[IllegalArgumentException] {
      timestamp = 3
      endtimestamp = 1
      MakeSlider
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 2
      endtimestamp = 3
      val s = MakeSlider
      s.endTime = 1
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 2
      endtimestamp = 3
      val s = MakeSlider
      s.time = 5
    }
  }

  it should "always detect overlaps properly" in {
    timestamp = 1
    endtimestamp = 4
    val slider = MakeSlider

    timestamp = 0
    endtimestamp = 5
    val slider2 = MakeSlider //slider2 contains slider

    timestamp = 4
    endtimestamp = 5
    val slider3 = MakeSlider //slider3 is on slider's end

    timestamp = 0
    endtimestamp = 1
    val slider4 = MakeSlider //slider4 is on slider's start

    timestamp = 2
    endtimestamp = 3
    val slider5 = MakeSlider //slider contains slider5

    timestamp = 16
    endtimestamp = 20
    val slider6 = MakeSlider //slider6 and slider are unrelated

    assert(slider overlaps slider2)
    assert(slider overlaps slider3)
    assert(slider overlaps slider4)
    assert(slider overlaps slider5)
    assert(!(slider overlaps slider6))

    timestamp = 0
    endtimestamp = 5
    val spin = MakeSpinner //spin contains slider

    timestamp = 4
    endtimestamp = 5
    val spin2 = MakeSpinner //spin2 is on slider's end

    timestamp = 0
    endtimestamp = 1
    val spin3 = MakeSpinner //spin3 is on slider's start

    timestamp = 2
    endtimestamp = 3
    val spin4 = MakeSpinner //slider contains spin4

    timestamp = 16
    endtimestamp = 20
    val spin5 = MakeSpinner //slider and spin5 are unrelated

    assert(slider overlaps spin)
    assert(slider overlaps spin2)
    assert(slider overlaps spin3)
    assert(slider overlaps spin4)
    assert(!(slider overlaps spin5))

    timestamp = 1
    val circle = MakeCircle //circle is on slider's start

    timestamp = 4
    val circle2 = MakeCircle //circle2 is on slider's end

    timestamp = 2
    val circle3 = MakeCircle //slider contains circle3

    timestamp = 6
    val circle4 = MakeCircle //slider and circle4 are unrelated

    assert(slider overlaps circle)
    assert(slider overlaps circle2)
    assert(slider overlaps circle3)
    assert(!(slider overlaps circle4))
  }

  it should "properly store, add, remove and return nodes" in {
    timestamp = 0
    endtimestamp = 1
    val s = MakeSlider
    s.addNode(1, 1).nodeType = 1
    assert(s.size == 2)

    val head = s(0)
    assert(head.x == 0)
    assert(head.y == 0)
    assert(head.nodeType == 1)

    val last = s(1)
    assert(last.x == 1)
    assert(last.y == 1)
    assert(last.nodeType == 1)

    s.addNode(2, 2).nodeType = 0
    s.addNode(1, (3, 3)).nodeType = 1
    s.x = 3

    assert(s.size == 4)

    assert(head.x == 3)
    assert(head.y == 0)
    assert(head.nodeType == 1)

    val first = s(1)
    assert(first.x == 3)
    assert(first.y == 3)
    assert(first.nodeType == 1)

    val second = s(2)
    assert(second.x == 1)
    assert(second.y == 1)
    assert(second.nodeType == 1)

    val third = s(3)
    assert(third.x == 2)
    assert(third.y == 2)
    assert(third.nodeType == 0)

    s removeNode 1
    val second2 = s(2)
    assert(second2 == third)

    s(1).nodeType = 1
    assert(s(1).nodeType == 1)
  }
}

