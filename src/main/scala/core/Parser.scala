package core

import scala.io.Source
import java.nio.file.{Files, Paths}

import components.{Circle, HitObject, Slider, Spinner}

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

  def readCircle(properties: Array[String]): HitObject = {
    val circle = new Circle((properties(0).toInt, properties(1).toInt), properties(2).toInt)
    circle
  }

  def readCircle(line: String): HitObject = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 1, () => "Line does not contain a circle")
    readCircle(properties)
  }

  def readSlider(properties: Array[String]): HitObject = {
    val sliderArray = properties(5).split("\\|")
    val sliderNodes = sliderArray.drop(1).map(_.split(":"))
    val repeats = properties(6).toInt - 1
    val endingPoint = sliderNodes.last.map(_.toInt)

    val slider = new Slider((properties(0).toInt, properties(1).toInt), (endingPoint(0), endingPoint(1)) , properties(2).toInt, properties(2).toInt + 1, repeats)

    var skip = 0
    for (a <- sliderNodes){
      if(skip == 0) {
        if (!(sliderNodes.indexOf(a) == sliderNodes.length - 1) && (sliderNodes(sliderNodes.indexOf(a)) sameElements sliderNodes(sliderNodes.indexOf(a) + 1))) {
          slider.addNode((a(0).toInt, a(1).toInt), 1)
          skip = 1
        }
        else slider.addNode((a(0).toInt, a(1).toInt), 0)
      }
      skip = 0
    }
    slider
  }

  def readSlider(line: String): HitObject = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 2, () => "Line does not contain a slider")
    readSlider(properties)
  }

  def readSpinner(properties: Array[String]) = {
    val spinner = new Spinner(properties(2).toInt, properties(5).toInt)
    spinner
  }

  def readSpinner(line: String): HitObject = {
    val properties = line.split(", *")
    require((properties(3).toInt & 11) == 8, () => "Line does not contain a spinner")
    readSlider(properties)
  }
}
