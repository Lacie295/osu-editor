package componentsTest

import components.Circle
import coreTest.BaseTest
import utils.Position

class CircleTest extends BaseTest {
  "A Circle" should "always return its position properly" in {
    val circle = new Circle(new Position(0, 1), 0)
    assert(circle.getX == 0)
    assert(circle.getY == 1)
    assert(circle.getPosition == new Position(0, 1))

    circle.setX(5)
    assert(circle.getX == 5)

    circle.setY(6)
    assert(circle.getY == 6)

  }

  it should "always return its time properly" in {
    val circle = new Circle(new Position(0, 1), 8)
    assert(circle.getTime == 8)
    circle.setTime(9)
    assert(circle.getTime == 9)
  }

  it should "be comparable to other circles" in {
    val circle = new Circle(new Position(0, 1), 5)
    val circle2 = new Circle(new Position(0, 2), 5)       //circle2 different to circle in Position
    val circle3 = new Circle(new Position(0, 1), 6)       //circle3 different to circle in Time
    val circle4 = new Circle(new Position(0, 1), 5)       //circle4 equal to circle
    assert(!(circle == circle2))
    assert(!(circle == circle3))
    assert(circle == circle4)
  }


}
