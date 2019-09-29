package utils

import scala.collection.mutable.ListBuffer

class BinarySearchTree[K, V]()(implicit ev$1: K => Comparable[K]) {
  // fixed color booleans
  val RED: Boolean = true
  val BLACK: Boolean = false

  // node objects, represents a node in the RB-tree with a key and a list of values
  private class Node(k: K, v: V, p: Option[Node] = None) {
    // the key and value pairs
    private var _key: K = k
    private var _value: List[V] = List(v)

    // the parent node, by default None for the root
    private var _parent: Option[Node] = p

    // left and right children, by default none
    private var _left: Option[Node] = None
    private var _right: Option[Node] = None

    // the colour of the node, by default black
    private var _colour: Boolean = BLACK
    private var _doubleBlack: Boolean = false

    // getter and setter for the children
    def left: Option[Node] = _left

    def right: Option[Node] = _right

    def left_=(n: Option[Node]): Unit = {
      if (n.isDefined)
        n.get.set_parent(Some(this))
      _left = n
    }

    def right_=(n: Option[Node]): Unit = {
      if (n.isDefined)
        n.get.set_parent(Some(this))
      _right = n
    }

    // getter and setter for the keys and values, plus a possibility to add a single value to the list
    def key: K = _key

    def value: List[V] = _value

    def key_=(k: K): Unit = _key = k

    def value_=(v: List[V]): Unit = _value = v

    def addValue(v: V): Unit = _value :+= v

    def removeValue(v: V): Unit = _value = _value.filter(_ != v)

    def isEmpty: Boolean = value.isEmpty

    // getter and setter for the parent. Setter is private
    def parent: Option[Node] = _parent

    private def set_parent(p: Option[Node]): Unit = _parent = p

    // leaf and root functions
    def isLeaf: Boolean = left.isEmpty && right.isEmpty

    def isRoot: Boolean = parent.isEmpty

    // colour getters and setters
    def colour: Boolean = _colour

    def colour_=(c: Boolean): Unit = _colour = c

    def colourFlip(): Unit = colour = !colour

    def isBlack: Boolean = colour == BLACK

    def isRed: Boolean = colour == RED

    def childrenBlack: Boolean = (left.isDefined && left.get.isBlack) && (right.isDefined && right.get.isBlack)

    // get the minimal node of the subtree
    def min: Node = {
      var m = this
      while (m.left.isDefined)
        m = m.left.get
      m
    }

    // get the direct successor of this node
    def succ: Node = {
      var m = this
      if (m.right.isDefined)
        m = m.right.get.min
      m
    }

    // rotates the subtree to the left. This child becomes its right child's child and inherits its left child
    def rotateLeft(): Unit = {
      if (right.isEmpty)
        throw new IllegalArgumentException("Can't rotate right without a right child")
      val newRoot = right.get
      newRoot.replace(this)
      right = newRoot.left
      newRoot.left = Some(this)
    }

    // rotates the subtree to the right. This child becomes its left child's child and inherits its right child
    def rotateRight(): Unit = {
      if (left.isEmpty)
        throw new IllegalArgumentException("Can't rotate right without a left child")
      val newRoot = left.get
      newRoot.replace(this)
      left = newRoot.right
      newRoot.right = Some(this)
    }

    // replaces a node by relinking as the child of its parent
    def replace(n: Node): Unit = {
      if (n.parent.isEmpty) {
        _root = Some(this)
        this.set_parent(None)
      } else {
        val p = n.parent.get
        if (p.right.isDefined && n == p.right.get)
          p.right = Some(this)
        else
          p.left = Some(this)
      }
    }

    // utility functions to get related nodes
    def grandparent: Option[Node] =
      if (parent.isDefined)
        parent.get.parent
      else
        None

    def uncle: Option[Node] =
      if (grandparent.isDefined)
        if (grandparent.get.left.isDefined && parent.get == grandparent.get.left.get)
          grandparent.get.right
        else
          grandparent.get.left
      else
        None

    def sibling: Option[Node] =
      if (parent.isDefined)
        if (parent.get.left.isDefined && this == parent.get.left.get)
          parent.get.right
        else
          parent.get.left
      else
        None

    def isRight: Boolean =
      if (parent.isDefined)
        parent.get.right.isDefined && this == parent.get.right.get
      else
        false

    def isLeft: Boolean =
      if (parent.isDefined)
        parent.get.left.isDefined && this == parent.get.left.get
      else
        false
  }

  // the root
  private var _root: Option[Node] = None
  private var _size: Int = 0

  def size: Int = _size

  def isEmpty: Boolean = _size == 0

  def insert(key: K, value: V): Unit = {
    if (_root.isEmpty) {
      // if there's no nodes in the tree, set this as new root
      _root = Some(new Node(key, value))
      _size += 1
    } else {
      // Iterate through the tree until finding the right place to put the values
      var n: Option[Node] = _root
      var found: Boolean = false
      var right: Boolean = false

      // loops on children until finding the right spot. Quit if landed on the node with the right value
      // or on a node lacking the correct child. (left if key < node, right otherwise)
      while (!found) {
        val comp = key.compareTo(n.get.key)
        if (comp < 0) {
          if (n.get.left.isDefined)
            n = n.get.left
          else
            found = true
        } else if (comp > 0) {
          if (n.get.right.isDefined)
            n = n.get.right
          else {
            found = true
            right = true
          }
        } else
          found = true
      }

      if (n.get.key == key) {
        // insert into found value
        n.get.addValue(value)
      } else {
        // Either insert left or right
        // Create a new red node with the right key and value
        val node: Node = new Node(key, value, n)

        // Put the new node as child of n
        node.colour = RED
        if (right) {
          n.get.right = Some(node)
        } else {
          n.get.left = Some(node)
        }

        // repairing the tree
        def repair(node: Node): Unit = {
          if (node.parent.isEmpty) {
            // if we're at the root, color the node black and everything is done
            node.colour = BLACK
          } else {
            // we're not at the root. Get the nodes parent
            val pNode: Node = node.parent.get
            if (pNode.isBlack) {
              // Do nothing the structure is good
            } else if (node.uncle.get.isRed) {
              // Uncle is red. Colour everything black and recurse on the grandparent
              pNode.colour = BLACK
              node.uncle.get.colour = BLACK
              node.grandparent.get.colour = BLACK
              repair(node.grandparent.get)
            } else {
              // We're not the root, our parent isn't black and our uncle is a leaf or black
              val gpNode: Node = node.grandparent.get

              // rotate so the node is at the top
              if (node.isRight && pNode.isLeft) {
                pNode.rotateLeft()
              } else if (node.isLeft && pNode.isRight) {
                pNode.rotateRight()
              }

              // rotate so that new uncle is at the top
              if (pNode.isLeft)
                gpNode.rotateRight()
              else
                gpNode.rotateLeft()

              node.colour = BLACK
              gpNode.colour = RED
            }
          }

          repair(node)
        }
      }
    }
  }

  // deletes node with key k. Returns true if successful
  def delete(k: K): List[V] = {
    val n = get(k)
    if (n.isEmpty)
      List()
    else {
      deleteNode(n.get)
      n.get.value
    }
  }

  // deletes a value off node with key k, and the entire node if v was the last value. Returns true if successful
  def delete(k: K, v: V): List[V] = {
    val n = get(k)
    if (n.isEmpty)
      List()
    else {
      if (n.get.value.contains(v)) {
        n.get.removeValue(v)
        if (n.get.isEmpty)
          deleteNode(n.get)
        List(v)
      } else
        List()
    }
  }

  // deletes a node
  private def deleteNode(n: Node): Unit = {
    // copy the value from the successor, the successor will be deleted
    val succ: Node = if (n.left.isDefined && n.right.isDefined) {
      val s = n.succ
      n.value = s.value
      n.key = s.key
      s
    } else {
      n
    }

    // get the successor's child if it exists
    val child = if (succ.right.isDefined) succ.right else succ.left

    // replace successor by its child
    if (child.isDefined) {
      child.get.replace(succ)
    }

    if (succ.isBlack) {
      // successor was black, we have to fix the depth again
      def fix(node: Node): Unit = {
        if (node.isRoot)
          node.colour = BLACK
        else if (node.isRed)
          // if child was red, simply recolour
          node.colour = BLACK
        else if (node.sibling.get.isRed) {
          // sibling is red, rotate to fix depth and swap colour of sibling and parent
          val par = node.parent.get
          val sib = node.sibling.get
          if (node.isRight)
            par.rotateRight()
          else
            par.rotateLeft()

          val temp = par.colour
          par.colour = sib.colour
          sib.colour = temp
        } else if (node.sibling.get.childrenBlack) {
          // recursion case, fix the sibling by making it red and fix the parent
          node.sibling.get.colour = RED
          fix(node.parent.get)
        } else {
          val par = node.parent.get
          val sib = node.sibling.get
          if (node.isRight) {
            if (sib.right.get.isRed) {
              // force red nephew to be the far one and recolour it black
              sib.rotateLeft()
              node.sibling.get.colour == BLACK
            }

            // rotate so sibling is at the top
            par.rotateRight()

            val temp = par.colour
            par.colour = node.colour
            node.colour = temp
          } else {
            // symmetry case
            if (sib.left.get.isRed) {
              // force red nephew to be the far one and recolour it black
              sib.rotateRight()
              node.sibling.get.colour == BLACK
            }

            // rotate so sibling is at the top
            par.rotateLeft()

            val temp = par.colour
            par.colour = node.colour
            node.colour = temp
          }
        }
      }

      if (child.isDefined)
        fix(child.get)
    }
  }

  def valid: Boolean = {
    if (_root.isDefined) {
      if (_root.get.isBlack) {
        def validRec(node: Node): Int = {
          if (node.parent.isDefined) {
            val p = node.parent.get
            if (p.isRed && node.isRed)
              -1
            else if (node.isLeaf && node.isBlack)
              1
            else {
              val l = if (node.left.isDefined) validRec(node.left.get) else 0
              val r = if (node.right.isDefined) validRec(node.right.get) else 0
              if (l == -1 || r == -1) -1 else (if (node.isBlack) 1 else 0) + l + r
            }
          } else
            1
        }

        validRec(_root.get) > 0
      } else
        false
    } else
      true
  }

  def foreach(f: V => _): Unit = {
    def foreachRec(node: Node): Unit = {
      if (node.left.isDefined)
        foreachRec(node.left.get)
      node.value.foreach(f)
      if (node.right.isDefined)
        foreachRec(node.right.get)
    }

    if (_root.isDefined)
      foreachRec(_root.get)
  }

  def map[B](f: List[V] => B): List[B] = {
    var l: ListBuffer[B] = ListBuffer()

    def mapRec(node: Node): Unit = {
      if (node.left.isDefined)
        mapRec(node.left.get)
      l += f(node.value)
      if (node.right.isDefined)
        mapRec(node.right.get)
    }

    if (_root.isDefined)
      mapRec(_root.get)
    l.toList
  }

  def flatMap[B](f: List[V] => IterableOnce[B]): List[B] = {
    var l: ListBuffer[B] = ListBuffer()

    def flatMapRec(node: Node): Unit = {
      if (node.left.isDefined)
        flatMapRec(node.left.get)
      for (v: B <- f(node.value))
        l :+= v
      if (node.right.isDefined)
        flatMapRec(node.right.get)
    }

    if (_root.isDefined)
      flatMapRec(_root.get)
    l.toList
  }

  private def get(key: K): Option[Node] = {
    if (_root.isEmpty)
      return None

    var n: Option[Node] = _root
    var lastGood: Node = n.get
    while (n.isDefined) {
      val node: Node = n.get
      if (node.key == key)
        return Some(node)
      else if (key.compareTo(node.key) > 0) {
        lastGood = node
        n = node.right
      } else
        n = node.left
    }
    Some(lastGood)
  }

  def apply(key: K): List[V] = {
    val r = get(key)
    if (r.isEmpty)
      List()
    else
      r.get.value
  }
}

object BinarySearchTree {
  def apply[K, V]()(implicit ev$1: K => Comparable[K]): BinarySearchTree[K, V] = new BinarySearchTree[K, V]()
}