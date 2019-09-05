package utils

import scala.collection.mutable.ListBuffer
import util.control.Breaks._

abstract sealed class Colour

case object Red extends Colour

case object Black extends Colour

class BinarySearchTree[K, V]()(implicit ev$1: K => Comparable[K]) {
  private val _head: Root = new Root()

  // Generic node, can be a leaf, internal node, or the root
  trait TreeNode {
    def isLeaf: Boolean
    def isRoot: Boolean
  }

  // Root node, only has one child, no parents, doesn't store values
  private class Root() extends TreeNode {
    private var _head: InternalNode = new Leaf(this)

    def child: InternalNode = _head

    def child_=(value: InternalNode): Unit = _head = value

    def isLeaf: Boolean = false
    def isRoot: Boolean = true
  }

  // Non root node of the tree, has a parent
  trait InternalNode extends TreeNode {
    def parent: TreeNode

    def parent_=(value: TreeNode): Unit

    def colour: Colour = Black
  }

  // Leaf node, has a parent, no children and can't store values
  private class Leaf(n: TreeNode) extends InternalNode {
    private var _parent: TreeNode = n

    def parent: TreeNode = _parent

    def parent_=(value: TreeNode): Unit = _parent = value

    def isLeaf: Boolean = true
    def isRoot: Boolean = false
  }

  // A normal internal node, has a parent, two children (by default leaves) and can store key-value relations
  private class Node(p: TreeNode, k: K) extends InternalNode {
    private var _colour: Colour = Black

    override def colour: Colour = _colour

    def colour_=(value: Colour): Unit = _colour = value

    private var _parent: TreeNode = p
    private var _left: InternalNode = new Leaf(this)
    private var _right: InternalNode = new Leaf(this)

    def parent: TreeNode = _parent
    def left: InternalNode = _left
    def right: InternalNode = _right

    def parent_=(value: TreeNode): Unit = _parent = value
    def left_=(value: InternalNode): Unit = _left = value
    def right_=(value: InternalNode): Unit = _right = value

    private val _key: K = k
    private var _value: List[V] = List()

    def key: K = _key
    def value: List[V] = _value

    def value_=(value: List[V]): Unit = _value = value
    def addValue(value: V): Unit = _value :+= value
    def removeValue(value: V): Unit = _value = _value.filter(_ != value)

    def isLeaf: Boolean = false
    def isRoot: Boolean = false

    // Rotate the node left
    def rotateLeft(): Unit = {
      // r is gonna replace this as head of the subtree
      val r: TreeNode = right
      val p: TreeNode = parent
      if (r.isLeaf)
        throw new IllegalArgumentException("RotateLeft requires a non empty right node")
      val newTop: Node = r.asInstanceOf[Node]

      // Take the left child of newTop and put this as left child of newTop
      right = newTop.left
      right.parent = this
      newTop.left = this
      parent = newTop

      // Put newTop as child of parent
      p match {
        case pRoot: Root =>
          pRoot.child = newTop
        case pNode: Node =>
          if (this == pNode.left) {
            pNode.left = newTop
          } else {
            pNode.right = newTop
          }
        case _ =>
          throw new RuntimeException("Non root or internal node as parent")
      }

      // Update newTop's parent
      newTop.parent = p
    }

    // Rotate the node right
    def rotateRight(): Unit = {
      // l is gonna replace this as head of the subtree
      val l: InternalNode = left
      val p: TreeNode = parent
      if (l.isLeaf)
        throw new IllegalArgumentException("RotateRight requires a non empty left node")
      val newTop: Node = l.asInstanceOf[Node]

      // Take the right child of newTop and put this as right child of newTop
      left = newTop.right
      left.parent = this
      newTop.right = this
      parent = newTop

      // Put newTop as child of parent
      p match {
        case pRoot: Root =>
          pRoot.child = newTop
        case pNode: Node =>
          if (this == pNode.right) {
            pNode.right = newTop
          } else {
            pNode.left = newTop
          }
        case _ =>
          throw new RuntimeException("Non root or internal node as parent")
      }

      // Update newTop's parent
      newTop.parent = p
    }

    def sibling: InternalNode = {
      if (parent.isRoot) {
        throw new IllegalArgumentException("Can't get sibling of root!")
      }

      val p: Node = parent.asInstanceOf[Node]
      if (this == p.left)
        p.right
      else
        p.left
    }

    def uncle: InternalNode = {
      if (parent.isRoot) {
        throw new IllegalArgumentException("Can't get uncle of root!")
      }

      val p: Node = parent.asInstanceOf[Node]
      p.sibling
    }

    def grandparent: Node = {
      if (parent.isRoot) {
        throw new IllegalArgumentException("Can't get grandparent of root!")
      }

      val p: Node = parent.asInstanceOf[Node]
      if (p.parent.isRoot) {
        throw new IllegalArgumentException("Can't get parent of root!")
      }

      p.parent.asInstanceOf[Node]
    }
  }

  def insert(key: K, value: V): Unit = {
    // Iterate through the tree until finding the right place to put the values
    var n: InternalNode = _head.child
    breakable{
      while (!n.isLeaf) {
        val node: Node = n.asInstanceOf[Node]
        if (key.compareTo(node.key) < 0) {
          n = node.left
        } else if (key.compareTo(node.key) > 0) {
          n = node.right
        } else {
          break
        }
      }
    }

    // Either a leaf to replace with a new node, or an existing node and insert the value
    n match {
      case leaf: Leaf =>
        // Create a new red node with the right key and value
        val p: TreeNode = leaf.parent
        val node: Node = new Node(p, key)
        node.addValue(value)

        // Put the new node as child of leaf's parent
        if (p.isRoot) {
          _head.child = node
        } else {
          node.colour = Red
          val pNode: Node = p.asInstanceOf[Node]
          if (pNode.left == leaf) {
            pNode.left = node
          } else {
            pNode.right = node
          }

          @scala.annotation.tailrec
          def repair(node: Node): Unit = {
            if (node.parent.isRoot) {
              node.colour = Black
            } else {
              val pNode: Node = node.parent.asInstanceOf[Node]
              if (pNode.colour == Black) {
                // Do nothing the structure is good
              } else if (node.uncle.colour == Red) {
                // Uncle is red and isn't a leaf
                pNode.colour = Black
                node.uncle.asInstanceOf[Node].colour = Black
                node.grandparent.colour = Black
                repair(node.grandparent)
              } else {
                // We're not the root, our parent isn't black and our uncle is a leaf or black
                val gpNode: Node = node.grandparent
                if (node == pNode.right && pNode == gpNode.left) {
                  pNode.rotateLeft()
                } else if (node == pNode.left && pNode == gpNode.right) {
                  pNode.rotateRight()
                }

                if (pNode == node.left)
                  gpNode.rotateRight()
                else
                  gpNode.rotateLeft()

                node.colour = Black
                gpNode.colour = Red
              }
            }
          }

          repair(node)
        }

      case node: Node =>
        // Update the value
        node.addValue(value)
    }
  }

  def delete(key: K): List[V] = ???

  def delete(key: K, value: V): V = ???

  def foreach(f: V => _): Unit = {
    def foreachRec(n: InternalNode): Unit = {
      if (n.isLeaf) return
      val node: Node = n.asInstanceOf[Node]
      foreachRec(node.left)
      node.value.foreach(f)
      foreachRec(node.right)
    }
    foreachRec(_head.child)
  }

  def map[B](f: List[V] => B): List[B] = {
    var l: ListBuffer[B] = ListBuffer()
    def mapRec(n: InternalNode): Unit = {
      if (n.isLeaf) return
      val node: Node = n.asInstanceOf[Node]
      mapRec(node.left)
      l += f(node.value)
      mapRec(node.right)
    }
    mapRec(_head.child)
    l.toList
  }

  def flatMap[B](f: List[V] => IterableOnce[B]): List[B] = {
    var l: ListBuffer[B] = ListBuffer()
    def flatMapRec(n: InternalNode): Unit = {
      if (n.isLeaf) return
      val node: Node = n.asInstanceOf[Node]
      flatMapRec(node.left)
      for (v: B <- f(node.value))
        l :+= v
      flatMapRec(node.right)
    }
    flatMapRec(_head.child)
    l.toList
  }

  def get(key: K): List[V] = {
    var n: InternalNode = _head.child
    var lastGood: InternalNode = n
    while (!n.isLeaf) {
      val node: Node = n.asInstanceOf[Node]
      if (node.key == key)
        return node.value
      else if (key.compareTo(node.key) > 0) {
        lastGood = node
        n = node.right
      } else
        n = node.left
    }
    if (lastGood.isLeaf)
      List()
    else
      lastGood.asInstanceOf[Node].value
  }

  def apply(key: K): List[V] = get(key)
}

object BinarySearchTree {
  def apply[K, V]()(implicit ev$1: K => Comparable[K]): BinarySearchTree[K, V] = new BinarySearchTree[K, V]()
}