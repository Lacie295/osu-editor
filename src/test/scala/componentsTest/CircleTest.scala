package componentsTest

import components.{Circle, Slider, Spinner}
import coreTest.BaseTest

class CircleTest extends BaseTest {
  "A Circle" should "always return its position properly" in {
    val circle = new Circle((0, 1), 0)
    assert(circle.x == 0)
    assert(circle.y == 1)

    circle.x = 5
    assert(circle.x == 5)

    circle.y = 6
    assert(circle.y == 6)

  }

  it should "always return its time properly" in {
    val circle = new Circle((0, 1), 8)
    assert(circle.time == 8)
    circle.time = 9
    assert(circle.time == 9)
  }

  it should "be comparable to other circles" in {
    val circle = new Circle((0, 1), 5)
    val circle2 = new Circle((0, 2), 5)       //circle2 different to circle in Position
    val circle3 = new Circle((0, 1), 6)       //circle3 different to circle in Time
    val circle4 = new Circle((0, 1), 5)       //circle4 equal to circle
    assert(!(circle == circle2))
    assert(!(circle == circle3))
    assert(circle == circle4)
  }

  it should "detect overlaps correctly" in {
    val circle = new Circle((0, 0), 4)
    val circle2 = new Circle((0, 0), 4)     //circle2 is on circle
    val circle3 = new Circle((0, 0), 5)     //circle3 is unrelated to circle

    assert(circle overlaps circle2)
    assert(!(circle overlaps circle3))

    val slider = new Slider((0, 0), (100, 101), 1, 4)   //circle is on end of slider
    val slider2 = new Slider((0, 0), (100, 101), 1, 5)  //slider2 contains circle
    val slider3 = new Slider((0, 0), (100, 101), 4, 5)  //circle is on end of slider3
    val slider4 = new Slider((0, 0), (100, 101), 1, 3)  //slider4 and circle are unrelated

    assert(circle overlaps slider)
    assert(circle overlaps slider2)
    assert(circle overlaps slider3)
    assert(!(circle overlaps slider4))

    val spin = new Spinner(1, 4)   //circle is on end of spin
    val spin2 = new Spinner(1, 5)  //spin2 contains circle
    val spin3 = new Spinner(4, 5)  //circle is on end of spin3
    val spin4 = new Spinner(1, 3)  //spin4 and circle are unrelated

    assert(circle overlaps spin)
    assert(circle overlaps spin2)
    assert(circle overlaps spin3)
    assert(!(circle overlaps spin4))
  }

}
