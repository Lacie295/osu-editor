package componentsTest

import core.ObjectHandler._
import coreTest.BaseTest

class SpinnerTest extends BaseTest {
  "A spinner" should "keep the timestamp for beginning and end correctly" in {
    timestamp = 3
    endtimestamp = 4
    val spin = MakeSpinner

    assert(spin.time == 3)
    assert(spin.endTime == 4)
    spin.time = 1
    assert(spin.time == 1)
    spin.endTime = 5
    assert(spin.endTime == 5)
  }

  it should "always have a position of (0, 0)" in {
    timestamp = 0
    endtimestamp = 3
    val spin = MakeSpinner

    assert(spin.x == 0)
    assert(spin.y == 0)
  }

  it should "never end before it begins" in {
    assertThrows[IllegalArgumentException] {
      timestamp = 3
      endtimestamp = 1
      MakeSpinner
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 3
      endtimestamp = 4
      val spin = MakeSpinner
      spin.endTime = 1
    }
    assertThrows[IllegalArgumentException] {
      timestamp = 3
      endtimestamp = 4
      val spin = MakeSpinner
      spin.time = 5
    }
  }

  it should "properly detect overlaps" in {
    timestamp = 1
    endtimestamp = 4
    val spin = MakeSpinner

    timestamp = 0
    endtimestamp = 5
    val spin2 = MakeSpinner // spin2 contains spin

    timestamp = 4
    endtimestamp = 5
    val spin3 = MakeSpinner // spin3 is on spin's end

    timestamp = 0
    endtimestamp = 1
    val spin4 = MakeSpinner // spin4 is on spin's start

    timestamp = 2
    endtimestamp = 3
    val spin5 = MakeSpinner // spin contains spin5

    timestamp = 16
    endtimestamp = 20
    val spin6 = MakeSpinner // spin and spin6 are unrelated

    assert(spin overlaps spin2)
    assert(spin overlaps spin3)
    assert(spin overlaps spin4)
    assert(spin overlaps spin5)
    assert(!(spin overlaps spin6))

    timestamp = 0
    endtimestamp = 5
    val slider = MakeSlider     //slider contains spin

    timestamp = 4
    endtimestamp = 5
    val slider2 = MakeSlider    //slider2 is on spin's end

    timestamp = 0
    endtimestamp = 1
    val slider3 = MakeSlider    //slider3 is on spin's start

    timestamp = 2
    endtimestamp = 3
    val slider4 = MakeSlider    //spin contains slider4

    timestamp = 16
    endtimestamp = 20
    val slider5 = MakeSlider    //slider and spin are unrelated

    assert(spin overlaps slider)
    assert(spin overlaps slider2)
    assert(spin overlaps slider3)
    assert(spin overlaps slider4)
    assert(!(spin overlaps slider5))

    timestamp = 1
    val circle = MakeCircle     //circle is on spin's start

    timestamp = 4
    val circle2 = MakeCircle    //circle2 is on spin's end

    timestamp = 2
    val circle3 = MakeCircle    //spin contains circle3

    timestamp = 5
    val circle4 = MakeCircle    //spin and circle4 are unrelated

    assert(spin overlaps circle)
    assert(spin overlaps circle2)
    assert(spin overlaps circle3)
    assert(!(spin overlaps circle4))
  }

  it should "be comparable to equal spinners" in {
    timestamp = 3
    endtimestamp = 4
    val spin = MakeSpinner
    val spin2 = MakeSpinner

    timestamp = 2
    endtimestamp = 5
    val spin3 = MakeSpinner
    spin3.time = 3
    spin3.endTime = 4
    assert(spin == spin2)
    assert(spin == spin3)
  }
}
