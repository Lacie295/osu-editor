package utilsTest

import coreTest.BaseTest

import utils.Position

class PositionTest extends BaseTest {
  "A Position" should "return its constructor and setter parameters properly" in {
    val pos = new Position(3, 2)
    assert(pos.getX == 3)
    assert(pos.getY == 2)
    pos.setX(4)
    pos.setY(6)
    assert(pos.getX == 4)
    assert(pos.getY == 6)
  }

  it should "be comparable to other Positions" in {
    val pos = new Position(3, 2)
    val pos2 = new Position(3, 2)
    val pos3 = new Position(4, 2)
    val pos4 = new Position(3, 1)
    val pos5 = new Position(1, 5)

    assert(pos == pos2)
    assert(pos != pos3)
    assert(pos != pos4)
    assert(pos != pos5)
  }
}
