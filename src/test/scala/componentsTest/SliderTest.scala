package componentsTest

import components.{Slider, Spinner, Circle}
import coreTest.BaseTest

class SliderTest extends BaseTest {
  "A slider" should "keep the timestamp for beginning and end correctly" in {
    val slider = new Slider((0, 0), (100, 100), 3, 4)
    assert(slider.time == 3)
    assert(slider.endTime == 4)
    slider.time = 1
    assert(slider.time == 1)
    slider.endTime = 5
    assert(slider.endTime == 5)
  }

  it should "always return its starting- and end points correctly" in {
    val slider = new Slider((0, 1), (100, 101), 3, 4)
    assert(slider.x == 0)
    assert(slider.y == 1)
    assert(slider.endX == 100)
    assert(slider.endY == 101)

    slider.position = (2, 3)
    assert(slider.x == 2)
    assert(slider.y == 3)

    slider.endPosition = (102, 103)
    assert(slider.endX == 102)
    assert(slider.endY == 103)
  }

  it should "never end before it begins" in {
    assertThrows[IllegalArgumentException] {
      new Slider((0, 0), (5, 5), 3, 1)
    }
    assertThrows[IllegalArgumentException] {
      val slider = new Slider((0, 0), (5, 5), 2, 3)
      slider.endTime = 1
    }
    assertThrows[IllegalArgumentException] {
      val slider = new Slider((0, 0), (5, 5), 2, 3)
      slider.time = 5
    }
  }

  it should "always detect overlaps properly" in {
    val slider = new Slider((0, 0), (100, 101), 1, 4)
    val slider2 = new Slider((0, 0), (100, 101), 0,5)     //slider2 contains slider
    val slider3 = new Slider((0, 0), (100, 101), 4,5)     //slider3 is on slider's end
    val slider4 = new Slider((0, 0), (100, 101), 0,1)     //slider4 is on slider's start
    val slider5 = new Slider((0, 0), (100, 101), 2,3)     //slider contains slider5
    val slider6 = new Slider((0, 0), (100, 101), 16,20)   //slider6 and slider are unrelated

    assert(slider overlaps slider2)
    assert(slider overlaps slider3)
    assert(slider overlaps slider4)
    assert(slider overlaps slider5)
    assert(!(slider overlaps slider6))

    val spin = new Spinner(0, 5)    //spin contains slider
    val spin2 = new Spinner(4, 5)   //spin2 is on slider's end
    val spin3 = new Spinner(0, 1)   //spin3 is on slider's start
    val spin4 = new Spinner(2, 3)   //slider contains spin4
    val spin5 = new Spinner(16, 20) //slider and spin5 are unrelated

    assert(slider overlaps spin)
    assert(slider overlaps spin2)
    assert(slider overlaps spin3)
    assert(slider overlaps spin4)
    assert(!(slider overlaps spin5))

    val circle = new Circle((0,0), 1)     //circle is on slider's start
    val circle2 = new Circle((0,0), 4)    //circle2 is on slider's end
    val circle3 = new Circle((0,0), 2)    //slider contains circle3
    val circle4 = new Circle((0,0), 5)    //slider and circle4 are unrelated

    assert(slider overlaps circle)
    assert(slider overlaps circle2)
    assert(slider overlaps circle3)
    assert(!(slider overlaps circle4))
  }
}

