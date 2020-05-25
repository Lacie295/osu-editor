import core.{MapExporter, Parser_legacy}

object Main {
  def main(args: Array[String]): Unit = {
    val path = System.getProperty("user.dir") + "/src/resources/" + args(0)
    val parser = new Parser_legacy(path)
    val map = parser.readMap()
    val exporter = MapExporter(map)
    exporter.writeToFile(path.dropRight(4) + ".omd")
    print(exporter.export)
  }
}


