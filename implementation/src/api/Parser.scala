package api

import scala.io.Source

object Parser {
  val initCompt: Int = -1

  def parseStart(line: List[String]): List[Int] =
    (line.head.toInt) :: (line.tail.head.toInt) :: Nil

  def parseStops(line: List[String]): List[Int] =
    line match {
      case Nil => Nil
      case _ => line.head match{
        case "#" => Nil
        case _ => (line.head.toInt)::parseStops(line.tail)
      }
    }
  
  def parsePointNumber(line: List[String]): Int =
    line.head.toInt

  def parsePoint(line: List[String]): Point =
    new Point(line.head.toInt, line.tail.head.toDouble, line.tail.tail.head.toDouble)

  def parseRoute(line: List[String], points: List[Point]): Route =
    List(points.find(p => p.isPointNumber(line.head.toInt)), 
        points.find(p => p.isPointNumber(line.tail.head.toInt))) match{
    case None:: tail => new Route(new Point(-1,0,0),new Point(-1,0,0))
    case begin::None::tail => new Route(new Point(-1,0,0),new Point(-1,0,0))
    case Some(begin)::Some(end)::tail => new Route(begin,end)
  }

  def parseAux(token: List[Any], step: String, nbPoints: Int, points: List[Point]): List[Any] =
    step match {
      case "start" =>
        val startAndEnd = parseStart((token.head.asInstanceOf[String]).split(" ").toList)
        startAndEnd.head :: startAndEnd.tail.head :: parseAux(token.tail, "stops", nbPoints, points)
      case "stops" => parseStops((token.head.asInstanceOf[String]).split(" ").toList) :: parseAux(token.tail, "pointsNumber", nbPoints, points)
      case "pointsNumber" =>
        val pointsNumber = parsePointNumber((token.head.asInstanceOf[String]).split(" ").toList) //Problème : je prend que le premier chiffre, et si nombre à plusieurs chiffres comme 14 ?
        parseAux(token.tail, "points", pointsNumber, points)
      case "points" =>
        nbPoints match {
          case 0 => points :: parseAux(token, "routes", initCompt, points)
          case n => parseAux(token.tail, "points", nbPoints - 1, points:::List(parsePoint((token.head.asInstanceOf[String]).split(" ").toList)))
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

  def parse(fileName: String): List[Any] =
    parseAux(Source.fromFile(fileName).getLines.toList, "start", initCompt, List())
}