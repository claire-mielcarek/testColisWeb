package api

class Path(val routes: List[Route]) {
  def listLength(l: List[Route]): Double =
    l match {
      case Nil => 0
      case x :: tail => x.length + listLength(tail)
      case _ => 0
    }

  def length: Double = listLength(routes)

  def listToString(l: List[Route]): String =
    l match {
      case Nil => " "
      case x :: Nil => x.begin.toString + " " + x.end.toString
      case x :: tail => x.begin.toString + " " + listToString(tail)
    }
  override def toString = listToString(routes)

  private[this] def containsPointAux(routes: List[Route], p: Int): Boolean =
    routes match {
      case Nil => false
      case x :: tail =>
        x.begin.isPointNumber(p) ||
          x.end.isPointNumber(p) ||
          containsPointAux(tail, p)
    }

  def containsPoint(p:Int): Boolean = containsPointAux(routes, p)

  def containsStops(stops: List[Int]): Boolean =
    stops match {
      case Nil => true
      case head :: tail => containsPoint(head) && containsStops(tail)
    }
}