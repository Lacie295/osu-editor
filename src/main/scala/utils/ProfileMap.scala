package utils

import components.Component

class ProfileMap[T <: Component]() {
  private var _storage: List[T] = List[T]()

  private var _size: Int = 0

  def size: Int = _size

  def isEmpty: Boolean = _size == 0

  def insert(obj: T): Unit = {
    val key = obj.timeStamp
    var index = binarySearch(key)
    if (index < 0)
      index = -index - 1
    val (front, back) = _storage.splitAt(index)
    _storage = front ++ List(obj) ++ back
  }

  def binarySearch(key: TimeStamp): Int = {
    var start = 0
    var end = _storage.size
    while (start < end && start < _storage.size) {
      val mid = (start + end) / 2
      val compare = _storage(mid)
      if (compare.timeStamp == key)
        return mid
      else if (compare.compareTo(key) > 0)
        end = mid
      else
        start = mid + 1
    }
    -start - 1
  }

  def search(key: TimeStamp): T = {
    val index = binarySearch(key)
    if (index == -1) {
      _storage(0)
    } else if (index < 0) {
      _storage(-index - 2)
    } else {
      _storage(index)
    }
  }

  def delete(value: T): Unit = _storage = _storage diff List(value)

  def getOverlaps(key: TimeStamp): List[T] = _storage.filter(_.overlaps(key))

  def getOverlaps(obj: Component): List[T] = _storage.filter(_.overlaps(obj))

  def apply(key: TimeStamp): List[T] = getOverlaps(key)

  def apply(obj: Component): List[T] = getOverlaps(obj)

  def -=(obj: T): Unit = delete(obj)

  def +=(obj: T): Unit = insert(obj)

  def toList: List[T] = _storage

  def get(index: Int): T = _storage(index)
}

object ProfileMap {
  def apply[T <: Component](): ProfileMap[T] = new ProfileMap[T]()
}