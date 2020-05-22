package utilsTest

import coreTest.BaseTest

import core.ObjectHandler._

class PositionTest extends BaseTest {
  "A Position" should "return its constructor and setter parameters properly" in {
    val pos = MakePosition (3, 2)
    assert(pos.x == 3)
    assert(pos.y == 2)
    pos.x = 4
    pos.y = 6
    assert(pos.x == 4)
    assert(pos.y == 6)
  }

  it should "be comparable to other Positions" in {
    val pos = MakePosition (3, 2)
    val pos2 = MakePosition (3, 2)
    val pos3 = MakePosition (4, 2)
    val pos4 = MakePosition (3, 1)
    val pos5 = MakePosition (1, 5)

    assert(pos == pos2)
    assert(pos != pos3)
    assert(pos != pos4)
    assert(pos != pos5)
  }
}
