package core

import java.io.{BufferedWriter, File, FileWriter}

import components.{Circle, Slider, Spinner, TimingPoint}
import core.ObjectHandler._

class MapExporter(m: Map) {
  val VERSION = 0
  val DENSE = false

  def export: String = {
    var data: StringBuilder = new StringBuilder("Metadata\n")
    var difficulty: StringBuilder = new StringBuilder("Map Difficulty\n")
    var settings: StringBuilder = new StringBuilder("Settings\n")
    var objects: StringBuilder = new StringBuilder("Objects\n")
    var timestamps: StringBuilder = new StringBuilder("Timestamps\n")

    data ++= "\tMusic name: " + m.music + "\n"
    data ++= "\tUnicode music name: " + m.unicodeMusic + "\n"
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

    timestamp = -1
    endtimestamp = -1
    position = (-1, -1)
    hitsound = (-1, -1)
    multiplier = -1
    repeat = -1
    bpm = -1
    meterA = -1
    meterB = -1

    m.allObjects.foreach {
      obj => {
        obj match {
          case c: Circle =>
            objects ++= "\tCircle:"
            objects ++= " at " + c.timeStamp
            objects ++= " at " + c.position
            objects ++= " with " + c.hitsound
            // 0-whistle, 1-finish, 2-clap
            objects ++= " additions " + c.additionsHitsound + " " + (if(c.additions(0)) "w" else "") + (if(c.additions(1)) "f" else "") + (if(c.additions(2)) "c" else "")
            objects ++= "\n"
          case s: Slider =>
            objects ++= "\tSlider:"
            objects ++= " at " + s.timeStamp
            objects ++= " at " + s.position
            objects ++= " speed " + s.velocity
            objects ++= " repeats " + s.repeats
            objects ++= " with " + s.hitsound
            // 0-whistle, 1-finish, 2-clap
            objects ++= " additions " + s.additionsHitsound + " " + (if(s.additions(0)) "w" else "") + (if(s.additions(1)) "f" else "") + (if(s.additions(2)) "c" else "")
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
            // 0-whistle, 1-finish, 2-clap
            objects ++= " additions " + s.additionsHitsound + " " + (if(s.additions(0)) "w" else "") + (if(s.additions(1)) "f" else "") + (if(s.additions(2)) "c" else "")
            objects ++= "\n"
        }
      }
    }

    m.allTimingPoints.foreach {
      obj => {
        obj match {
          case t: TimingPoint =>
            timestamps ++= "\tTimingPoint:"
            timestamps ++= " at " + t.timeStamp
            timestamps ++= " bpm " + t.bpm
            timestamps ++= " measure " + t.meterA
            meterA = t.meterA
            timestamps ++= " / " + t.meterB
            meterB = t.meterB
            timestamps ++= "\n"
        }
      }
    }

    "Version: " + VERSION + "\n\n" + data.mkString + "\n" + difficulty.mkString + "\n" + settings.mkString + "\n" + timestamps.mkString + "\n" + objects.mkString
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
