package core

import java.nio.file.{Files, Paths}

import ObjectHandler._
import components.Slider

import scala.collection.mutable
import scala.io.Source

class MapParser(filename: String) {
  require(Files.isRegularFile(Paths.get(filename)), () => "Path is not a readable file.")

  def readFromFile(): Map = {
    val file = Source.fromFile(filename)
    parse(file.getLines().filter(!_.isEmpty).toList)
  }

  def parse(map: List[String]): Map = {
    val m = Map()

    var prevSlider: Option[Slider] = None

    map.foreach(line => {
      val split = line.split(":")
      val qualifier = split(0).strip().toLowerCase()
      val data = split(1).strip()

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

        case "hp" => m.hp = data.toDouble
        case "cs" => m.cs = data.toDouble
        case "od" => m.od = data.toDouble
        case "ar" => m.ar = data.toDouble

        case "tick rate" => m.tickrate = data.toInt
        case "stack leniency" => m.stackLeniency = data.toDouble
        case "song file" => m.songFile = data
        case "background" => m.backgroundFile = data

        case "circle" => ???
        case "slider" => ???
        case "node" => ???
        case "spinner" => ???

        case "timingpoint" => ???
      }
    })

    m
  }
}
