package utilsTest

import coreTest.BaseTest
import utils.Addition

class AdditionTest extends BaseTest {
 "An Addition" should "always return its values correctly" in {
   val addition = new Addition()

   assert(addition.sampleIndex == 0)
   assert(addition.sampleSet == 0)
   assert(!addition.active)

   addition.sampleIndex = 3
   assert(addition.sampleIndex == 3)

   addition.sampleSet = 2
   assert(addition.sampleSet == 2)

   addition.active = true
   assert(addition.active)
 }

  "An addition" should "always compare itself correctly to other additions" in {
    val addition = new Addition()
    val addition1 = new Addition(2, 3, false)
    val addition2 = new Addition(2, 2, false)
    val addition3 = new Addition(0, 0, false)
    val addition4 = new Addition(0, 0, true)
    val addition5 = new Addition(2, 3)

    assert(addition == addition3)
    assert(addition1 != addition2)
    assert(addition4 != addition)
    assert(addition5 == addition1)
  }
}
