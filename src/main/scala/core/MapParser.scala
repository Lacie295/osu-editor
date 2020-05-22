package core

import java.nio.file.{Files, Paths}

import ObjectHandler._
import components.Slider
import scala.io.Source

class MapParser() {
  def readFromFile(filename: String): Map = {
    require(Files.isRegularFile(Paths.get(filename)), () => "Path is not a readable file.")
    val file = Source.fromFile(filename)
    parse(file.getLines().filter(!_.isEmpty).toList)
  }

  def parse(map: String): Map = {
    val data = map.split("\n").toList
    parse(data)
  }

  def parse(map: List[String]): Map = {
    val m = Map()

    var prevSlider: Option[Slider] = None
    var i = 0

    map.foreach(line => {
      val split = line.split(":")
      val qualifier = split(0).strip().toLowerCase()
      val data = if (split.size > 1) split(1).strip() else ""
      i += 1

      if (qualifier != "node")
        prevSlider = None

      qualifier match {
        case "song" => m.song = data
        case "unicode song" => m.unicodeSong = data
        case "artist" => m.artist = data
        case "unicode artist" => m.unicodeArtist = data
        case "creator" => m.creator = data
        case "difficulty" => m.difficulty = data
        case "source" => m.source = data
        case "tags" => m.tags = data
        case "id" => m.id = data.toInt
        case "setid" => m.setId = data.toInt

        case "hp" => m.hp = readDouble(data, qualifier, i)
        case "cs" => m.cs = readDouble(data, qualifier, i)
        case "od" => m.od = readDouble(data, qualifier, i)
        case "ar" => m.ar = readDouble(data, qualifier, i)

        case "tick rate" => m.tickrate = readInt(data, qualifier, i)
        case "stack leniency" => m.stackLeniency = readDouble(data, qualifier, i)
        case "song file" => m.songFile = data
        case "background" => m.backgroundFile = data

        case "circle" =>
          try {
            val args = data.split("\\s+")
            var curr = 0
            while (curr < args.length) {
              args(curr) match {
                case "at" =>
                  curr += 1
                  val param = args(curr)
                  if (param endsWith "ms")
                    timestamp = readInt(param.dropRight(2), "time stamp", i)
                  else if (param.startsWith("x") && args(curr + 1).startsWith("y")) {
                    val x = readInt(param.drop(1), "position", i)
                    curr += 1
                    val y = readInt(args(curr).drop(1), "position", i)
                    position = (x, y)
                  } else {
                    unexpected(i)
                  }
                case "with" =>
                  curr += 1
                  val ss = args(curr)

                  curr += 1
                  val si = args(curr)

                  if (ss.startsWith("ss") && si.startsWith("si")) {
                    sampleset = readInt(ss.drop(2), "sample set", i)
                    sampleindex = readInt(si.drop(2), "sample index", i)
                  } else {
                    unexpected(i)
                  }
                case _ =>
                  unexpected(i)
              }
              curr += 1
            }
            m addObject MakeCircle
          } catch {
            case _: IndexOutOfBoundsException => throw new IllegalArgumentException("Missing data at row " + i + ".")
          }

        case "slider" =>
          try {
            val args = data.split("\\s+")
            var curr = 0
            while (curr < args.length) {
              args(curr) match {
                case "at" =>
                  curr += 1
                  val param = args(curr)
                  if (param endsWith "ms") {
                    timestamp = readInt(param.dropRight(2), "time stamp", i)
                    endtimestamp = 1 + timestamp
                  } else if (param.startsWith("x") && args(curr + 1).startsWith("y")) {
                    val x = readInt(param.drop(1), "position", i)
                    curr += 1
                    val y = readInt(args(curr).drop(1), "position", i)
                    position = (x, y)
                  } else {
                    unexpected(i)
                  }
                case "speed" =>
                  curr += 1
                  multiplier = readDouble(args(curr), "multiplier", i)
                case "repeats" =>
                  curr += 1
                  repeat = readInt(args(curr), "repeats", 1)
                case "with" =>
                  curr += 1
                  val ss = args(curr)

                  curr += 1
                  val si = args(curr)

                  if (ss.startsWith("ss") && si.startsWith("si")) {
                    sampleset = readInt(ss.drop(2), "sample set", i)
                    sampleindex = readInt(si.drop(2), "sample index", i)
                  } else {
                    unexpected(i)
                  }
                case _ =>
                  unexpected(i)
              }
              curr += 1
            }
            prevSlider = Some(MakeSlider)
            m addObject prevSlider.get
          } catch {
            case _: IndexOutOfBoundsException => throw new IllegalArgumentException("Missing data at row " + i + ".")
          }

        case "node" =>
          try {
            val args = data.split("\\s+")
            var curr = 0
            var nodeType = 0
            while (curr < args.length) {
              args(curr) match {
                case "at" =>
                  curr += 1
                  val param = args(curr)
                  if (param.startsWith("x") && args(curr + 1).startsWith("y")) {
                    val x = readInt(param.drop(1), "position", i)
                    curr += 1
                    val y = readInt(args(curr).drop(1), "position", i)
                    position = (x, y)
                  } else {
                    unexpected(i)
                  }
                case "type" =>
                  curr += 1
                  nodeType = readInt(args(curr), "node type", i)
                case _ =>
                  unexpected(i)
              }
              curr += 1
            }
            if (prevSlider.isEmpty)
              throw new IllegalArgumentException("Lose node at row " + i + ".")
            else
              prevSlider.get.addNode(position, nodeType)
          } catch {
            case _: IndexOutOfBoundsException => throw new IllegalArgumentException("Missing data at row " + i + ".")
          }

        case "spinner" =>
          try {
            val args = data.split("\\s+")
            var curr = 0
            while (curr < args.length) {
              args(curr) match {
                case "at" | "until" =>
                  curr += 1
                  val param = args(curr)
                  if (param endsWith "ms")
                    if (args(curr - 1) == "at")
                      timestamp = readInt(param.dropRight(2), "time stamp", i)
                    else
                      endtimestamp = readInt(param.dropRight(2), "time stamp", i)
                  else {
                    unexpected(i)
                  }
                case "with" =>
                  curr += 1
                  val ss = args(curr)

                  curr += 1
                  val si = args(curr)

                  if (ss.startsWith("ss") && si.startsWith("si")) {
                    sampleset = readInt(ss.drop(2), "sample set", i)
                    sampleindex = readInt(si.drop(2), "sample index", i)
                  } else {
                    unexpected(i)
                  }
                case _ =>
                  unexpected(i)
              }
              curr += 1
            }
            m addObject MakeSpinner
          } catch {
            case _: IndexOutOfBoundsException => throw new IllegalArgumentException("Missing data at row " + i + ".")
          }

        case "timingpoint" =>
          try {
            val args = data.split("\\s+")
            var curr = 0
            while (curr < args.length) {
              args(curr) match {
                case "at" =>
                  curr += 1
                  val param = args(curr)
                  if (param endsWith "ms")
                    timestamp = readInt(param.dropRight(2), "time stamp", i)
                  else {
                    unexpected(i)
                  }
                case "bpm" =>
                  curr += 1
                  bpm = readDouble(args(curr), "bpm", i)
                case "measure" =>
                  curr += 1
                  meterA = readInt(args(curr), "repeats", 1)
                  curr += 1
                  if (args(curr) != "/")
                    throw new IllegalArgumentException("Bad time signature at row " + i + ".")
                  curr += 1
                  meterB = readInt(args(curr), "repeats", 1)
                case _ =>
                  unexpected(i)
              }
              curr += 1
            }
            m addTimingPoint MakeTimingPoint
          } catch {
            case _: IndexOutOfBoundsException => throw new IllegalArgumentException("Missing data at row " + i + ".")
          }

        case _ =>
      }
    })

    m
  }

  def readInt(data: String, name: String, row: Int): Int = {
    if (data.matches("^\\d+$"))
      data.toInt
    else
      throw new IllegalArgumentException("Illegal value for " + name + " at line " + row + ". " + data)
  }

  def readDouble(data: String, name: String, row: Int): Double = {
    if (data.matches("^(\\d+\\.?)|(\\.\\d+)|(\\d+\\.\\d+)$"))
      data.toDouble
    else
      throw new IllegalArgumentException("Illegal value for " + name + " at line " + row + ". " + data)
  }

  def unexpected(row: Int): Unit = throw new IllegalArgumentException("Unexpected token at row " + row + ".")
}

object MapParser {
  def apply(): MapParser = new MapParser()
}
