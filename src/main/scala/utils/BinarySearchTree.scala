package utils

abstract sealed class Color

case object Red extends Color

case object Black extends Color

class BinarySearchTree[K <: Comparable[K], V] {
  private var _head: TreeNode = new Head(new Node(_head))

  private trait TreeNode {
    def left: Node

    def left_=(value: Node): Unit

    def right: Node

    def right_=(value: Node): Unit
  }

  private class Head(n: Node) extends TreeNode {
    private var _head: Node = n

    def left: Node = _head

    def left_=(value: Node): Unit = _head = value

    def right: Node = _head

    def right_=(value: Node): Unit = _head = value
  }

  private class Node(p: TreeNode) extends TreeNode {
    private var _color: Color = Black

    def color: Color = _color

    def color_=(value: Color): Unit = _color = value

    private var _parent: TreeNode = p
    private var _left: Node = new Node(this)
    private var _right: Node = new Node(this)

    def parent: TreeNode = _parent
    def left: Node = _left
    def right: Node = _right

    def parent_=(value: TreeNode): Unit = _parent = value
    def left_=(value: Node): Unit = _left = value
    def right_=(value: Node): Unit = _right = value

    private var _key: Option[K] = None
    private var _value: List[V] = List()

    def key: Option[K] = _key
    def value: List[V] = _value

    def key_=(value: K): Unit = _key = Some(value)
    def value_=(value: List[V]): Unit = _value = value
    def addValue(value: V): Unit = _value :+= value
    def removeValue(value: V): Unit = _value = _value.filter(_ != value)

    def isEmpty(): Boolean = key.isEmpty

    def rotateLeft(): Unit = {
      val nnew: Node = right
      val p: TreeNode = parent
      if (nnew.isEmpty())
        throw new IllegalArgumentException("RotateLeft requires a non empty right node")

      right = nnew.left
      right.parent = this
      nnew.left = this
      parent = nnew

      if (this == p.left) {
        p.left = nnew
      } else {
        p.right = nnew
      }

      nnew.parent = p
    }

    def rotateRight(): Unit = {
      val nnew: Node = left
      val p: TreeNode = parent
      if (nnew.isEmpty())
        throw new IllegalArgumentException("RotateRight requires a non empty left node")

      left = nnew.right
      left.parent = this
      nnew.right = this
      parent = nnew

      if (this == p.right) {
        p.right = nnew
      } else {
        p.left = nnew
      }

      nnew.parent = p
    }
  }

  def insert(key: K, value: V): Unit = ???

  def delete(key: K): List[V] = ???

  def delete(key: K, value: V): V = ???

  def iterator(): Iterator[(K, V)] = ???

  def foreach(): Iterator[K] = ???

  def get(key: K): List[V] = ???

  def apply(key: K): List[V] = get(key)
}

object BinarySearchTree {
  def apply[A, B <: Comparable[B]](): BinarySearchTree[A, B] = new BinarySearchTree[A, B]()
}