package utilsTest

import coreTest.BaseTest
import utils.BinarySearchTree

import scala.collection.mutable.ListBuffer

class BinarySearchTreeTest extends BaseTest {
  "A BST " should " properly add and remove items" in {
    val bst: BinarySearchTree[Int, Int] = BinarySearchTree[Int, Int]()

    bst.insert(1, 1)
    bst.insert(4, 5)
    bst.insert(3, 2)
    bst.insert(0, 1)

    assert(bst(1) == List(1))
    assert(bst(4) == List(5))
    assert(bst(3) == List(2))
    assert(bst(0) == List(1))
    assert(bst(2) == bst(1))

    bst.insert(1, 2)
    assert(bst.delete(4) == List(5))
    assert(bst(1) == List(1, 2))
    assert(bst(4) == bst(3))
    assert(bst.delete(1, 1) == 1)
    assert(bst(1) == List(2))
  }

  it should " keep its ordering properly" in {
    val bst: BinarySearchTree[Int, Int] = BinarySearchTree[Int, Int]()

    bst.insert(1, 1)
    bst.insert(1, 6)
    bst.insert(4, 5)
    bst.insert(3, 2)
    bst.insert(0, 1)

    var l: ListBuffer[Int] = new ListBuffer[Int]

    for (a <- bst) {
      l += a
    }

    assert(l(0) == 1)
    assert(l(1) == 1)
    assert(l(2) == 6)
    assert(l(3) == 2)
    assert(l(4) == 5)
  }
}
