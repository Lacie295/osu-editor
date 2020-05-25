import core.{MapExporter, Parser_legacy}

object Main {
  def main(args: Array[String]): Unit = {
    args.foreach(s => {
      val path = System.getProperty("user.dir") + "/src/resources/" + s
      val parser = new Parser_legacy(path)
      val map = parser.readMap()
      val exporter = MapExporter(map)
      exporter.writeToFile(path.dropRight(4) + ".omd")
      print(exporter.export)
    })
  }
}


