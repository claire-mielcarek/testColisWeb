package api

class Route(val begin: Point, val end: Point) {

  /**
   * Compute the length of the route, that is the euclidean distance between begin and end
   */
  def length: Double =
    scala.math.sqrt(
      scala.math.pow((begin.lat - end.lat), 2) +
        scala.math.pow((begin.lon - end.lon), 2))

  /**
   * @return if the id of the begin point of the route is n
   */
  def startWith(n: Int): Boolean =
    this.begin.isPointNumber(n)

  override def toString = "(" + begin.toString + "," + end.toString + ")"
}