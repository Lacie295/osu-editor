package core

import components.{AbstractTimingPoint, Component, HitObject}
import utils.{ComponentMap, TimeStamp}

/**
 * an implementation of an osu! map
 */
class Map {
  private val objects = ComponentMap[HitObject]()
  private val timingPoints = ComponentMap[AbstractTimingPoint]()

  // metadata

  private var _song: String = ""
  private var _unicodeSong: String = ""
  private var _artist: String = ""
  private var _unicodeArtist: String = ""
  private var _creator: String = ""
  private var _difficulty: String = ""
  private var _source: String = ""
  private var _tags: String = ""
  private var _id: Int = 0
  private var _setId: Int = -1

  // getters and setters

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

  def setId: Int = _setId

  def setId_=(value: Int): Unit = {
    _setId = value
  }

  // difficulty

  private var _hp: Double = 5.0
  private var _cs: Double = 5.0
  private var _od: Double = 5.0
  private var _ar: Double = 5.0

  // getters and setters

  def hp: Double = _hp

  def hp_=(value: Double): Unit = {
    _hp = value
  }

  def cs: Double = _cs

  def cs_=(value: Double): Unit = {
    _cs = value
  }

  def od: Double = _od

  def od_=(value: Double): Unit = {
    _od = value
  }

  def ar: Double = _ar

  def ar_=(value: Double): Unit = {
    _ar = value
  }

  // settings

  private var _tickrate: Int = 1
  private var _stackLeniency: Double = 0
  private var _songFile: String = ""
  private var _backgroundFile: String = ""

  // getters and setters

  def tickrate: Int = _tickrate

  def tickrate_=(value: Int): Unit = {
    _tickrate = value
  }

  def stackLeniency: Double = _stackLeniency

  def stackLeniency_=(value: Double): Unit = {
    _stackLeniency = value
  }

  def songFile: String = _songFile

  def songFile_=(value: String): Unit = {
    _songFile = value
  }

  def backgroundFile: String = _backgroundFile

  def backgroundFile_=(value: String): Unit = {
    _backgroundFile = value
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

  def apply(o: Component): List[HitObject] = getOverlapObject(o)

  def apply(t: TimeStamp): List[HitObject] = getOverlapObject(t)

  override def toString: String = allObjects.map(_.toString()).mkString("\n") + "\n" + allTimingPoints.map(_.toString()).mkString("\n")
}