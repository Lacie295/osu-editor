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
}
