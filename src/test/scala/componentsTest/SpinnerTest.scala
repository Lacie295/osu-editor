package componentsTest

import components.Spinner
import coreTest.BaseTest

class SpinnerTest extends BaseTest {
  "A spinner" should "keep the timestamp for beginning and end correctly" in {
    val spin = new Spinner(3, 4)
    assert(spin.getTime == 3)
    assert(spin.getEndTime == 4)
    spin.setTime(1)
    assert(spin.getTime == 1)
    spin.setEndTime(5)
    assert(spin.getEndTime == 5)
  }

  it should "always have a position of (0, 0)" in {
    val spin = new Spinner(0, 4)
    assert(spin.getX == 0)
    assert(spin.getY == 0)
    assert(spin.getEndX == 0)
    assert(spin.getEndY == 0)
  }

  it should "never end before it begins" in {
    assertThrows[IllegalArgumentException] {
      val spin = new Spinner(3, 1)
    }
    assertThrows[IllegalArgumentException] {
      val spin = new Spinner(3, 4)
      spin.setEndTime(1)
    }
    assertThrows[IllegalArgumentException] {
      val spin = new Spinner(3, 4)
      spin.setTime(5)
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
  }

  it should "be comparable to equal spinners" in {
    val spin = new Spinner(3, 4)
    val spin2 = new Spinner(3, 4)
    val spin3 = new Spinner(2, 5)
    spin3.setTime(3)
    spin3.setEndTime(4)
    assert(spin == spin2)
    assert(spin == spin3)
  }
}
