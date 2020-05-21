package utils

import components.Component

class ProfileMap {
  private var _storage: List[Component] = List[Component]()

  private var _size: Int = 0

  def size: Int = _size

  def isEmpty: Boolean = _size == 0

  def insert(key: TimeStamp, value: Component): Unit = {
    val index = binarySearch(key)
    val (front, back) = _storage.splitAt(index)
    _storage = front ++ List(value) ++ back
  }

  def binarySearch(key: TimeStamp): Int = {
    var start = 0
    var end = _storage.size - 1
    while (start != end) {
      val mid = (start + end) / 2
      val compare = _storage(mid)
      if (compare.timeStamp == key)
        return mid
      else if (compare.compareTo(key) < 0)
        end = mid - 1
      else
        start = mid + 1
    }
    start
  }
}