package componentsTest

import components.{Spinner, Slider, Circle}
import coreTest.BaseTest

class SpinnerTest extends BaseTest {
  "A spinner" should "keep the timestamp for beginning and end correctly" in {
    val spin = new Spinner(3, 4)
    assert(spin.time == 3)
    assert(spin.endTime == 4)
    spin.time = 1
    assert(spin.time == 1)
    spin.endTime = 5
    assert(spin.endTime == 5)
  }

  it should "always have a position of (0, 0)" in {
    val spin = new Spinner(0, 4)
    assert(spin.x == 0)
    assert(spin.y == 0)
  }

  it should "never end before it begins" in {
    assertThrows[IllegalArgumentException] {
      new Spinner(3, 1)
    }
    assertThrows[IllegalArgumentException] {
      val spin = new Spinner(3, 4)
      spin.endTime = 1
    }
    assertThrows[IllegalArgumentException] {
      val spin = new Spinner(3, 4)
      spin.time = 5
    }
  }

  it should "properly detect overlaps" in {
    val spin = new Spinner(1, 4)
    val spin2 = new Spinner(0, 5) // spin2 contains spin
    val spin3 = new Spinner(4, 5) // spin3 is on spin's end
    val spin4 = new Spinner(0, 1) // spin4 is on spin's start
    val spin5 = new Spinner(2, 3) // spin contains spin5
    val spin6 = new Spinner(16, 20) // spin and spin6 are unrelated

    assert(spin overlaps spin2)
    assert(spin overlaps spin3)
    assert(spin overlaps spin4)
    assert(spin overlaps spin5)
    assert(!(spin overlaps spin6))

    val slider = new Slider((0, 0), 0,5)      //slider contains spin
    val slider2 = new Slider((0, 0), 4,5)     //slider2 is on spin's end
    val slider3 = new Slider((0, 0), 0,1)     //slider3 is on spin's start
    val slider4 = new Slider((0, 0), 2,3)     //spin contains slider4
    val slider5 = new Slider((0, 0), 16,20)   //slider and spin are unrelated

    assert(spin overlaps slider)
    assert(spin overlaps slider2)
    assert(spin overlaps slider3)
    assert(spin overlaps slider4)
    assert(!(spin overlaps slider5))

    val circle = new Circle((0,0), 1)     //circle is on spin's start
    val circle2 = new Circle((0,0), 4)    //circle2 is on spin's end
    val circle3 = new Circle((0,0), 2)    //spin contains circle3
    val circle4 = new Circle((0,0), 5)    //spin and circle4 are unrelated

    assert(spin overlaps circle)
    assert(spin overlaps circle2)
    assert(spin overlaps circle3)
    assert(!(spin overlaps circle4))
  }

  it should "be comparable to equal spinners" in {
    val spin = new Spinner(3, 4)
    val spin2 = new Spinner(3, 4)
    val spin3 = new Spinner(2, 5)
    spin3.time = 3
    spin3.endTime = 4
    assert(spin == spin2)
    assert(spin == spin3)
  }
}
