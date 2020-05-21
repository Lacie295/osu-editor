package core

import scala.io.Source
import java.nio.file.{Files, Paths}

import components.{Circle, HitObject, Inherited_legacy, Slider, Spinner, TimingPoint, TimingPoint_legacy, Uninherited_legacy}
import utils.{Addition, Hitsound}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class Parser(fp: String) {

  private var _sourcePath = fp

  require(Files.isRegularFile(Paths.get(_sourcePath)), () => "Path is not a readable file.")

  def sourcePath: String = _sourcePath

  def sourcePath_=(fp: String): Unit = {
    require(Files.isRegularFile(Paths.get(fp)), () => "Path is not a readable file.")
    _sourcePath = fp
  }

  def readLines(): List[String] = {           // gets the lines from the file in _sourcePath
    val file = Source.fromFile(_sourcePath)
    file.getLines().filter(!_.isEmpty).toList
  }

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
          case "[Events]"       => ???
          case "[General]"      => ???
          case "[Editor]"       => ???
          case "[Metadata]"     => ???
          case "[Difficulty]"   => ???
          case "[TimingPoints]" =>
            val timingPointLegacy = readTimingPoint(l)
            if (timingPointLegacy.isInstanceOf[Uninherited_legacy]) {
              val t: TimingPoint = timingPointLegacy
              map.addTimingPoint(t)
            }
            tps += timingPointLegacy
          case "[HitObjects]"   => map.addObject(readObject(l))
          case _ => ???
      }
    }

    var iT = 0
    map.allObjects.foreach { obj =>
      if (iT < tps.length) {
        if ((tps(iT).time <= obj.time) && (tps(iT + 1).time > obj.time)) {
          applyTP(obj, tps(iT))
        }
        else if (obj.time <= tps(0).time) {
          iT += 1
          applyTP(obj, tps(iT))
        }
      }
      
      obj match {
        case slider: Slider =>
          slider.endTime
        case _ =>
      }
    }

    def applyTP(ho: HitObject, tp: TimingPoint_legacy): Unit = {
      if (ho.hitsound.sampleSet == 0) ho.hitsound.sampleSet = tp.sampleSet
      if (ho.hitsound.sampleIndex == 0) ho.hitsound.sampleIndex = tp.sampleIndex
      if (ho.additions(0).sampleSet == 0) ho.additions(0).sampleSet = tp.sampleSet
      if (ho.additions(1).sampleSet == 0) ho.additions(1).sampleSet = tp.sampleSet
      if (ho.additions(2).sampleSet == 0) ho.additions(2).sampleSet = tp.sampleSet
      ho.additions(0).sampleIndex = tp.sampleIndex
      ho.additions(1).sampleIndex = tp.sampleIndex
      ho.additions(2).sampleIndex = tp.sampleIndex

      ho match {
        case sl: Slider =>
          if (tp.isInstanceOf[Uninherited_legacy]) sl.velocity = 1.0
          tp match {
            case inh: Inherited_legacy => sl.velocity = 100.0 / (inh.svMultiplier * -1)
            case _: Uninherited_legacy => sl.velocity = 1.0
            case _ => throw new Exception("Timing Point error: TP isn't inherited nor uninherited")
          }
        case _ =>
      }
    }
    map
  }

        /////// OBJECT READER ///////
  def readObject(line: String): HitObject = {                     // reads Object type then redirects to respective methods
    val properties = line.split(", *")                     // all Object readers are overloaded for an array of properties or a line string containing an object

    properties(3).toInt & 11 match {
      case 1 => readCircle(properties)
      case 2 => readSlider(properties)
      case 8 => readSpinner(properties)
    }
  }

    // Hit Circle syntax: [x,y,time,type,hitSound,extras]
  def readCircle(properties: Array[String]): Circle = {
    val circle = new Circle((properties(0).toInt, properties(1).toInt), properties(2).toInt)

    if (!(properties.length < 5)) {
      val h = readActiveHitsound(properties(5), properties(4))
      circle.hitsound = h._1
      circle.additions = h._2
    }
    else {
      circle.hitsound = new Hitsound()
      circle.additions = Array(new Addition(), new Addition(), new Addition())
    }

    circle
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
    //  get last node and save
    val endingPoint = sliderNodes.last.map(_.toInt)

    val slider = new Slider((properties(0).toInt, properties(1).toInt), (endingPoint(0), endingPoint(1)) , properties(2).toInt, properties(2).toInt + 1, repeats)

    //  save nodes from sliderNodes as Node types as red and grey nodes
    var skip = false
    for (a <- sliderNodes){
      if(!skip) {
        if (!(sliderNodes.indexOf(a) == sliderNodes.length - 1) && (sliderNodes(sliderNodes.indexOf(a)) sameElements sliderNodes(sliderNodes.indexOf(a) + 1))) {
          slider.addNode((a(0).toInt, a(1).toInt), 1)
          skip = true
        }
        else slider.addNode((a(0).toInt, a(1).toInt), 0)
      }
      skip = false
    }

    //hitsounds getting from pipe-separated lists
    if (properties.length > 8) {
      val setsIndexes = properties(9).split("\\|").map(_.split(":").map(_.toInt))   //Array(Array(index,set))
      val additionsHs = properties(8).split("\\|").map(_.toInt)   //Array of additions for each edge of slider (head, repeats, end) so repeats + 2

      slider.hitsound = new Hitsound(setsIndexes(0)(1), setsIndexes(0)(0))
      slider.additions = readAdditionBit(additionsHs(0))

      setsIndexes.drop(1)
      additionsHs.drop(1)

      for (i <- 0 to slider.repeats + 1) {
        slider.repeatHitsounds(i) = (new Hitsound(setsIndexes(i)(1), setsIndexes(i)(0)), readAdditionBit(additionsHs(0)))
      }
    }
    else {
      slider.hitsound = new Hitsound()
      slider.additions = Array.fill(3)(new Addition())

      slider.repeatHitsounds = Array.fill(slider.repeats + 1)((new Hitsound, Array.fill(3)(new Addition)))
    }

    slider
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
    val spinner = new Spinner(properties(2).toInt, properties(5).toInt)

    if (!(properties.length < 7)) {
      val h = readActiveHitsound(properties(6), properties(4))
      spinner.hitsound = h._1
      spinner.additions = h._2
    }
    else {
      spinner.hitsound = new Hitsound()
      spinner.additions = Array(new Addition(), new Addition(), new Addition())
    }
    spinner
  }

    // spinner overload for string line
  def readSpinner(line: String): Spinner = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 8, () => "Line does not contain a spinner")
    readSpinner(properties)
  }

    // combines extras and addition bit FOR CIRCLES AND SPINNERS
  def readActiveHitsound(ext: String, adb: String): (Hitsound, Array[Addition]) = {                           // ONLY FOR CIRCLES AND SPINNERS
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
    var bpm: Double = 0.0  // bpm
    var svmult: Double = 0.0  // sv multiplier

    if (mspb >= 0.0) {  // if millisec per beat is positive
      bpm = 60000.0 / mspb  // make a bpm
    }
    else svmult = 100.0 / (mspb * -1) // else make an sv

    val kiai = if(properties.equals("1")) true else false

    if (properties(6)== "1") {
      new Inherited_legacy(time, svmult, properties(3).toInt, properties(4).toInt, properties(5).toInt, kiai)
    }
    else new Uninherited_legacy(time, bpm, properties(2).toInt, properties(3).toInt, properties(4).toInt, properties(5).toInt, kiai)
  }
}
