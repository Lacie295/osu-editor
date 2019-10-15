package core

import scala.io.Source
import java.nio.file.{Files, Paths}

import components.{Circle, HitObject, Slider, Spinner}
import utils.{Addition, Hitsound}

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

  val Headers: Set[String] = Set("[Events]", "[General]", "[Editor]", "[Metadata]", "[Difficulty]", "[TimingPoints]", "[HitObjects]")
  def readMap(): Map = {
    var mode = ""

    val map = new Map()

    for (l <- readLines()) {
        if (Headers.contains(l)) mode = l
        else mode match {
          case "[Events]"       => ???
          case "[General]"      => ???
          case "[Editor]"       => ???
          case "[Metadata]"     => ???
          case "[Difficulty]"   => ???
          case "[TimingPoints]" => ???
          case "[HitObjects]"   => map.addObject(readObject(l))
          case _ => ???
      }
    }
    map
  }

        /////// VERSION READER ///////
  def readVersion(line: String): Int = {      // reads the osu file version from a line
    require(line.contains("osu file format v"), () => "cannot read file version")
    line.split("osu file format v").mkString("").toInt
  }

  def readVersion(lines: List[String]): Int = {   // overload file version from List[String]
    readVersion(lines.filter(!_.isEmpty)(0))
  }

  def readVersion(): Int = {    // overload file version from currently present _sourcePath file
    readVersion(readLines())
  }

        /////// OBJECT READER ///////
  def readObject(line: String): HitObject = {                     // reads Object type then redirects to respective methods
    val properties = line.split(", *")                        // all Object readers are overloaded for an array of properties or a line string containing an object

    properties(3).toInt & 11 match {
      case 1 => readCircle(properties)
      case 2 => readSlider(properties)
      case 8 => readSpinner(properties)
    }
  }

        // TODO: HITSOUNDS READER

  def readCircle(properties: Array[String]): Circle = {
    val circle = new Circle((properties(0).toInt, properties(1).toInt), properties(2).toInt)

    val e = readExtras(properties(5))
    val b = readAdditionBit(properties(4))

    for (s <- b) {
      s.sampleSet = e._2
      s.sampleIndex = e._3
    }

    circle.hitsound = new Hitsound(e._1.toInt, e._3.toInt)
    circle.additions = b

    circle
  }

  def readCircle(line: String): Circle = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 1, () => "Line does not contain a circle")
    readCircle(properties)
  }

  def readSlider(properties: Array[String]): Slider = {
    val sliderArray = properties(5).split("\\|")
    val sliderNodes = sliderArray.drop(1).map(_.split(":"))
    val repeats = properties(6).toInt - 1
    val endingPoint = sliderNodes.last.map(_.toInt)

    val slider = new Slider((properties(0).toInt, properties(1).toInt), (endingPoint(0), endingPoint(1)) , properties(2).toInt, properties(2).toInt + 1, repeats)

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
    slider
  }

  def readSlider(line: String): Slider = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 2, () => "Line does not contain a slider")
    readSlider(properties)
  }

  def readSpinner(properties: Array[String]): Spinner = {
    val spinner = new Spinner(properties(2).toInt, properties(5).toInt)
    spinner
  }

  def readSpinner(line: String): Spinner = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 8, () => "Line does not contain a spinner")
    readSpinner(properties)
  }

  def readAdditionBit(hsb: String): Array[Addition] = {
    val additionArray: Array[Addition] = Array(new Addition(0, 0, false), new Addition(0, 0, false), new Addition(0, 0, false))
    if ((hsb.toInt & 2) == 2) {
      additionArray(0).active = true
    }
    if ((hsb.toInt & 4) == 4) {
      additionArray(1).active = true
    }
    if ((hsb.toInt & 8) == 8) {
      additionArray(2).active = true
    }
    additionArray
  }

  def readExtras(extras: String): (Int, Int, Int, Int, String) = {
    val e = extras.split(":")
    (e(0).toInt, e(1).toInt, e(2).toInt, e(3).toInt, e(4))
  }
}
