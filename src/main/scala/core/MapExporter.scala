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
            if (timestamp != c.timeStamp || !DENSE) {
              objects ++= " at " + c.timeStamp
              timestamp = c.timeStamp
            }
            if (position != c.position || !DENSE) {
              objects ++= " at " + c.position
              position = c.position
            }
            if (hitsound != c.hitsound || !DENSE) {
              objects ++= " with " + c.hitsound
              hitsound = c.hitsound
            }
            objects ++= "\n"
          case s: Slider =>
            objects ++= "\tSlider:"
            if (timestamp != s.timeStamp || !DENSE) {
              objects ++= " at " + s.timeStamp
              timestamp = s.timeStamp
            }
            if (position != s.position || !DENSE) {
              objects ++= " at " + s.position
              position = s.position
            }
            if (multiplier != s.velocity || !DENSE) {
              objects ++= " speed " + s.velocity
              multiplier = s.velocity
            }
            if (repeat != s.repeats || !DENSE) {
              objects ++= " repeats " + s.repeats
              repeat = s.repeats
            }
            if (hitsound != s.hitsound || !DENSE) {
              objects ++= " with " + s.hitsound
              hitsound = s.hitsound
            }
            objects ++= "\n"
            s.nodes.drop(1).foreach(node => {
              objects ++= "\t\tNode:"
              if (position != node.position || !DENSE) {
                objects ++= " at " + node.position
                position = node.position
              }
              objects ++= " type " + node.nodeType
              objects ++= "\n"
            })
          case s: Spinner =>
            objects ++= "\tSpinner:"
            if (timestamp != s.timeStamp || !DENSE) {
              objects ++= " at " + s.timeStamp
              timestamp = s.timeStamp
            }
            if (endtimestamp != s.endTimeStamp || !DENSE) {
              objects ++= " until " + s.endTimeStamp
              endtimestamp = s.endTimeStamp
            }
            if (hitsound != s.hitsound || !DENSE) {
              objects ++= " with " + s.hitsound
              hitsound = s.hitsound
            }
            objects ++= "\n"
        }
      }
    }

    m.allTimingPoints.foreach {
      obj => {
        obj match {
          case t: TimingPoint =>
            timestamps ++= "\tTimingPoint:"
            if (timestamp != t.timeStamp || !DENSE) {
              timestamps ++= " at " + t.timeStamp
              timestamp = t.timeStamp
            }
            if (bpm != t.bpm || !DENSE) {
              timestamps ++= " bpm " + t.bpm
              bpm = t.bpm
            }
            if (meterA != t.meterA || meterB != t.meterB || !DENSE) {
              timestamps ++= " measure " + t.meterA
              meterA = t.meterA
              timestamps ++= " / " + t.meterB
              meterB = t.meterB
            }
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
