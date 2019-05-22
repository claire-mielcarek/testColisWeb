package api

object Api {
  val lengthMax = 10000

  private[this] def shortestPathAux(l: List[Path], bestPath: Path, bestLength: Double): Path =
    l match {
      case Nil => bestPath
      case x :: tail =>
        val length = x.length
        if (length < bestLength)
          shortestPathAux(tail, x, length)
        else
          shortestPathAux(tail, bestPath, bestLength)
    }

  def shortestPath(l: List[Path]): Path = shortestPathAux(l, new Path(Nil), lengthMax)

  def filterPaths(paths: List[Path], stops: List[Int]): List[Path] =
    paths.filter((p: Path) => { p.containsStops(stops) })

  def findBestPath(paths: List[Path], stops: List[Int]): Path =
    shortestPath(filterPaths(paths, stops))

  def getAllPaths(possibleRoutes: List[Route], begin: Int, end: Int): List[Path] =
    test(possibleRoutes, begin, end) map { list => new Path(list) }

  def getFinalPath(routes: List[Route], origin: Int, destination: Int, stops: List[Int]): Path =
    findBestPath(getAllPaths(routes, origin, destination), stops)

  def test(possibleRoutes: List[Route], begin: Int, end: Int): List[List[Route]] =
    begin match {
      case `end` => List(List())
      case x =>
        val nextRoutes = possibleRoutes.filter(r => r.startWith(x))
        nextRoutes flatMap {
          route => test(possibleRoutes diff List(route), route.end.id, end) map { l => route :: l }
        }
    }
}