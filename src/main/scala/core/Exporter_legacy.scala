package core

import components.{Circle, HitObject, Slider, Spinner, TimingPoint, TimingPoint_legacy, Uninherited_legacy}

import scala.collection.mutable.ListBuffer

class Exporter_legacy(m: Map, v: Int) {
  val VERSION: Int = v

  def export: String = {
    var general = new StringBuilder("[General]\n")
    var editor = new StringBuilder("[Editor]\n")
    var data = new StringBuilder("[Metadata]\n")
    var difficulty = new StringBuilder("[Difficulty]\n")
    var timingpoints = new StringBuilder("[TimingPoints]\n")
    var colours = new StringBuilder("[Colours]\n")
    var HitObjects = new StringBuilder("[HitObjects]\n")
    var events = new StringBuilder("[Events]\n")

    general ++= "AudioFilename: " + m.songFile + "\n"
    general ++= "AudioLeadIn: 0\n" + "\n"
    general ++= "PreviewTime: 0" + "\n" // TODO
    general ++= "Countdown: 0" + "\n" // TODO
    general ++= "SampleSet: None" + "\n"
    general ++= "StackLeniency: " + m.stackLeniency + "\n"
    general ++= "Mode: 0" + "\n"
    general ++= "LetterboxInBreaks: 0" + "\n"
    general ++= "EpilepsyWarning: 0" + "\n" // TODO
    general ++= "WidescreenStoryboard: 1" + "\n" // TODO

    editor ++= "Bookmarks: " + "\n" // TODO ?
    editor ++= "DistanceSpacing: 1" + "\n"
    editor ++= "BeatDivisor: 4" + "\n"
    editor ++= "GridSize: 4" + "\n"
    editor ++= "TimelineZoom: 1" + "\n"

    data ++= "Title:" + m.music + "\n"
    data ++= "TitleUnicode:" + m.unicodeMusic + "\n"
    data ++= "Artist:" + m.artist + "\n"
    data ++= "ArtistUnicode:" + m.unicodeArtist + "\n"
    data ++= "Creator:" + m.creator + "\n"
    data ++= "Version:" + m.difficulty + "\n"
    data ++= "Source:" + m.source + "\n"
    data ++= "Tags" + m.tags + "\n"
    data ++= "BeatmapID:" + m.id + "\n"
    data ++= "BeatmapSetID:" + m.setId + "\n"

    difficulty ++= "HPDrainRate:" + m.hp + "\n"
    difficulty ++= "CircleSize:" + m.cs + "\n"
    difficulty ++= "OverallDifficulty:" + m.od + "\n"
    difficulty ++= "ApproachRate:" + m.artist + "\n"
    difficulty ++= "SliderMultiplier:" + m.sliderVelocity + "\n"
    difficulty ++= "SliderTickRate:" + m.tickrate + "\n"

    events ++= "//Background and Video events\n"
    events ++= "0,0,\"" + m.backgroundFile + "\",0,0" + "\n"
    events ++= "//Break Periods\n"
    events ++= "//Storyboard Layer 0 (Background)\n"
    events ++= "//Storyboard Layer 1 (Fail)\n"
    events ++= "//Storyboard Layer 2 (Pass)\n"
    events ++= "//Storyboard Layer 3 (Foreground)\n"
    events ++= "//Storyboard Sound Samples\n"

    var ic = 1
    m.colours.foreach(c => {
      colours ++= "Combo" + ic + " : " + c._1 + "," + c._2 + "," + c._3 + "\n"
      ic += 1
    })

    var tps = new ListBuffer[TimingPoint_legacy]

    m.allTimingPoints.foreach(tp => {
      tps.addOne(new Uninherited_legacy(tp.timeStamp, tp.asInstanceOf[TimingPoint].bpm, tp.asInstanceOf[TimingPoint].meterA, 0, 0, 100, false))
    })

    m.allObjects.foreach(o => {
      o match {
        case circle: Circle => HitObjects ++= o.x + "," + o.y + "," + o.time + "," + generateTypeBitmap(o)
        case slider: Slider => HitObjects ++=
      }
    })
  }

  def generateTypeBitmap(o: HitObject): String = {
    var bits: Int = 0;
    o match {
      case circle: Circle => bits += 1
      case slider: Slider => bits += 2
      case spinner: Spinner => bits += 8
    }
    if (o.newCombo) bits += 4

    bits.toString
  }

  def generateHitsoundBitmap(o: HitObject): String = {

  }

}
