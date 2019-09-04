package utils

import scala.collection.mutable

class BinarySearchTree[K <: Comparable[K], V] {
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