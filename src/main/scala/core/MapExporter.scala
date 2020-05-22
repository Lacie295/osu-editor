package core

import java.io.{BufferedWriter, File, FileWriter}

import components.{Circle, Slider, Spinner, TimingPoint}

class MapExporter(m: Map) {
  val VERSION = 0

  def export: String = {
    var data: StringBuilder = new StringBuilder("Metadata\n")
    var difficulty: StringBuilder = new StringBuilder("Map Difficulty\n")
    var settings: StringBuilder = new StringBuilder("Settings\n")
    var objects: StringBuilder = new StringBuilder("Objects\n")
    var timestamps: StringBuilder = new StringBuilder("Timestamps\n")

    data ++= "\tSong: " + m.song + "\n"
    data ++= "\tUnicode song: " + m.unicodeSong + "\n"
    data ++= "\tArtist: " + m.artist + "\n"
    data ++= "\tUnicode artist: " + m.unicodeArtist + "\n"
    data ++= "\tCreator: " + m.creator + "\n"
    data ++= "\tDifficulty: " + m.difficulty + "\n"
    data ++= "\tSource: " + m.source + "\n"
    data ++= "\tTags: " + m.tags + "\n"
    data ++= "\tID: " + m.id + "\n"
    data ++= "\tSetID: " + m.setId + "\n"

    difficulty ++= "\tHP: " + m.hp + "\n"
    difficulty ++= "\tCS: " + m.cs + "\n"
    difficulty ++= "\tOD: " + m.od + "\n"
    difficulty ++= "\tAR: " + m.ar + "\n"

    settings ++= "\tTick rate: " + m.tickrate + "\n"
    settings ++= "\tStack leniency: " + m.stackLeniency + "\n"
    settings ++= "\tSong file: " + m.songFile + "\n"
    settings ++= "\tBackground: " + m.backgroundFile + "\n"

    m.allObjects.foreach {
      obj => {
        obj match {
          case c: Circle =>
            objects ++= "\tCircle:"
            objects ++= " at " + c.timeStamp
            objects ++= " at " + c.position
            objects ++= " with " + c.hitsound
            objects ++= "\n"
          case s: Slider =>
            objects ++= "\tSlider:"
            objects ++= " at " + s.timeStamp
            objects ++= " at " + s.position
            objects ++= " speed " + s.velocity
            objects ++= " repeats " + s.repeats
            objects ++= " with " + s.hitsound
            objects ++= "\n"
            s.nodes.drop(1).foreach(node => {
              objects ++= "\t\tNode:"
              objects ++= " at " + node.position
              objects ++= " type " + node.nodeType
              objects ++= "\n"
            })
          case s: Spinner =>
            objects ++= "\tSpinner:"
            objects ++= " at " + s.timeStamp
            objects ++= " until " + s.endTimeStamp
            objects ++= " with " + s.hitsound
            objects ++= "\n"
        }
      }
    }

    m.allTimingPoints.foreach {
      obj => {
        obj match {
          case t: TimingPoint =>
            timestamps ++= "\tTimingPoint:"
            timestamps ++= " bpm " + t.bpm
            timestamps ++= " at " + t.timeStamp
            timestamps ++= " measure " + t.meterA
            timestamps ++= " / " + t.meterB
            timestamps ++= "\n"
        }
      }
    }

    "Version: " + VERSION + "\n\n" + data.mkString + "\n" + difficulty.mkString + "\n" + settings.mkString + "\n" + objects.mkString + "\n" + timestamps.mkString
  }

  def writeToFile(filename: String): Unit = {
    val file = new File(filename)
    val writer = new BufferedWriter(new FileWriter(file))
    writer.write(export)
    writer.close()
  }
}

object MapExporter {
  def apply(m: Map): MapExporter = new MapExporter(m)
}
