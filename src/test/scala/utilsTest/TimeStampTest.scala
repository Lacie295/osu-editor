package utilsTest

import coreTest.BaseTest
import utils.TimeStamp

class TimeStampTest extends BaseTest {
  "A TimeStamp" should "return its contructor and setter paramters correctly" in {
    val t = new TimeStamp(3)
    assert(t.getTime == 3)
    t.setTime(4)
    assert(t.getTime == 4)
  }

  it should "compare itself correctly to other timestamps" in {
    val t = new TimeStamp(3)
    val t2 = new TimeStamp(4)
    assert(t.compareTo(t2) < 0)
    t.setTime(4)
    assert(t.compareTo(t2) == 0)
    t.setTime(5)
    assert(t.compareTo(t2) > 0)
  }
}
