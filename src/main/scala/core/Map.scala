package core

import components.{AbstractTimingPoint, Component, HitObject}
import utils.{ComponentMap, TimeStamp}

/**
 * an implementation of an osu! map
 */
class Map {
  private val objects = ComponentMap[HitObject]()
  private val timingPoints = ComponentMap[AbstractTimingPoint]()

  private var _song: String = ""
  private var _unicodeSong: String = ""
  private var _artist: String = ""
  private var _unicodeArtist: String = ""
  private var _creator: String = ""
  private var _difficulty: String = ""
  private var _source: String = ""
  private var _tags: String = ""
  private var _id: Int = 0

  def song: String = _song

  def song_=(value: String): Unit = {
    _song = value
  }

  def unicodeSong: String = _unicodeSong

  def unicodeSong_=(value: String): Unit = {
    _unicodeSong = value
  }

  def artist: String = _artist

  def artist_=(value: String): Unit = {
    _artist = value
  }

  def unicodeArtist: String = _unicodeArtist

  def unicodeArtist_=(value: String): Unit = {
    _unicodeArtist = value
  }

  def creator: String = _creator

  def creator_=(value: String): Unit = {
    _creator = value
  }

  def difficulty: String = _difficulty

  def difficulty_=(value: String): Unit = {
    _difficulty = value
  }

  def source: String = _source

  def source_=(value: String): Unit = {
    _source = value
  }

  def tags: String = _tags

  def tags_=(value: String): Unit = {
    _tags = value
  }

  def id: Int = _id

  def id_=(value: Int): Unit = {
    _id = value
  }

  // interactions with both lists

  def addObject(o: HitObject): Unit = objects += o

  def getObject(t: TimeStamp): Option[HitObject] = {
    val res = objects(t)
    if (res.isEmpty) {
      None
    } else {
      Some(res(0))
    }
  }

  def deleteObject(o: HitObject): Unit = objects -= o

  def getOverlapObject(o: Component): List[HitObject] = objects(o)

  def getOverlapObject(t: TimeStamp): List[HitObject] = objects(t)

  def addTimingPoint(t: AbstractTimingPoint): Unit  = timingPoints += t

  def getTimingPoint(t: TimeStamp): AbstractTimingPoint = timingPoints.search(t)

  def deleteTimingPoint(t: AbstractTimingPoint): Unit = timingPoints -= t

  def getOverlapTimingPoint(o: Component): List[AbstractTimingPoint] = timingPoints(o)

  def getOverlapTimingPoint(t: TimeStamp): List[AbstractTimingPoint] = timingPoints(t)

  def allObjects: List[HitObject] = objects.toList

  def allTimingPoints: List[AbstractTimingPoint] = timingPoints.toList

  def +=(obj: HitObject): Unit = addObject(obj)

  def +=(t: AbstractTimingPoint): Unit = addTimingPoint(t)

  def -=(obj: HitObject): Unit = deleteObject(obj)

  def -=(t: AbstractTimingPoint): Unit = deleteTimingPoint(t)
}