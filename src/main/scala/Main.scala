import components.Spinner

object Main {
  def main(args: Array[String]): Unit = {
    val spin = new Spinner(1, 4)
    val spin2 = new Spinner(0, 5) // spin2 contains spin
    val spin3 = new Spinner(4, 5) // spin3 is on spin's end
    val spin4 = new Spinner(0, 1) // spin4 is on spin's start
    val spin5 = new Spinner(2, 3) // spin contains spin5
    val spin6 = new Spinner(16, 20)

    println(spin overlaps spin2)
    println(spin overlaps spin3)
    println(spin overlaps spin4)
    println(spin overlaps spin5)
    println(spin overlaps spin6)
  }




}


