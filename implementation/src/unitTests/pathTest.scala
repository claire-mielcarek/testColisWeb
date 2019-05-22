package unitTests

import org.junit.Test
import org.junit.Assert._
import _root_.api._

class pathTest {
  @Test def length() {
    val p0 = new Point(0, 0.5, 10.2)
    val p1 = new Point(1, 10.1, 10.5)
    val p2 = new Point(2, 10.5, 20.3)
    val r1 = new Route(p0, p1)
    val r2 = new Route(p1, p2)
    val path1 = new Path(List(r1, r2))
    val path2 = new Path(List())
    assertEquals("path length", 19.413, path1.length, 0.001)
    assertEquals("empty path length", 0, path2.length, 0.001)
  }

  @Test def containsPoint() {
    val p0 = new Point(0, 0.5, 10.2)
    val p1 = new Point(1, 10.1, 10.5)
    val p2 = new Point(2, 10.5, 20.3)
    val r1 = new Route(p0, p1)
    val r2 = new Route(p1, p2)
    val path1 = new Path(List(r1, r2))
    val path2 = new Path(List())
    assertTrue("path contains this point", path1.containsPoint(1))
    assertFalse("path does not contains this point", path1.containsPoint(4))
    assertFalse("empty path does not contains anything", path2.containsPoint(0))
  }

  @Test def containsStops() {
    val p0 = new Point(0, 0.5, 10.2)
    val p1 = new Point(1, 10.1, 10.5)
    val p2 = new Point(2, 10.5, 20.3)
    val r1 = new Route(p0, p1)
    val r2 = new Route(p1, p2)
    val path1 = new Path(List(r1, r2))
    val path2 = new Path(List())
    assertTrue("path contains all these points", path1.containsStops(List(1, 2)))
    assertTrue("path always contains empty stops", path1.containsStops(List()))
    assertFalse("path contains not all the points", path1.containsStops(List(2, 3)))
    assertFalse("path contains none of the points", path1.containsStops(List(5, 6, 7)))
    assertFalse("empty path contains nothing", path2.containsStops(List(1,2)))

  }
}