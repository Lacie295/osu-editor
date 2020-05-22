package coreTest

import components.TimingPoint
import core.MapParser
import core.ObjectHandler._
import core.MapHandler._

class MapParserTest extends BaseTest {
  "A parser" should "properly read metadata" in {
    val mapString = "Metadata\n" +
      "\tSong: Old Yuanxian ~ Death Echo\n" +
      "\tUnicode song: 古きユアンシェン ～ Death Echo\n" +
      "\tArtist: Demetori\n" +
      "\tUnicode artist: Demetori\n" +
      "\tCreator: GoldenWolf\n" +
      "\tDifficulty: Extra Stage\n" +
      "\tSource: 東方神霊廟　～ Ten Desires.\n" +
      "\tTags: kaku seiga death echo old yuanshen tendre est la mort introduction le grimoire de reve c83 c85 power speed metal\n" +
      "\tID: 929660\n" +
      "\tSetID: 430959"

    val map = MapParser().parse(mapString)

    assert(map.song == "Old Yuanxian ~ Death Echo")
    assert(map.unicodeSong == "古きユアンシェン ～ Death Echo")
    assert(map.artist == "Demetori")
    assert(map.unicodeArtist == "Demetori")
    assert(map.creator == "GoldenWolf")
    assert(map.difficulty == "Extra Stage")
    assert(map.source == "東方神霊廟　～ Ten Desires.")
    assert(map.tags == "kaku seiga death echo old yuanshen tendre est la mort introduction le grimoire de reve c83 c85 power speed metal")
    assert(map.id == 929660)
    assert(map.setId == 430959)
  }

  it should "properly read difficulty" in {
    val mapString = "Difficulty\n" +
      "\tHP: 5.5\n" +
      "\tCS: 4.5\n" +
      "\tOD: 9\n" +
      "\tAR: 9.5"

    val map = MapParser().parse(mapString)

    assert(map.hp == 5.5)
    assert(map.cs == 4.5)
    assert(map.od == 9)
    assert(map.ar == 9.5)
  }

  it should "properly read settings" in {
    val mapString = "Settings\n" +
      "\tTick rate: 1\n" +
      "\tStack leniency: 0.2\n" +
      "\tSong file: audio.mp3\n" +
      "\tBackground: beautiful_pic.png"

    val map = MapParser().parse(mapString)

    assert(map.tickrate == 1)
    assert(map.stackLeniency == 0.2)
    assert(map.songFile == "audio.mp3")
    assert(map.backgroundFile == "beautiful_pic.png")
  }

  it should "properly read circles" in {
    val mapString = "Objects\n" +
      "\tCircle: at 150ms at x23 y94 with ss0 si1\n" +
      "\tCircle: at 299ms at x92 y221 with ss1 si2"

    val map = MapParser().parse(mapString)

    assert((map getCircle 150).position == MakePosition(23, 94))
    assert((map getCircle 299).position == MakePosition(92, 221))
  }

  it should "properly read sliders" in {
    val mapString = "Objects\n" +
      "\tSlider: at 150ms at x23 y94 speed 1.2 repeats 3 with ss0 si1\n" +
      "\t\tNode: at x25 y83 type 0\n" +
      "\t\tNode: at x51 y80 type 1\n" +
      "\tSlider: at 299ms at x111 y53 speed 3.1 repeats 0 with ss1 si2\n" +
      "\t\tNode: at x100 y51 type 1"

    val map = MapParser().parse(mapString)

    assert((map getSlider 150).position == MakePosition(23, 94))
    assert((map getSlider 150).size == 3)
    assert((map getSlider 150)(1).position == MakePosition(25, 83))
    assert((map getSlider 150)(2).position == MakePosition(51, 80))
    assert((map getSlider 299).position == MakePosition(111, 53))
    assert((map getSlider 299).size == 2)
    assert((map getSlider 299)(1).position == MakePosition(100, 51))
  }

  it should "properly read spinners" in {
    val mapString = "Objects\n" +
      "\tSpinner: at 150ms until 190ms with ss0 si1\n" +
      "\tSpinner: at 299ms until 310ms with ss1 si2"

    val map = MapParser().parse(mapString)

    assert((map getSpinner 150).hitsound == MakeHitsound(0, 1))
    assert((map getSpinner 150).overlaps(170))
    assert((map getSpinner 299).hitsound == MakeHitsound(1, 2))
    assert((map getSpinner 299).overlaps(300))
  }

  it should "properly read timestamps" in {
    val mapString = "Timestamp\n" +
      "\tTimingPoint: bpm 200 at 150ms measure 4 / 4\n" +
      "\tTimingPoint: bpm 200 at 299ms measure 3 / 4"

    val map = MapParser().parse(mapString)

    assert((map getTimingPoint 150) == (map getTimingPoint 200))
    assert((map getTimingPoint 299) == (map getTimingPoint 300))
    assert((map getTimingPoint 150) != (map getTimingPoint 300))
    assert((map getTimingPoint 150).asInstanceOf[TimingPoint].bpm == 200)
    assert((map getTimingPoint 150).asInstanceOf[TimingPoint].meterA == 4)
    assert((map getTimingPoint 299).asInstanceOf[TimingPoint].bpm == 200)
    assert((map getTimingPoint 299).asInstanceOf[TimingPoint].meterA == 3)
  }
}
