package core

import components.{Circle, Slider, Spinner, TimingPoint}

class MapExporter {
  val VERSION = 0
  def export(m: Map): String = {
    var data: StringBuilder = new StringBuilder("Metadata")
    var difficulty: StringBuilder = new StringBuilder("Difficulty")
    var objects: StringBuilder = new StringBuilder("Objects")
    var timestamps: StringBuilder = new StringBuilder("Timestamps")

    data ++= "\tSong: " + m.song
    data ++= "\tUnicode song: " + m.unicodeSong
    data ++= "\tArtist: " + m.artist
    data ++= "\tUnicode artist: " + m.unicodeArtist
    data ++= "\tCreator: " + m.creator
    data ++= "\tDifficulty: " + m.difficulty
    data ++= "\tSource: " + m.source
    data ++= "\tTags: " + m.tags
    data ++= "\tID: " + m.id
    data ++= "\tSetID: " + m.setId

    difficulty ++= "\tHP: " + m.hp
    difficulty ++= "\tCS: " + m.cs
    difficulty ++= "\tOD: " + m.od
    difficulty ++= "\tAR: " + m.ar

    m.allObjects.foreach {
      obj => {
        obj match {
          case c: Circle =>
            objects ++= "\tcircle"
            objects ++= " at " + c.timeStamp
            objects ++= " at " + c.position
            objects ++= " with " + c.hitsound
          case s: Slider =>
            objects ++= "\tslider"
            objects ++= " at " + s.timeStamp
            objects ++= " at " + s.position
            objects ++= " speed " + s.velocity
            objects ++= " repeats " + s.repeats
            objects ++= " with " + s.hitsound
            s.nodes.foreach(node => {
              objects ++= "\n\t\tnode"
              objects ++= " at " + s.position
            })
          case s: Spinner =>
            objects ++= "\tspinner"
            objects ++= " at " + s.timeStamp
            objects ++= " until " + s.endTimeStamp
            objects ++= " with " + s.hitsound
        }
      }
    }

    m.allTimingPoints.foreach {
      obj => {
        obj match {
          case t: TimingPoint =>
            timestamps ++= "\t" + t.bpm
            timestamps ++= " at " + t.timeStamp
            timestamps ++= " measure " + t.meterA
            timestamps ++= " / " + t.meterB
        }
      }
    }

    "Version: " + VERSION + "\n\n" + data.mkString + "\n\n" + difficulty.mkString + "\n\n" + objects.mkString + "\n\n" + timestamps.mkString
  }
}
