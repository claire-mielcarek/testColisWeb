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

  private[this] def createNewCombinations(head: List[Route], tail: List[Route], el: Route): List[List[Route]] =
    tail match {
      case Nil => List(head ::: List(el))
      case x :: next => (head ::: List(el) ::: tail) :: createNewCombinations(head ::: List((tail.head)), tail.tail, el)
    }

  def getAllPossibleRoutes(possibleRoutes: List[Route]): List[List[Route]] =
    possibleRoutes match {
      case Nil => List(Nil)
      case x :: Nil => List(Nil, List(x))
      case x :: tail =>
        val newList = getAllPossibleRoutes(tail)
        newList ::: (newList flatMap { l => createNewCombinations(Nil, l, x) })
    }
  
  def isValid(route:List[Route], begin:Int, end:Int):Boolean =
    route match{
    case Nil => (begin == end)
    case x::tail => (x.begin.isPointNumber(begin) && isValid(tail,x.end.id,end))
  }
  
  def getAllPaths(possibleRoutes: List[Route], begin:Int, end:Int):List[Path] =
    getAllPossibleRoutes(possibleRoutes).filter((route:List[Route]) => isValid(route,begin,end)) map {list => new Path(list)}
  
  def getFinalPath(routes:List[Route], origin:Int, destination:Int, stops:List[Int]):Path =
    findBestPath(getAllPaths(routes,origin,destination),stops)
}