package core

import java.nio.file.{Files, Paths}

import ObjectHandler._

import scala.io.Source

class MapParser(filename: String) {
  require(Files.isRegularFile(Paths.get(filename)), () => "Path is not a readable file.")

  def readFromFile(): Map = {
    val file = Source.fromFile(filename)
    parse(file.getLines().filter(!_.isEmpty).toList)
  }

  def parse(map: List[String]): Map = {
    val m = Map()

    var currLine = 0

    while (currLine < map.size) {
      val line = map(currLine)

      val qualifier = line.split(":")

      // TODO: parse all lines


      currLine += 1
    }

    m
  }
}
