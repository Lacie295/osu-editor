package componentsTest

import core.ObjectHandler._
import coreTest.BaseTest

class CircleTest extends BaseTest {
  "A Circle" should "always return its position properly" in {
    timestamp = 0
    position = (0, 1)
    val circle = MakeCircle

    assert(circle.x == 0)
    assert(circle.y == 1)

    circle.x = 5
    assert(circle.x == 5)

    circle.y = 6
    assert(circle.y == 6)

  }

  it should "always return its time properly" in {
    timestamp = 8
    position = (0, 1)
    val circle = MakeCircle

    assert(circle.time == 8)
    circle.time = 9
    assert(circle.time == 9)
  }

  it should "be comparable to other circles" in {
    timestamp = 5
    position = (0, 1)
    val circle = MakeCircle

    position = (0, 2)
    val circle2 = MakeCircle //circle2 different to circle in Position

    timestamp = 6
    position = (0, 1)
    val circle3 = MakeCircle //circle3 different to circle in Time

    timestamp = 5
    val circle4 = MakeCircle //circle4 equal to circle

    assert(!(circle == circle2))
    assert(!(circle == circle3))
    assert(circle == circle4)
  }

  it should "detect overlaps correctly" in {
    position = (0, 0)
    timestamp = 4
    val circle = MakeCircle
    val circle2 = MakeCircle //circle2 is on circle

    timestamp = 5
    val circle3 = MakeCircle //circle3 is unrelated to circle

    assert(circle overlaps circle2)
    assert(!(circle overlaps circle3))

    timestamp = 1
    endtimestamp = 4
    val slider = MakeSlider //circle is on end of slider

    endtimestamp = 5
    val slider2 = MakeSlider //slider2 contains circle

    timestamp = 4
    val slider3 = MakeSlider //circle is on end of slider3

    timestamp = 1
    endtimestamp = 3
    val slider4 = MakeSlider //slider4 and circle are unrelated

    assert(circle overlaps slider)
    assert(circle overlaps slider2)
    assert(circle overlaps slider3)
    assert(!(circle overlaps slider4))

    timestamp = 1
    endtimestamp = 4
    val spin = MakeSpinner //circle is on end of spin

    endtimestamp = 5
    val spin2 = MakeSpinner //spin2 contains circle

    timestamp = 4
    val spin3 = MakeSpinner //circle is on end of spin3

    timestamp = 1
    endtimestamp = 3
    val spin4 = MakeSpinner //spin4 and circle are unrelated

    assert(circle overlaps spin)
    assert(circle overlaps spin2)
    assert(circle overlaps spin3)
    assert(!(circle overlaps spin4))
  }

}
