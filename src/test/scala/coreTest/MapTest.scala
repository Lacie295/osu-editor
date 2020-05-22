package coreTest

import components.{Circle, HitObject, Slider, Spinner, TimingPoint}
import core.Map

class MapTest extends BaseTest {
  "A map" should "store all its objects" in {
    val m: Map = new Map()

    m.addObject(new Slider((0, 0), 0, 1))
    m.addObject(new Circle((0, 0), 2))
    m.addObject(new Spinner(3, 4))

    val s: Slider = m.getObject(0).get.asInstanceOf[Slider]
    val s2: Slider = m.getObject(1).get.asInstanceOf[Slider]
    assert(s == s2)
    assert(s.x == 0)
    assert(s.y == 0)
    assert(s.time == 0)
    assert(s.endTime == 1)

    val c: Circle = m.getObject(2).get.asInstanceOf[Circle]
    assert(c.x == 0)
    assert(c.y == 0)
    assert(c.time == 2)

    val sp: Spinner = m.getObject(3).get.asInstanceOf[Spinner]
    val sp2: Spinner = m.getObject(4).get.asInstanceOf[Spinner]
    assert(sp == sp2)
    assert(sp.time == 3)
    assert(sp.endTime == 4)

    m.deleteObject(sp)
    val none: Option[HitObject] = m.getObject(3)
    assert(none.isEmpty)
  }

  it should "properly store timing points" in {
    val m: Map = new Map()

    m.addTimingPoint(new TimingPoint(0, 120))
    m.addTimingPoint(new TimingPoint(4, 130))

    assert(m.getTimingPoint(0).time == 0)
    assert(m.getTimingPoint(0).asInstanceOf[TimingPoint].bpm == 120)
    assert(m.getTimingPoint(6).time == 4)
    assert(m.getTimingPoint(6).asInstanceOf[TimingPoint].bpm == 130)
  }

  it should "handle overlapping objects" in {
    val m: Map = new Map()

    val s1 = new Slider((0, 0), 0, 5)
    m.addObject(s1)
    val s2 = new Slider((0, 0), 3, 8)
    m.addObject(s2)
    val c1 = new Circle((0, 0), 7)
    m.addObject(c1)

    assert(m.getOverlapObject(0) == List(s1))
    assert(m.getOverlapObject(3) == List(s1, s2))
    assert(m.getOverlapObject(5) == List(s1, s2))
    assert(m.getOverlapObject(6) == List(s2))
    assert(m.getOverlapObject(7) == List(s2, c1))
    assert(m.getOverlapObject(8) == List(s2))
  }

  it should "store its metadata properly" in {
    val m: Map = new Map()

    m.song = "Koyoi wa Hyouitsu na Egoist ~ Ego, Schizoid, Beat."
    m.unicodeSong = "今宵は飄逸なエゴイスト ～ Ego,Schizoid,Beat."
    m.artist = "Demetori"
    m.unicodeArtist = "Demetori"
    m.creator = "GoldenWolf"
    m.difficulty = "The Most Despicable and Disastrous God of Destitution and Misery."
    m.source = "東方憑依華　～ Antinomy of Common Flowers."
    m.tags = "Shion Joon Yorigami Spirit Possession Bloom Tonight Stars an Easygoing Egoistic Flowers 瑰狂鬱嵂 metal C94 ZUN woof"
    m.id = 1729489

    assert(m.song == "Koyoi wa Hyouitsu na Egoist ~ Ego, Schizoid, Beat.")
    assert(m.unicodeSong == "今宵は飄逸なエゴイスト ～ Ego,Schizoid,Beat.")
    assert(m.artist == "Demetori")
    assert(m.unicodeArtist == "Demetori")
    assert(m.creator == "GoldenWolf")
    assert(m.difficulty == "The Most Despicable and Disastrous God of Destitution and Misery.")
    assert(m.source == "東方憑依華　～ Antinomy of Common Flowers.")
    assert(m.tags == "Shion Joon Yorigami Spirit Possession Bloom Tonight Stars an Easygoing Egoistic Flowers 瑰狂鬱嵂 metal C94 ZUN woof")
    assert(m.id == 1729489)
  }
}
