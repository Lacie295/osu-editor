package utilsTest

import coreTest.BaseTest
import core.ObjectHandler._

class AdditionTest extends BaseTest {
 "An Addition" should "always return its values correctly" in {
   val addition = MakeAddition

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
    val addition = MakeAddition
    val addition1 = MakeAddition (2, 3, false)
    val addition2 = MakeAddition (2, 2, false)
    val addition3 = MakeAddition (0, 0, false)
    val addition4 = MakeAddition (0, 0, true)
    val addition5 = MakeAddition (2, 3)

    assert(addition == addition3)
    assert(addition1 != addition2)
    assert(addition4 != addition)
    assert(addition5 == addition1)
  }
}
