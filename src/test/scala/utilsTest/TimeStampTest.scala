package utilsTest

import coreTest.BaseTest
import core.ObjectHandler._

class TimeStampTest extends BaseTest {
  "A TimeStamp" should "return its contructor and setter paramters correctly" in {
    val t = MakeTimeStamp (3)
    assert(t.time == 3)
    t.time = 4
    assert(t.time == 4)
  }

  it should "compare itself correctly to other timestamps" in {
    val t = MakeTimeStamp (3)
    val t2 = MakeTimeStamp (4)
    assert(t < t2)
    assert(t != t2)
    t.time = 4
    assert(t == t2)
    t.time = 5
    assert(t > t2)
    assert(t != t2)
  }
}
