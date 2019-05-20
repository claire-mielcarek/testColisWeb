import api._

object test {
  def main(args: Array[String]) {
    /*val r1 = new Route(new Point(1, 1, 1), new Point(2, 2, 2))
    val r2 = new Route(new Point(2, 2, 2), new Point(3, 3, 3))
    val r3 = new Route(new Point(3, 3, 3), new Point(4, 4, 4))
    val r4 = new Route(new Point(2, 2, 2), new Point(5, 45, 45))
    val r5 = new Route(new Point(3, 45, 45), new Point(4, 4, 4))
    val p1 = new Path(List(r1))
    val p2 = new Path(List(r1, r4, r5))
    val p3 = new Path(List(r1, r4))
    val p4 = new Path(List(r1, r2, r3))
    println(r1.length)
    println(p4.length)
    println(new Path(List()).length)
    println(Api.shortestPath(p1 :: p2 :: Nil))
    println(Api.filterPaths(p1 :: p2 :: p1 :: Nil, 4 :: Nil))
    println(p1.containsPoint(1))
    println(p1.containsStops(1 :: Nil))
    println(Api.findBestPath(p1 :: p2 :: p1 :: Nil, 1 :: Nil))
    println(Api.findBestPath(p1 :: p2 :: p3 :: Nil, 3 :: Nil))
    val l1 = List(r1, r2, r3)
    println(Api.getAllPossibleRoutes(l1))
    println(Api.isValid(List(r1, r4), 1, 5))
    println("")
    println("Input 1")
    val start1 = 0
    val end1 = 2
    val stops1 = List(1, 2)
    val p10 = new Point(0, 0.5, 10.2)
    val p11 = new Point(1, 10.1, 10.5)
    val p12 = new Point(2, 10.5, 20.3)
    val r10 = new Route(p10, p11)
    val r11 = new Route(p11, p12)
    val r12 = new Route(p12, p10)
    println("Possible Paths : " + Api.getAllPaths(List(r10, r11, r12), start1, end1))
    println("Best Path : " + Api.getFinalPath(List(r10, r11, r12), start1, end1, stops1))

    println("")
    println("Input 2")
    val start2 = 0
    val end2 = 5
    val stops2 = List()
    val p20 = new Point(0, 50.63, 3.07)
    val p21 = new Point(1, 48.86, 2.35)
    val p22 = new Point(2, 44.84, -0.58)
    val p23 = new Point(3, 45.76, 4.84)
    val p24 = new Point(4, 43.3, 5.37)
    val p25 = new Point(5, 43.6, 1.44)
    val r20 = new Route(p20, p21)
    val r21 = new Route(p21, p22)
    val r22 = new Route(p21, p23)
    val r23 = new Route(p20, p22)
    val r24 = new Route(p23, p24)
    val r25 = new Route(p24, p23)
    val r26 = new Route(p22, p25)
    println("Possible Paths : " + Api.getAllPaths(List(r20, r21, r22, r23, r24, r25, r26), start2, end2))
    println("Best Path :" + Api.getFinalPath(List(r20, r21, r22, r23, r24, r25, r26), start2, end2, stops2))
    */

    val beginTime = System.currentTimeMillis()
    args.length match {
      case 0 => println("Name of the file missing (with extension)")
      case _ =>
        val filename = args(0)
        val parsing = Parser.parse(filename)
        println(Api.getFinalPath(
          parsing.tail.tail.tail.tail.head.asInstanceOf[List[Route]],
          parsing.head.asInstanceOf[Int],
          parsing.tail.head.asInstanceOf[Int],
          parsing.tail.tail.head.asInstanceOf[List[Int]]))
    }
    val diffTime =System.currentTimeMillis() - beginTime
    println("Time taken : " + diffTime + " ms")
  }
}