package coreTest

import core.ObjectHandler._
import core.MapHandler._
import core.{Map, MapExporter, MapParser}

class MapExporterTest extends BaseTest {
  "An exporter" should "properly export a map in a way readable by the importer" in {
    val m: Map = Map()

    m.song = "Consciousness (pt. 1 & 2)"
    m.unicodeSong = m.song
    m.artist = "Persefone"
    m.unicodeArtist = m.artist
    m.creator = "GoldenWolf"
    m.difficulty = "Enlightenment"
    m.source = ""
    m.tags = "sitting in silence a path to enlightenment spiritual migration 2013 progressive melodic metal"
    m.id = 1483603
    m.setId = 700870

    m.hp = 6
    m.cs = 4.5
    m.od = 9
    m.ar = 9.5

    m.tickrate = 1
    m.stackLeniency = 0
    m.songFile = "audio.mp3"
    m.backgroundFile = "nice_pic.jpg"

    timestamp = 15
    position = (9, 10)

    m addObject MakeCircle

    timestamp = 92
    endtimestamp = 93
    position = (12, 15)
    repeat = 3
    multiplier = 0.5

    m addObject (MakeSlider addNode (12, 99) addNode (45, 95, 1))

    timestamp = 105
    endtimestamp = 120

    m addObject MakeSpinner

    timestamp = 15
    bpm = 150
    meterA = 5

    m addTimingPoint MakeTimingPoint

    val mapString = MapExporter(m).export
    val m2 = MapParser().parse(mapString)

    assert(m == m2)
  }
}
