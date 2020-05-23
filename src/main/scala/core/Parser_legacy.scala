package core

import scala.io.Source
import java.nio.file.{Files, Paths}

import components.{AbstractTimingPoint, Circle, HitObject, Inherited_legacy, Slider, Spinner, TimingPoint_legacy, Uninherited_legacy}
import core.ObjectHandler._
import utils.{Addition, Hitsound}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class Parser_legacy(fp: String) {

  private var _sourcePath = fp

  require(Files.isRegularFile(Paths.get(_sourcePath)), () => "Path is not a readable file.")

  def sourcePath: String = _sourcePath

  def sourcePath_=(fp: String): Unit = {
    require(Files.isRegularFile(Paths.get(fp)), () => "Path is not a readable file.")
    _sourcePath = fp
  }

  def readLines(): List[String] = { // gets the lines from the file in _sourcePath
    val file = Source.fromFile(_sourcePath)
    file.getLines().filter(!_.isEmpty).toList
  }

  var sliderMultiplier = 1.4
  //  Headers required for modes in readMap
  val Headers: Set[String] = Set("[Events]", "[General]", "[Editor]", "[Metadata]", "[Difficulty]", "[TimingPoints]", "[HitObjects]")

  //  returns full map object
  def readMap(): Map = {
    var mode = ""

    val tps = ListBuffer[TimingPoint_legacy]()
    val map = new Map()

    for (l <- readLines()) {
      if (Headers.contains(l)) mode = l
      else mode match {
        case "[Events]" => ???
        case "[General]" || "[Editor]" || "[Metadata]" || "[Difficulty]" => readSettings(l, map)
        case "[TimingPoints]" =>
          val timingPointLegacy = readTimingPoint(l)
          if (timingPointLegacy.isInstanceOf[Uninherited_legacy]) {
            val t: AbstractTimingPoint = timingPointLegacy
            map.addTimingPoint(t)
          }
          tps += timingPointLegacy
        case "[HitObjects]" => map.addObject(readObject(l))
        case _ => ???
      }
    }

    map.allObjects.foreach { obj => applyTP(obj, map.getTimingPoint(obj.timeStamp)) }

    def applyTP(ho: HitObject, abstp: AbstractTimingPoint): Unit = {
      abstp match {
        case tp: TimingPoint_legacy =>
          ho.hitsound.sampleSet = tp.sampleSet
          ho.hitsound.sampleIndex = tp.sampleIndex
          ho.additions(0).sampleSet = tp.sampleSet
          ho.additions(1).sampleSet = tp.sampleSet
          ho.additions(2).sampleSet = tp.sampleSet
          ho.additions(0).sampleIndex = tp.sampleIndex
          ho.additions(1).sampleIndex = tp.sampleIndex
          ho.additions(2).sampleIndex = tp.sampleIndex

          ho match {
            case sl: Slider =>
              tp match {
                case inh: Inherited_legacy => sl.velocity = 100.0 / (inh.svMultiplier * -1) * sliderMultiplier
                case _: Uninherited_legacy => sl.velocity = 1.0 * sliderMultiplier
                case _ => throw new Exception("Timing Point error: TP isn't inherited nor uninherited")
              }
            case _ =>
          }
        case _ =>
      }
    }

    var prev: Option[Uninherited_legacy] = None

    map.allTimingPoints.foreach {
      case tp: Uninherited_legacy =>
        prev = Some(tp)
        map -= tp
        map += MakeTimingPoint(tp.timeStamp, tp.bpm, m1 = tp.meter)
      case tp: Inherited_legacy =>
        map -= tp
        map += MakeTimingPoint(tp.timeStamp, if (prev.nonEmpty) prev.get.bpm else 120, m1 = if (prev.nonEmpty) prev.get.meter else 4)
    }

    map
  }

  def readSettings(line: String, map: Map) = {
    val properties = line.split(": *")

    properties(0) match {
      case "AudioFilename" => map.songFile = properties(1)
      case "StackLeniency" => map.stackLeniency = properties(1).toDouble
      case "Title" => map.song = properties(1)
      case "TitleUnicode" => map.unicodeSong = properties(1)
      case "Artist" => map.artist = properties(1)
      case "ArtistUnicode" => map.unicodeArtist = properties(1)
      case "Creator" => map.creator = properties(1)
      case "Version" => map.difficulty = properties(1)
      case "Source" => map.source = properties(1)
      case "Tags" => map.tags = properties(1)
      case "BeatmapID" => map.id = properties(1).toInt
      case "BeatmapSetID" => map.setId = properties(1).toInt
      case "HPDrainRate" => map.hp = properties(1).toDouble
      case "CircleSize" => map.cs = properties(1).toDouble
      case "OverallDifficulty" => map.od = properties(1).toDouble
      case "ApproachRate" => map.ar = properties(1).toDouble
      case "SliderMultiplier" => sliderMultiplier = properties(1).toDouble
      case "SliderTickRate" => map.tickrate = properties(1).toDouble
    }
  }

  /////// OBJECT READER ///////
  def readObject(line: String): HitObject = { // reads Object type then redirects to respective methods
    val properties = line.split(", *") // all Object readers are overloaded for an array of properties or a line string containing an object

    properties(3).toInt & 11 match {
      case 1 => readCircle(properties)
      case 2 => readSlider(properties)
      case 8 => readSpinner(properties)
    }
  }

  // Hit Circle syntax: [x,y,time,type,hitSound,extras]
  def readCircle(properties: Array[String]): Circle = {
    val c = MakeCircle((properties(0).toInt, properties(1).toInt), properties(2).toInt)

    if (!(properties.length < 5)) {
      val h = readActiveHitsound(properties(5), properties(4))
      c.hitsound = h._1
      c.additions = h._2
    }
    else {
      c.hitsound = new Hitsound()
      c.additions = Array(new Addition(), new Addition(), new Addition())
    }

    c
  }

  // circle overload for string line
  def readCircle(line: String): Circle = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 1, () => "Line does not contain a circle")
    readCircle(properties)
  }

  // TODO: SLIDER HITSOUNDS

  // Slider syntax: [x,y,time,type,hitSound,sliderType|curvePoints,repeat,pixelLength,edgeAdditions,edgeSets/Index,extras] OR
  //                [x,y,time,type,hitSound,sliderType|curvePoints,repeat,pixelLength]
  def readSlider(properties: Array[String]): Slider = {
    //  slider anchor array
    val sliderArray = properties(5).split("\\|")
    //  throw away slider type char and transform string nodes (x:y) into 2 strings
    val sliderNodes = sliderArray.drop(1).map(_.split(":"))
    //  slider repeat count
    val repeats = properties(6).toInt - 1

    val s = MakeSlider((properties(0).toInt, properties(1).toInt), properties(2).toInt, properties(2).toInt + 1, 1.0, repeats)

    //  save nodes from sliderNodes as Node types as red and grey nodes
    var skip = false
    for (a <- sliderNodes) {
      if (!skip) {
        if (!(sliderNodes.indexOf(a) == sliderNodes.length - 1) && (sliderNodes(sliderNodes.indexOf(a)) sameElements sliderNodes(sliderNodes.indexOf(a) + 1))) {
          s.addNode((a(0).toInt, a(1).toInt), 1)
          skip = true
        }
        else s.addNode((a(0).toInt, a(1).toInt), 0)
      }
      skip = false
    }

    //hitsounds getting from pipe-separated lists
    if (properties.length > 8) {
      var setsIndexes = properties(9).split("\\|").map(_.split(":").map(_.toInt)) //Array(Array(index,set))
      var additionsHs = properties(8).split("\\|").map(_.toInt) //Array of additions for each edge of slider (head, repeats, end) so repeats + 2

      s.hitsound = new Hitsound(setsIndexes(0)(1), setsIndexes(0)(0))
      s.additions = readAdditionBit(additionsHs(0))

      setsIndexes = setsIndexes.drop(1)
      additionsHs = additionsHs.drop(1)

      for (i <- 0 to s.repeats) {
        s.repeatHitsounds(i) = (new Hitsound(setsIndexes(i)(1), setsIndexes(i)(0)), readAdditionBit(additionsHs(i)))
      }
    }
    else {
      s.hitsound = new Hitsound()
      s.additions = Array.fill(3)(new Addition())

      s.repeatHitsounds = Array.fill(s.repeats + 1)((new Hitsound, Array.fill(3)(new Addition)))
    }

    s
  }

  // slider overload for string line
  def readSlider(line: String): Slider = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 2, () => "Line does not contain a slider")
    readSlider(properties)
  }

  //                  0 1  2    3    4        5       6
  // Spinner syntax: [x,y,time,type,hitSound,endTime,extras]
  def readSpinner(properties: Array[String]): Spinner = {
    val s = MakeSpinner(properties(2).toInt, properties(5).toInt)

    if (!(properties.length < 7)) {
      val h = readActiveHitsound(properties(6), properties(4))
      s.hitsound = h._1
      s.additions = h._2
    }
    else {
      s.hitsound = new Hitsound()
      s.additions = Array(new Addition(), new Addition(), new Addition())
    }
    s
  }

  // spinner overload for string line
  def readSpinner(line: String): Spinner = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 8, () => "Line does not contain a spinner")
    readSpinner(properties)
  }

  // combines extras and addition bit FOR CIRCLES AND SPINNERS
  def readActiveHitsound(ext: String, adb: String): (Hitsound, Array[Addition]) = { // ONLY FOR CIRCLES AND SPINNERS
    val e = readExtras(ext)
    val b = readAdditionBit(adb)

    for (s <- b) {
      s.sampleSet = e._2
      s.sampleIndex = e._3
    }
    (new Hitsound(e._1.toInt, e._3.toInt), b)
  }

  // Addition Bit Structure: ( { 3: clap }, { 2: finish }, { 1: whistle }, { 0: normal - irrelevant } )
  def readAdditionBit(hsb: String): Array[Addition] = {
    readAdditionBit(hsb.toInt)
  }

  def readAdditionBit(hsb: Int): Array[Addition] = {
    val additionArray: Array[Addition] = Array(new Addition(), new Addition(), new Addition())

    //  read out bits to activate respective additions
    if ((hsb & 2) == 2) {
      additionArray(0).active = true
    }
    if ((hsb & 4) == 4) {
      additionArray(1).active = true
    }
    if ((hsb & 8) == 8) {
      additionArray(2).active = true
    }
    additionArray
  }

  // Extras syntax: [sampleSet:additionSet:customIndex:sampleVolume:filename] - filename is ignored - ONLY WORKS FOR CIRCLES AND SPINNERS
  def readExtras(extras: String): (Int, Int, Int, Int, String) = {
    val e = extras.split(":")

    if (e.length == 4) {
      (e(0).toInt, e(1).toInt, e(2).toInt, e(3).toInt, "")
    }
    else {
      (e(0).toInt, e(1).toInt, e(2).toInt, e(3).toInt, e(4))
    }
  }

  /// TIMING POINTS READER ///
  //                            0       1                     2        3           4           5        6          7
  // Timing point structure: [Offset, Milliseconds per Beat, Meter, Sample Set, Sample Index, Volume, Inherited, Kiai Mode]
  def readTimingPoint(line: String): TimingPoint_legacy = {
    val properties = line.split(",")

    val time = properties(0).toInt
    val mspb = properties(1).toDouble // milliseconds per beat
    var bpm: Double = 0.0 // bpm
    var svmult: Double = 0.0 // sv multiplier

    if (mspb >= 0.0) { // if millisec per beat is positive
      bpm = 60000.0 / mspb // make a bpm
    }
    else svmult = 100.0 / (mspb * -1) // else make an sv

    val kiai = properties.equals("1")

    if (properties(6) == "1") {
      new Inherited_legacy(time, svmult, properties(3).toInt, properties(4).toInt, properties(5).toInt, kiai)
    }
    else new Uninherited_legacy(time, bpm, properties(2).toInt, properties(3).toInt, properties(4).toInt, properties(5).toInt, kiai)
  }
}
