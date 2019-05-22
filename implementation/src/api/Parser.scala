package api

import scala.io.Source

object Parser {
  val initCompt: Int = -1
  
  private[this] def isPositiveInt(str: String) = 
    scala.util.Try(str.toInt).isSuccess && (str.toInt >= 0)

  /**
   * Parse the first line of the file
   */
  def parseStart(line: List[String]): List[Int] =
    line match{
    case x::y::tail => 
      isPositiveInt(x) && isPositiveInt(y) match{
        case true => x.toInt::y.toInt::Nil
        case false => Nil
      }
    case _ => Nil
  }

  /**
   * Parse the line of the file which contains the stops
   */
  def parseStops(line: List[String]): List[Int] =
    line match {
      case Nil => Nil
      case _ => line.head match {
        case "#" => Nil
        case _ => (line.head.toInt) :: parseStops(line.tail)
      }
    }

  /**
   * Parse the line which presents the number of points and routes
   */
  def parsePointNumber(line: List[String]): Int =
    line.head.toInt

  /**
   * Parse a line corresponding to a point
   */
  def parsePoint(line: List[String]): Point =
    new Point(line.head.toInt, line.tail.head.toDouble, line.tail.tail.head.toDouble)

  
  /**
   * Parse a line corresponding to a route
   * @param line : the String corresponding to a rout
   * @param points : the list of the disponible points, previously created
   */
  def parseRoute(line: List[String], points: List[Point]): Route =
    List(
      points.find(p => p.isPointNumber(line.head.toInt)),
      points.find(p => p.isPointNumber(line.tail.head.toInt))) match {
        case Some(begin) :: Some(end) :: tail => new Route(begin, end)
        case _ => new Route(new Point(-1, 0, 0), new Point(-1, 0, 0))
      }

  /**
   * Instantiate the different elements of the problem
   * @param token : list of the line of the file to parse
   * @param step : a String representing which analyze to do
   * @param nbPoints : number of line corresponding to a point still to be parsed
   * @param points : list of the points (updated through recursive call), needed for the creation of routes
   */
  def parseAux(token: List[Any], step: String, nbPoints: Int, points: List[Point]): List[Any] =
    step match {
      case "start" =>
        val startAndEnd = parseStart((token.head.asInstanceOf[String]).split(" ").toList)
        startAndEnd match{
          case x::y::Nil =>x :: y :: parseAux(token.tail, "stops", nbPoints, points)
          case _ => Nil 
        }
        
      case "stops" => parseStops((token.head.asInstanceOf[String]).split(" ").toList) :: parseAux(token.tail, "pointsNumber", nbPoints, points)
      case "pointsNumber" =>
        val pointsNumber = parsePointNumber((token.head.asInstanceOf[String]).split(" ").toList) //Problème : je prend que le premier chiffre, et si nombre à plusieurs chiffres comme 14 ?
        parseAux(token.tail, "points", pointsNumber, points)
      case "points" =>
        nbPoints match {
          case 0 => points :: parseAux(token, "routes", initCompt, points)
          case n => parseAux(token.tail, "points", nbPoints - 1, points ::: List(parsePoint((token.head.asInstanceOf[String]).split(" ").toList)))
        }
      case "routes" =>
        token match {
          case Nil => List()
          case t :: Nil => List(parseRoute((token.head.asInstanceOf[String]).split(" ").toList, points)) :: parseAux(token.tail, "end", nbPoints, points)
          case t :: tail =>
            val ret: List[Any] = parseAux(token.tail, "routes", nbPoints, points)
            (parseRoute((token.head.asInstanceOf[String]).split(" ").toList, points) :: (ret.head.asInstanceOf[List[Route]])) :: ret.tail
        }
      case "end" => List()
    }

  /**
   * Parse a file 
   * @return a list of all the characteristics of the problem
   * Type of the return List(start:Int, end:Int, stops:List[Int], points:List[Points], routes:List[Route])
   */
  def parse(fileName: String): List[Any] =
    parseAux(Source.fromFile(fileName).getLines.toList, "start", initCompt, List())
}