package unitTests

import org.junit.Test
import org.junit.Assert._
import _root_.api._

class apiTest {

  @Test def shortestPath() {
    val p0 = new Point(0, 0.5, 10.2)
    val p1 = new Point(1, 10.1, 10.5)
    val p2 = new Point(2, 10.5, 20.3)
    val r1 = new Route(p0, p1)
    val r2 = new Route(p1, p2)
    val path1 = new Path(List(r1, r2))
    val path2 = new Path(List())
    assertEquals("le chemin le plus court", Api.shortestPath(List(path1, path2)), path2)
    assertEquals("Chemin le plus court dans une liste vide", Api.shortestPath(List()), new Path(Nil))
  }

  @Test def filterPaths() {
    val p0 = new Point(0, 0.5, 10.2)
    val p1 = new Point(1, 10.1, 10.5)
    val p2 = new Point(2, 10.5, 20.3)
    val r1 = new Route(p0, p1)
    val r2 = new Route(p1, p2)
    val path1 = new Path(List(r1, r2))
    val path2 = new Path(List(r1))
    assertEquals("Filtrer", Api.filterPaths(List(path1, path2), List(2)), List(path1))
    assertEquals("Filtrer with no stops", Api.filterPaths(List(path1, path2), List()), List(path1, path2))
    assertEquals("Filtrer with no paths", Api.filterPaths(List(), List(2)), List())
  }

  @Test def finBestPath() {
    val r1 = new Route(new Point(1, 1, 1), new Point(2, 2, 2))
    val r2 = new Route(new Point(2, 2, 2), new Point(3, 3, 3))
    val r3 = new Route(new Point(3, 3, 3), new Point(4, 4, 4))
    val r4 = new Route(new Point(2, 2, 2), new Point(5, 45, 45))
    val r5 = new Route(new Point(3, 45, 45), new Point(4, 4, 4))
    val p1 = new Path(List(r1))
    val p2 = new Path(List(r1, r4, r5))
    val p3 = new Path(List(r1, r4))
    val p4 = new Path(List(r1, r2, r3))
    assertEquals("find best path", Api.findBestPath(p1 :: p2 :: p1 :: Nil, 1 :: Nil), p1)
    assertEquals("find best path : no path found", Api.findBestPath(p1 :: p2 :: p1 :: Nil, 4 :: 5 :: 6 :: Nil), new Path(List()))
    assertEquals("find best path : 1 solutions", Api.findBestPath(p1 :: p2 :: p3 :: Nil, 5 :: Nil), p3)
    assertEquals("find best path : no stops", Api.findBestPath(p1 :: p2 :: p3 :: p4 :: Nil, Nil), p1)
    assertEquals("find best path : no paths", Api.findBestPath(Nil, List(1, 2, 3)), new Path(Nil))
  }

  @Test def combinations() {
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
    val r27 = new Route(p23, p21)
    assertEquals("All paths possible", Api.computeAllRoutesCombinations(List(r20, r21, r22, r23, r24, r25, r26), start2, end2), List(List(r20, r21, r26), List(r23, r26)))
    assertEquals("All paths possible, with routes making a cycle", Api.computeAllRoutesCombinations(List(r20, r21, r22, r23, r25, r26, r27), start2, end2), List(List(r20, r21, r26), List(r20, r22, r27, r21, r26), List(r23, r26)))
    assertEquals("All paths possible, with a begin not in routes", Api.computeAllRoutesCombinations(List(r20, r21, r22, r23, r24, r25, r26, r27), 10, end2), List())
    assertEquals("All paths possible, with a destination not in routes", Api.computeAllRoutesCombinations(List(r20, r21, r22, r23, r24, r25, r26, r27), start2, 10), List())
    assertEquals("All paths possible, with no routes", Api.computeAllRoutesCombinations(List(), start2, 10), List())
  }

  @Test def allPaths() {
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
    val r27 = new Route(p23, p21)
    assertEquals("All paths", Api.getAllPaths(List(r20, r21, r22, r23, r24, r25, r26), start2, end2), List(new Path(List(r20, r21, r26)), new Path(List(r23, r26))))
    assertEquals("All path possible, with routes making a cycle", Api.getAllPaths(List(r20, r21, r22, r23, r25, r26, r27), start2, end2), List(new Path(List(r20, r21, r26)), new Path(List(r20, r22, r27, r21, r26)), new Path(List(r23, r26))))
    assertEquals("All paths, with a begin not in routes", Api.getAllPaths(List(r20, r21, r22, r23, r24, r25, r26, r27), 10, end2), List())
    assertEquals("All paths, with a destination not in routes", Api.getAllPaths(List(r20, r21, r22, r23, r24, r25, r26, r27), start2, 10), List())
    assertEquals("All paths, with no routes", Api.getAllPaths(List(), start2, 10), List())

  }

  @Test def finalPath() {
    val start1 = 0
    val end1 = 2
    val stops1 = List(1, 2)
    val p10 = new Point(0, 0.5, 10.2)
    val p11 = new Point(1, 10.1, 10.5)
    val p12 = new Point(2, 10.5, 20.3)
    val r10 = new Route(p10, p11)
    val r11 = new Route(p11, p12)
    val r12 = new Route(p12, p10)
    assertEquals("final path", Api.getFinalPath(List(r10, r11, r12), start1, end1, stops1), new Path(List(r10, r11)))
    assertEquals("final path without stops", Api.getFinalPath(List(r10, r11, r12), start1, end1, Nil), new Path(List(r10, r11)))
  }
}