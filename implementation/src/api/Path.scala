package api

class Path(val routes: List[Route]) {

  override def equals(that: Any): Boolean =
    that match {
      case that: Path => that.routes == this.routes
      case _ => false
    }

  /**
   * @return Return the cumulative length of a list of route
   * @param l : the list of route to use
   */
  private[this] def listLength(l: List[Route]): Double =
    l match {
      case Nil => 0
      case x :: tail => x.length + listLength(tail)
      case _ => 0
    }

  /**
   * @return the length of the path
   */
  def length: Double = listLength(routes)

  /**
   * @return a string representing a list of route
   */
  private[this] def listToString(l: List[Route]): String =
    l match {
      case Nil => " "
      case x :: Nil => x.begin.toString + " " + x.end.toString
      case x :: tail => x.begin.toString + " " + listToString(tail)
    }
  override def toString = listToString(routes)

  /**
   * @return if a list of route pass through a point
   * @param routes : list of routes to consider
   * @param p : id of the point
   */
  private[this] def containsPointAux(routes: List[Route], p: Int): Boolean =
    routes match {
      case Nil => false
      case x :: tail =>
        x.begin.isPointNumber(p) ||
          x.end.isPointNumber(p) ||
          containsPointAux(tail, p)
    }

  /**
   * @return if the path goes through point of id p
   * @param p : id of the point
   */
  def containsPoint(p: Int): Boolean = containsPointAux(routes, p)

  /**
   * @return if the path goes through a list of point
   * @param stops : list of the ids of the points
   */
  def containsStops(stops: List[Int]): Boolean =
    stops match {
      case Nil => true
      case head :: tail => containsPoint(head) && containsStops(tail)
    }
}