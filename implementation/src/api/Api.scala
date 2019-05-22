package api

object Api {
  val lengthMax = 10000

  /**
   * Compute the shortest path of the list l, considering that bestPath is the current shortest and bestLength its length
   * @param l : list of Path to evaluate
   * @param bestPath : shortest Path found so far
   * @param bestLength : length of bestPath
   */
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

  /**
   * Compute the shortest path of the list l
   */
  def shortestPath(l: List[Path]): Path = shortestPathAux(l, new Path(Nil), lengthMax)

  /**
   * @return Path which contains all the stops
   * @param paths : paths to filter
   * @param stops : list of point ids that selected paths must go through
   */
  def filterPaths(paths: List[Path], stops: List[Int]): List[Path] =
    paths.filter((p: Path) => { p.containsStops(stops) })

  /**
   * @return the best Path, that is the shortest that go through given points
   * @param paths : paths to analyze
   * @param stops :  list of point ids that selected paths must go through
   */
  def findBestPath(paths: List[Path], stops: List[Int]): Path =
    shortestPath(filterPaths(paths, stops))

  /**
   * @return all the routes combinations that go from begin to end
   * @param possibleRoutes : list of routes that are available
   * @param begin : id of the origin
   * @param end : id of the destination 
   */
  def computeAllRoutesCombinations(possibleRoutes: List[Route], begin: Int, end: Int): List[List[Route]] =
    begin match {
      case `end` => List(List())
      case x =>
        val nextRoutes = possibleRoutes.filter(r => r.startWith(x))
        nextRoutes flatMap {
          route => computeAllRoutesCombinations(possibleRoutes diff List(route), route.end.id, end) map { l => route :: l }
        }
    }

  /**
   * @return all paths that go from point number begin to point number end
   * @param possibleRoutes : list of the disponible routes
   * @param begin : id of the origin of the paths to find
   * @param end : id of the destination of the paths to find
   */
  def getAllPaths(possibleRoutes: List[Route], begin: Int, end: Int): List[Path] =
    computeAllRoutesCombinations(possibleRoutes, begin, end) map { list => new Path(list) }

  /**
   * @return the shortest Path going to destination from origin through stops
   * @param routes : list of the disponible routes
   * @param origin : id of the origin of the path to return
   * @param destination : id of the destination of the path to return
   */
  def getFinalPath(routes: List[Route], origin: Int, destination: Int, stops: List[Int]): Path =
    findBestPath(getAllPaths(routes, origin, destination), stops)

}