package api

class Route(val begin: Point, val end: Point) {
  def length: Double =
    scala.math.sqrt(
      scala.math.pow((begin.lat - end.lat), 2) +
        scala.math.pow((begin.lon - end.lon), 2))

  def startWith(n: Int): Boolean =
    this.begin.isPointNumber(n)

  override def toString = "(" + begin.toString + "," + end.toString + ")"
}