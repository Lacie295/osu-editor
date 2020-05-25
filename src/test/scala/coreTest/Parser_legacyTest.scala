package coreTest

import components.{Circle, HitObject, Inherited_legacy, Slider, Spinner, Uninherited_legacy}
import core.Parser_legacy
import utils.Hitsound

class Parser_legacyTest extends BaseTest {
  "A parser" should "load up a given file and return its constructor correctly" in {
    var path = System.getProperty("user.dir") + "/src/resources/readertestfile.osu"
    val parser = new Parser_legacy(path)

    assert(parser.sourcePath == path)
    assert(parser.readLines().mkString("") == "Hello [World!]")

    path = System.getProperty("user.dir") + "/src/resources/readertestfile2.osu"
    val parser2 = new Parser_legacy(path)

    assert(parser2.sourcePath == path)
    assert(parser2.readLines().mkString(", ") == "Hello, [World!]")
  }

  it should "determine which object is contained in a line" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val lines = parser.readLines()

    assert(parser.readObject(lines(0)).isInstanceOf[Circle]) // is circle
    assert(parser.readObject(lines(1)).isInstanceOf[Slider]) // is slider
    assert(parser.readObject(lines(2)).isInstanceOf[Spinner]) // is spinner
  }
  // TODO
  it should "detect and handle a faulty line" in {

  }

  it should "read a circle correctly" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val line = parser.readLines()(0)
    val circle = new Circle((68, 50), 439)

    assert(parser.readObject(line) == circle)

    val line2 = parser.readLines()(4)
    val circle2 = new Circle((68, 50), 439, new Hitsound(1, 2))
    circle2.additionsHitsound = new Hitsound(2, 3)
    circle2.additions = Array(true, true, false)

    assert(parser.readObject(line2) == circle2)

    val l2 = parser.readObject(line2)
    assert(l2.additions(0) == circle2.additions(0))
    assert(l2.additions(1) == circle2.additions(1))
    assert(l2.additions(2) == circle2.additions(2))
  }

  it should "read a slider correctly" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val line = parser.readLines()(1)
    val slider = new Slider((496, 279), 450, 451, 0)

    val line2 = parser.readLines()(3)
    val slider2 = new Slider((179, 318), 30292, 30293)

    slider.addNode((426, 266), 0)
    slider.addNode((381, 261), 0)

    slider2.addNode((188, 263), 1)
    slider2.addNode((170, 263), 0)

    assert(parser.readObject(line) == slider)
    assert(parser.readObject(line2) == slider2)
  }

  it should "read a spinner correctly" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val line = parser.readLines()(2)
    val spinner = new Spinner(730, 3983)

    assert(parser.readObject(line) == spinner)
  }

  it should "return an Addition correctly" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/objectlines.osu")

    val a1 = parser.readAdditionBit("0")
    assert(!a1(0))
    assert(!a1(1))
    assert(!a1(2))

    val a2 = parser.readAdditionBit("2")
    assert(a2(0))
    assert(!a2(1))
    assert(!a2(2))

    val a3 = parser.readAdditionBit("4")
    assert(!a3(0))
    assert(a3(1))
    assert(!a3(2))

    val a4 = parser.readAdditionBit("6")
    assert(a4(0))
    assert(a4(1))
    assert(!a4(2))

    val a5 = parser.readAdditionBit("8")
    assert(!a5(0))
    assert(!a5(1))
    assert(a5(2))

    val a6 = parser.readAdditionBit("10")
    assert(a6(0))
    assert(!a6(1))
    assert(a6(2))

    val a7 = parser.readAdditionBit("12")
    assert(!a7(0))
    assert(a7(1))
    assert(a7(2))

    val a8 = parser.readAdditionBit("14")
    assert(a8(0))
    assert(a8(1))
    assert(a8(2))
  }

  it should "return the extras correctly" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val ext = parser.readExtras("3:2:2:85:")

    assert(ext == (3, 2, 2, 85, ""))
    assert(parser.readExtras("3:2:2:85:file.wav") == (3, 2, 2, 85, "file.wav"))
  }

  it should "return HS + AD (circles/spinners) correctly" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/objectlines.osu")

    val a1 = parser.readActiveHitsound("3:2:2:85:", "0")
    assert(a1._1 == new Hitsound(3, 2))
    assert(a1._2 == new Hitsound(2, 2))
    assert(!a1._3(0))
    assert(!a1._3(1))
    assert(!a1._3(2))

    val a2 = parser.readActiveHitsound("3:2:2:85:", "2")
    assert(a2._1 == new Hitsound(3, 2))
    assert(a2._2 == new Hitsound(2, 2))
    assert(a2._3(0))
    assert(!a2._3(1))
    assert(!a2._3(2))

    val a3 = parser.readActiveHitsound("3:2:2:85:", "4")
    assert(a3._1 == new Hitsound(3, 2))
    assert(a3._2 == new Hitsound(2, 2))
    assert(!a3._3(0))
    assert(a3._3(1))
    assert(!a3._3(2))

    val a4 = parser.readActiveHitsound("3:2:2:85:", "6")
    assert(a4._1 == new Hitsound(3, 2))
    assert(a4._2 == new Hitsound(2, 2))
    assert(a4._3(0))
    assert(a4._3(1))
    assert(!a4._3(2))

    val a5 = parser.readActiveHitsound("3:2:2:85:", "8")
    assert(a5._1 == new Hitsound(3, 2))
    assert(a5._2 == new Hitsound(2, 2))
    assert(!a5._3(0))
    assert(!a5._3(1))
    assert(a5._3(2))

    val a6 = parser.readActiveHitsound("3:2:2:85:", "10")
    assert(a6._1 == new Hitsound(3, 2))
    assert(a6._2 == new Hitsound(2, 2))
    assert(a6._3(0))
    assert(!a6._3(1))
    assert(a6._3(2))

    val a7 = parser.readActiveHitsound("3:2:2:85:", "12")
    assert(a7._1 == new Hitsound(3, 2))
    assert(a7._2 == new Hitsound(2, 2))
    assert(!a7._3(0))
    assert(a7._3(1))
    assert(a7._3(2))

    val a8 = parser.readActiveHitsound("3:2:2:85:", "14")
    assert(a8._1 == new Hitsound(3, 2))
    assert(a8._2 == new Hitsound(2, 2))
    assert(a8._3(0))
    assert(a8._3(1))
    assert(a8._3(2))
  }

  it should "read a timing point correctly" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/objectlines.osu")

    // TODO
    val tp1 = parser.readTimingPoint("450,315.789473684211,4,2,0,100,1,0")
    if (tp1.isInstanceOf[Uninherited_legacy]) {
      assert(tp1.asInstanceOf[Uninherited_legacy].bpm == 190)
      assert(tp1.asInstanceOf[Uninherited_legacy].meter == 4)
      assert(tp1.asInstanceOf[Uninherited_legacy].time == 450)
      assert(tp1.asInstanceOf[Uninherited_legacy].sampleSet == 2)
      assert(tp1.asInstanceOf[Uninherited_legacy].sampleIndex == 0)
      assert(tp1.asInstanceOf[Uninherited_legacy].volume == 100)
      assert(tp1.asInstanceOf[Uninherited_legacy].kiai == false)
    }

    val tp2 = parser.readTimingPoint("734,285.714285714285,6,1,4,46,1,1")
    if (tp2.isInstanceOf[Uninherited_legacy]) {
      assert(tp2.asInstanceOf[Uninherited_legacy].bpm == 210)
      assert(tp2.asInstanceOf[Uninherited_legacy].meter == 6)
      assert(tp2.asInstanceOf[Uninherited_legacy].time == 734)
      assert(tp2.asInstanceOf[Uninherited_legacy].sampleSet == 1)
      assert(tp2.asInstanceOf[Uninherited_legacy].sampleIndex == 4)
      assert(tp2.asInstanceOf[Uninherited_legacy].volume == 46)
      assert(tp2.asInstanceOf[Uninherited_legacy].kiai == true)
    }

    val tp3 = parser.readTimingPoint("4239,-52.6315789473684,4,2,1,100,0,0")
    if (tp3.isInstanceOf[Inherited_legacy]) {
      assert(tp3.asInstanceOf[Inherited_legacy].time == 4239)
      assert(tp3.asInstanceOf[Inherited_legacy].svMultiplier == 1.9)
      assert(tp3.asInstanceOf[Inherited_legacy].sampleSet == 2)
      assert(tp3.asInstanceOf[Inherited_legacy].sampleIndex == 1)
      assert(tp3.asInstanceOf[Inherited_legacy].volume == 100)
      assert(tp3.asInstanceOf[Inherited_legacy].kiai == false)
    }

    val tp4 = parser.readTimingPoint("4279,-83.3333333333333,4,1,5,22,0,1")
    if (tp4.isInstanceOf[Inherited_legacy]) {
      assert(tp4.asInstanceOf[Inherited_legacy].time == 4279)
      assert(tp4.asInstanceOf[Inherited_legacy].svMultiplier == 1.2)
      assert(tp4.asInstanceOf[Inherited_legacy].sampleSet == 1)
      assert(tp4.asInstanceOf[Inherited_legacy].sampleIndex == 5)
      assert(tp4.asInstanceOf[Inherited_legacy].volume == 22)
      assert(tp4.asInstanceOf[Inherited_legacy].kiai == true)
    }
  }

  it should "read slider hitsounds correctly" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/objectlines.osu")

    val slider = parser.readSlider("81,227,84765,2,0,P|80:193|91:162,1,66.4999982242585,10|0,0:0|0:0,0:0:0:0:")
    assert(slider.hitsound == new Hitsound(0, 0))
    assert(slider.additionsHitsound == new Hitsound(0, 0))
    assert(slider.additions(0))
    assert(!slider.additions(1))
    assert(slider.additions(2))
    assert(slider.repeatHitsounds(0) == new Hitsound(0, 0))
    assert(slider.repeatAdditionsHitsounds(0) == new Hitsound(0, 0))
    assert(!slider.repeatAdditions(0)(0))
    assert(!slider.repeatAdditions(0)(1))
    assert(!slider.repeatAdditions(0)(2))

    val slider2 = parser.readSlider("333,154,5426,6,0,L|231:124,2,105,4|10|12,0:0|0:0|0:0,0:0:0:0:")
    assert(slider2.hitsound == new Hitsound(0, 0))
    assert(slider2.additionsHitsound == new Hitsound(0, 0))
    assert(!slider2.additions(0))
    assert(slider2.additions(1))
    assert(!slider2.additions(2))
    assert(slider2.repeatHitsounds(0) == new Hitsound(0, 0))
    assert(slider2.repeatAdditionsHitsounds(0) == new Hitsound(0, 0))
    assert(slider2.repeatAdditions(0)(0))
    assert(!slider2.repeatAdditions(0)(1))
    assert(slider2.repeatAdditions(0)(2))
    assert(slider2.repeatHitsounds(1) == new Hitsound(0, 0))
    assert(slider2.repeatAdditionsHitsounds(1) == new Hitsound(0, 0))
    assert(!slider2.repeatAdditions(1)(0))
    assert(slider2.repeatAdditions(1)(1))
    assert(slider2.repeatAdditions(1)(2))
  }

  it should "apply a greenline to notes correctly" in {
    val parser = new Parser_legacy(System.getProperty("user.dir") + "/src/resources/greenlinetestfile.osu")

    val map = parser.readMap()
    val obj = map.getObject(4335)
    assert(obj.nonEmpty)
    assert(obj.get.isInstanceOf[Slider])
    val slider: Slider = obj.get.asInstanceOf[Slider]

    val slider1 = new Slider((173, 175), 4335, 4336, 1.4, 0, new Hitsound(1, 1))
    assert(slider1.additions(0))
    assert(!slider1.additions(1))
    assert(slider1.additions(2))
    assert(slider1.repeatHitsounds(0) == new Hitsound(1, 1))
    assert(slider1.repeatAdditionsHitsounds(0) == new Hitsound(1, 1))
    assert(!slider1.repeatAdditions(0)(0))
    assert(!slider1.repeatAdditions(0)(1))
    assert(!slider1.repeatAdditions(0)(2))

    assert(slider == slider1)
    assert(slider.hitsound == slider1.hitsound)
    assert(slider.additions(0))
    assert(!slider.additions(1))
    assert(slider.additions(2))
    assert(slider.repeatHitsounds(0) == slider1.repeatHitsounds(0))
    assert(slider.repeatAdditionsHitsounds(0) == slider1.repeatAdditionsHitsounds(0))
    assert(slider.repeatAdditions(0)(0) == slider1.repeatAdditions(0)(0))
    assert(slider.repeatAdditions(0)(1) == slider1.repeatAdditions(0)(1))
    assert(slider.repeatAdditions(0)(2) == slider1.repeatAdditions(0)(2))

  }
}
