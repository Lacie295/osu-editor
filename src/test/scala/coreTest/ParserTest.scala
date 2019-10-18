package coreTest

import components.{Circle, Slider, Spinner}
import core.Parser
import utils.Addition

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
  }

  it should "read a slider correctly" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val line = parser.readLines()(1)
    val slider = new Slider((496, 279),(381, 261), 450, 451, 0)

    val line2 = parser.readLines()(3)
    val slider2 = new Slider((179, 318), (170, 206), 30292, 30293)

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

    assert(parser.readAdditionBit("0") sameElements Array(new Addition(0, 0, false), new Addition(0, 0, false), new Addition(0, 0, false)))
    assert(parser.readAdditionBit("2") sameElements Array(new Addition(0, 0, true), new Addition(0, 0, false), new Addition(0, 0, false)))
    assert(parser.readAdditionBit("4") sameElements Array(new Addition(0, 0, false), new Addition(0, 0, true), new Addition(0, 0, false)))
    assert(parser.readAdditionBit("6") sameElements Array(new Addition(0, 0, true), new Addition(0, 0, true), new Addition(0, 0, false)))
    assert(parser.readAdditionBit("8") sameElements Array(new Addition(0, 0, false), new Addition(0, 0, false), new Addition(0, 0, true)))
    assert(parser.readAdditionBit("10") sameElements Array(new Addition(0, 0, true), new Addition(0, 0, false), new Addition(0, 0, true)))
    assert(parser.readAdditionBit("12") sameElements Array(new Addition(0, 0, false), new Addition(0, 0, true), new Addition(0, 0, true)))
    assert(parser.readAdditionBit("14") sameElements Array(new Addition(0, 0, true), new Addition(0, 0, true), new Addition(0, 0, true)))
  }

  it should "return the extras correctly" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")
    val ext = parser.readExtras("3:2:2:85:")

    assert(ext == (3,2,2,85,""))
    assert(parser.readExtras("3:2:2:85:file.wav") == (3,2,2,85,"file.wav"))
  }

  it should "return HS + AD (circles/sliders) correctly" in {
    val parser = new Parser(System.getProperty("user.dir") + "/src/resources/objectlines.osu")

    assert(parser.readActiveHitsound("3:2:2:85:", "0") == (Array(new Addition(0, 0, false), new Addition(0, 0, false), new Addition(0, 0, false)), (3,2,2,85,"")))
    assert(parser.readActiveHitsound("3:2:2:85:", "2") == (Array(new Addition(0, 0, true), new Addition(0, 0, false), new Addition(0, 0, false)),3,2,2,85,""))
    assert(parser.readActiveHitsound("3:2:2:85:", "4") == (Array(new Addition(0, 0, false), new Addition(0, 0, true), new Addition(0, 0, false)),3,2,2,85,""))
    assert(parser.readActiveHitsound("3:2:2:85:", "6") == (Array(new Addition(0, 0, true), new Addition(0, 0, true), new Addition(0, 0, false)),3,2,2,85,""))
    assert(parser.readActiveHitsound("3:2:2:85:", "8") == (Array(new Addition(0, 0, false), new Addition(0, 0, false), new Addition(0, 0, true)),3,2,2,85,""))
    assert(parser.readActiveHitsound("3:2:2:85:", "10") == (Array(new Addition(0, 0, true), new Addition(0, 0, false), new Addition(0, 0, true)),3,2,2,85,""))
    assert(parser.readActiveHitsound("3:2:2:85:", "12") == (Array(new Addition(0, 0, false), new Addition(0, 0, true), new Addition(0, 0, true)),3,2,2,85,""))
    assert(parser.readActiveHitsound("3:2:2:85:", "14") == (Array(new Addition(0, 0, true), new Addition(0, 0, true), new Addition(0, 0, true)),3,2,2,85,""))
  }
}
