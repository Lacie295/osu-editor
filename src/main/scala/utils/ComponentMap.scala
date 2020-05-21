package utils

import components.Component

/**
 * a structure to store components in an ordered fashion
 * @tparam T
 */
class ComponentMap[T <: Component]() {
  private var _storage: List[T] = List[T]()

  private var _size: Int = 0

  def size: Int = _size

  def isEmpty: Boolean = _size == 0

  /**
   * adds obj to the map in the right spot
   * @param obj: the object to add
   */
  def insert(obj: T): Unit = {
    // find where to insert the time stamp
    val key = obj.timeStamp
    var index = binarySearch(key)

    // convert if the index wasn't found
    if (index < 0)
      index = -index - 1

    // insert and increase size
    val (front, back) = _storage.splitAt(index)
    _storage = front ++ List(obj) ++ back
    _size += 1
  }

  /**
   * a binary search
   * @param key: the key to search for
   * @return the index at which to find key, otherwise -index-1 with index being where to insert the object to keep order
   */
  def binarySearch(key: TimeStamp): Int = {
    // the fork to search in
    var start = 0
    var end = _storage.size

    while (start < end && start < _storage.size) {
      // check middle value
      val mid = (start + end) / 2

      // check if the key has been found
      val compare = _storage(mid)
      if (compare.timeStamp == key)
        return mid
      else if (compare > key)
        // key is in the lower part
        end = mid
      else
        // key is in the upper part
        start = mid + 1
    }
    -start - 1
  }

  /**
   * searches for a component at key
   * @param key: the timestamp to look at
   * @return the component at timestamp key if there is one, otherwise the closest preceding one, or the first one if none precede the timestamp
   */
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

  // interactions with list

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

/**
 * shorthand to create a map
 */
object ComponentMap {
  def apply[T <: Component](): ComponentMap[T] = new ComponentMap[T]()
}