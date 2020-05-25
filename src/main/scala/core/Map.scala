package core

import components.{AbstractTimingPoint, Component, HitObject}
import utils.{ComponentMap, TimeStamp}

/**
 * an implementation of an osu! map
 */
class Map {
  private val _objects = ComponentMap[HitObject]()
  private val _timingPoints = ComponentMap[AbstractTimingPoint]()

  // metadata

  private var _music: String = ""
  private var _unicodeMusic: String = ""
  private var _artist: String = ""
  private var _unicodeArtist: String = ""
  private var _creator: String = ""
  private var _difficulty: String = ""
  private var _source: String = ""
  private var _tags: String = ""
  private var _id: Int = 0
  private var _setId: Int = -1

  // getters and setters

  def music: String = _music

  def music_=(value: String): Unit = {
    _music = value
  }

  def unicodeMusic: String = _unicodeMusic

  def unicodeMusic_=(value: String): Unit = {
    _unicodeMusic = value
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

  private var _tickrate: Double = 1
  private var _stackLeniency: Double = 0
  private var _sliderVelocity: Double = 1
  private var _songFile: String = ""
  private var _backgroundFile: String = ""

  // getters and setters

  def tickrate: Double = _tickrate

  def tickrate_=(value: Double): Unit = {
    _tickrate = value
  }

  def stackLeniency: Double = _stackLeniency

  def stackLeniency_=(value: Double): Unit = {
    _stackLeniency = value
  }

  def sliderVelocity: Double = _sliderVelocity

  def sliderVelocity_=(value: Double): Unit = {
    _sliderVelocity = value
  }

  def songFile: String = _songFile

  def songFile_=(value: String): Unit = {
    _songFile = value
  }

  def backgroundFile: String = _backgroundFile

  def backgroundFile_=(value: String): Unit = {
    _backgroundFile = value
  }

  // colours

  private var _colours = Array[(Int, Int, Int)]((255,192,0), (0,202,0), (18,124,255), (242,24,57))

  def addColour(c: (Int, Int, Int)): Unit = {
    if (_colours.length == 8)
      throw new IllegalArgumentException("Too many colours!")
    _colours :+= c
  }

  def removeColours(i: Int): Unit = {
    if (_colours.length == 2)
      throw new IllegalArgumentException("Too few colours!")
    _colours = _colours.take(i) ++ _colours.drop(i + 1)
  }

  def setColour(i: Int, c: (Int, Int, Int)): Unit = _colours(i) = c

  def getColour(i: Int): (Int, Int, Int) = _colours(i)

  def colours: Array[(Int, Int, Int)] = _colours

  def colours_=(c: Array[(Int, Int, Int)]): Unit = {
    require(c.length < 9 && c.length > 1)
    _colours = c
  }

  // interactions with both lists

  def addObject(o: HitObject): Unit = _objects += o

  def getObject(t: TimeStamp): Option[HitObject] = {
    val res = _objects(t)
    if (res.isEmpty) {
      None
    } else {
      Some(res(0))
    }
  }

  def deleteObject(o: HitObject): Unit = _objects -= o

  def getOverlapObject(o: Component): List[HitObject] = _objects(o)

  def getOverlapObject(t: TimeStamp): List[HitObject] = _objects(t)

  def addTimingPoint(t: AbstractTimingPoint): Unit  = _timingPoints += t

  def getTimingPoint(t: TimeStamp): AbstractTimingPoint = _timingPoints.search(t)

  def deleteTimingPoint(t: AbstractTimingPoint): Unit = _timingPoints -= t

  def getOverlapTimingPoint(o: Component): List[AbstractTimingPoint] = _timingPoints(o)

  def getOverlapTimingPoint(t: TimeStamp): List[AbstractTimingPoint] = _timingPoints(t)

  def allObjects: List[HitObject] = _objects.toList

  def allTimingPoints: List[AbstractTimingPoint] = _timingPoints.toList

  def +=(obj: HitObject): Unit = addObject(obj)

  def +=(t: AbstractTimingPoint): Unit = addTimingPoint(t)

  def -=(obj: HitObject): Unit = deleteObject(obj)

  def -=(t: AbstractTimingPoint): Unit = deleteTimingPoint(t)

  def apply(o: Component): List[HitObject] = getOverlapObject(o)

  def apply(t: TimeStamp): List[HitObject] = getOverlapObject(t)

  def canEqual(other: Any): Boolean = other.isInstanceOf[Map]

  override def equals(other: Any): Boolean = other match {
    case that: Map =>
      (that canEqual this) &&
        _objects == that._objects &&
        _timingPoints == that._timingPoints &&
        _music == that._music &&
        _unicodeMusic == that._unicodeMusic &&
        _artist == that._artist &&
        _unicodeArtist == that._unicodeArtist &&
        _creator == that._creator &&
        _difficulty == that._difficulty &&
        _source == that._source &&
        _tags == that._tags &&
        _id == that._id &&
        _setId == that._setId &&
        _hp == that._hp &&
        _cs == that._cs &&
        _od == that._od &&
        _ar == that._ar &&
        _tickrate == that._tickrate &&
        _stackLeniency == that._stackLeniency &&
        _sliderVelocity == that._sliderVelocity &&
        _songFile == that._songFile &&
        _backgroundFile == that._backgroundFile &&
        (_colours sameElements that._colours)
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(_objects, _timingPoints, _music, _unicodeMusic, _artist, _unicodeArtist, _creator, _difficulty, _source, _tags, _id, _setId, _hp, _cs, _od, _ar, _tickrate, _stackLeniency, _sliderVelocity, _songFile, _backgroundFile)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String = {
    MapExporter(this).export
  }
}

object Map {
  def apply(): Map = new Map()
}