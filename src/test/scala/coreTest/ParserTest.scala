package coreTest

import components.{Circle, Slider, Spinner}
import core.Parser
import utils.{Addition, Hitsound}

class ParserTest extends BaseTest {
  "A parser" should "load up a given file and return its constructor correctly" in {
    var path = System.getProperty("user.dir") + "/src/resources/readertestfile.osu"
    val parser = new Parser(path)

    assert(parser.sourcePath == path)
    assert(parser.readLines().mkString("") == "Hello [World!]")

    path = System.getProperty("user.dir") + "/src/resources/readertestfile2.osu"
    val parser2 = new Parser(path)

    assert(parser2.sourcePath == path)
    assert(parser2.readLines().mkString(", ") == "Hello, [World!]")
  }

  it should "determine which object is contained in a line" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val lines = parser.readLines()

    assert(parser.readObject(lines(0)).isInstanceOf[Circle])    // is circle
    assert(parser.readObject(lines(1)).isInstanceOf[Slider])    // is slider
    assert(parser.readObject(lines(2)).isInstanceOf[Spinner])   // is spinner
  }
    // TODO
  it should "detect and handle a faulty line" in {

  }

  it should "read a circle correctly" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val line = parser.readLines()(0)
    val circle = new Circle((68, 50), 439)

    assert(parser.readObject(line) == circle)

    val line2 = parser.readLines()(4)
    val circle2 = new Circle((68, 50), 439, new Hitsound(1, 2))
    circle2.additions = Array(new Addition(2, 3, true), new Addition(2, 3, true), new Addition(2, 3, false))

    assert(parser.readObject(line2) == circle2)

    val l2 = parser.readObject(line2)
    assert(l2.additions(0) == circle2.additions(0))
    assert(l2.additions(1) == circle2.additions(1))
    assert(l2.additions(2) == circle2.additions(2))
  }

  it should "read a slider correctly" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
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
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val line = parser.readLines()(2)
    val spinner = new Spinner(730, 3983)

    assert(parser.readObject(line) == spinner)
  }

  it should "return an Addition correctly" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")

    val a1 = parser.readAdditionBit("0")
    assert(a1(0) == new Addition(0, 0, false))
    assert(a1(1) == new Addition(0, 0, false))
    assert(a1(2) == new Addition(0, 0, false))

    val a2 = parser.readAdditionBit("2")
    assert(a2(0) == new Addition(0, 0, true))
    assert(a2(1) == new Addition(0, 0, false))
    assert(a2(2) == new Addition(0, 0, false))

    val a3 = parser.readAdditionBit("4")
    assert(a3(0) == new Addition(0, 0, false))
    assert(a3(1) == new Addition(0, 0, true))
    assert(a3(2) == new Addition(0, 0, false))

    val a4 = parser.readAdditionBit("6")
    assert(a4(0) == new Addition(0, 0, true))
    assert(a4(1) == new Addition(0, 0, true))
    assert(a4(2) == new Addition(0, 0, false))

    val a5 = parser.readAdditionBit("8")
    assert(a5(0) == new Addition(0, 0, false))
    assert(a5(1) == new Addition(0, 0, false))
    assert(a5(2) == new Addition(0, 0, true))

    val a6 = parser.readAdditionBit("10")
    assert(a6(0) == new Addition(0, 0, true))
    assert(a6(1) == new Addition(0, 0, false))
    assert(a6(2) == new Addition(0, 0, true))

    val a7 = parser.readAdditionBit("12")
    assert(a7(0) == new Addition(0, 0, false))
    assert(a7(1) == new Addition(0, 0, true))
    assert(a7(2) == new Addition(0, 0, true))

    val a8 = parser.readAdditionBit("14")
    assert(a8(0) == new Addition(0, 0, true))
    assert(a8(1) == new Addition(0, 0, true))
    assert(a8(2) == new Addition(0, 0, true))
  }

  it should "return the extras correctly" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val ext = parser.readExtras("3:2:2:85:")

    assert(ext == (3,2,2,85,""))
    assert(parser.readExtras("3:2:2:85:file.wav") == (3,2,2,85,"file.wav"))
  }

  it should "return HS + AD (circles/spinners) correctly" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")

    val a1 = parser.readActiveHitsound("3:2:2:85:", "0")
    assert(a1._1 == new Hitsound(3, 2))
    assert(a1._2(0) == new Addition(2, 2, false))
    assert(a1._2(1) == new Addition(2, 2, false))
    assert(a1._2(2) == new Addition(2, 2, false))

    val a2 = parser.readActiveHitsound("3:2:2:85:", "2")
    assert(a2._1 == new Hitsound(3, 2))
    assert(a2._2(0) == new Addition(2, 2, true))
    assert(a2._2(1) == new Addition(2, 2, false))
    assert(a2._2(2) == new Addition(2, 2, false))

    val a3 = parser.readActiveHitsound("3:2:2:85:", "4")
    assert(a3._1 == new Hitsound(3, 2))
    assert(a3._2(0) == new Addition(2, 2, false))
    assert(a3._2(1) == new Addition(2, 2, true))
    assert(a3._2(2) == new Addition(2, 2, false))

    val a4 = parser.readActiveHitsound("3:2:2:85:", "6")
    assert(a4._1 == new Hitsound(3, 2))
    assert(a4._2(0) == new Addition(2, 2, true))
    assert(a4._2(1) == new Addition(2, 2, true))
    assert(a4._2(2) == new Addition(2, 2, false))

    val a5 = parser.readActiveHitsound("3:2:2:85:", "8")
    assert(a5._1 == new Hitsound(3, 2))
    assert(a5._2(0) == new Addition(2, 2, false))
    assert(a5._2(1) == new Addition(2, 2, false))
    assert(a5._2(2) == new Addition(2, 2, true))

    val a6 = parser.readActiveHitsound("3:2:2:85:", "10")
    assert(a6._1 == new Hitsound(3, 2))
    assert(a6._2(0) == new Addition(2, 2, true))
    assert(a6._2(1) == new Addition(2, 2, false))
    assert(a6._2(2) == new Addition(2, 2, true))

    val a7 = parser.readActiveHitsound("3:2:2:85:", "12")
    assert(a7._1 == new Hitsound(3, 2))
    assert(a7._2(0) == new Addition(2, 2, false))
    assert(a7._2(1) == new Addition(2, 2, true))
    assert(a7._2(2) == new Addition(2, 2, true))

    val a8 = parser.readActiveHitsound("3:2:2:85:", "14")
    assert(a8._1 == new Hitsound(3, 2))
    assert(a8._2(0) == new Addition(2, 2, true))
    assert(a8._2(1) == new Addition(2, 2, true))
    assert(a8._2(2) == new Addition(2, 2, true))
  }

  it should "read a timing point correctly" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")

    // TODO
    val tp1 = parser.readTimingPoint("")
  }
}
