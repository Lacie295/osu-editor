package coreTest

import components.{Circle, HitObject, Slider, Spinner, TimingPoint}
import core.Map

class MapTest extends BaseTest {
  "A map" should "store all its objects" in {
    val m: Map = new Map()

    m.addObject(new Slider((0, 0), (1, 1), 0, 1))
    m.addObject(new Circle((0, 0), 2))
    m.addObject(new Spinner(3, 4))

    val s: Slider = m.getObject(0)(0).asInstanceOf[Slider]
    val s2: Slider = m.getObject(1)(0).asInstanceOf[Slider]
    assert(s == s2)
    assert(s.x == 0)
    assert(s.y == 0)
    assert(s.endX == 1)
    assert(s.endY == 1)
    assert(s.time == 0)
    assert(s.endTime == 1)

    val c: Circle = m.getObject(2)(0).asInstanceOf[Circle]
    assert(c.x == 0)
    assert(c.y == 0)
    assert(c.time == 2)

    val sp: Spinner = m.getObject(3)(0).asInstanceOf[Spinner]
    val sp2: Spinner = m.getObject(4)(0).asInstanceOf[Spinner]
    assert(sp == sp2)
    assert(sp.time == 3)
    assert(sp.endTime == 4)

    m.deleteObject(sp)
    val none: List[HitObject] = m.getObject(3)
    assert(none.isEmpty)
  }

  it should "properly store timing points" in {
    val m: Map = new Map()

    m.addTimingPoint(new TimingPoint(0, 120))
    m.addTimingPoint(new TimingPoint(4, 130))

    assert(m.getTimingPoint(0)(0).time == 0)
    assert(m.getTimingPoint(0)(0).asInstanceOf[TimingPoint].bpm == 120)
    assert(m.getTimingPoint(6)(0).time == 4)
    assert(m.getTimingPoint(6)(0).asInstanceOf[TimingPoint].bpm == 130)
  }
}
