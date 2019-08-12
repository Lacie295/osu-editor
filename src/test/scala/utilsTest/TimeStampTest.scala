package utilsTest

import coreTest.BaseTest
import utils.TimeStamp

class TimeStampTest extends BaseTest {
  "A TimeStamp" should "return its contructor and setter paramters correctly" in {
    val t = new TimeStamp(3)
    assert(t.time == 3)
    t.time = 4
    assert(t.time == 4)
  }

  it should "compare itself correctly to other timestamps" in {
    val t = new TimeStamp(3)
    val t2 = new TimeStamp(4)
    assert(t < t2)
    assert(t != t2)
    t.time = 4
    assert(t == t2)
    t.time = 5
    assert(t > t2)
    assert(t != t2)
  }
}
