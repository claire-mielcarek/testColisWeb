import api._

object test {
  def main(args: Array[String]) {

    val beginTime = System.currentTimeMillis()
    args.length match {
      case 0 => println("Name of the file missing (with extension)")
      case 1 => println("You have to indicate if you want to display time of execution")
      case _ =>
        val filename = args(0)
        val parsing = Parser.parse(filename)
        val routes = parsing.tail.tail.tail.tail.head.asInstanceOf[List[Route]]
        val origin = parsing.head.asInstanceOf[Int]
        val destination = parsing.tail.head.asInstanceOf[Int]
        val stops = parsing.tail.tail.head.asInstanceOf[List[Int]]
        println(Api.getFinalPath(routes, origin, destination, stops))
        val diffTime = System.currentTimeMillis() - beginTime
        args(1) match {
          case "true" => println("Time taken : " + diffTime + " ms")
          case _ =>
        }
    }
  }
}