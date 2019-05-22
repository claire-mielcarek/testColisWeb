package unitTests

import org.junit.Test
import org.junit.Assert._
import _root_.api._

class routeTests {
 
  @Test def length() {
    val r1 = new Route(new Point(1, 0, 0), new Point(2, 10, 10))
    val r2 = new Route(new Point(1,2,2), new Point(1,2,2))
    assertEquals("Longueur d'une route", scala.math.sqrt(200),r1.length, 0.001)
    assertEquals("Longueur d'une route composée de deux fois le même point", 0,r2.length,0.001)
  }
  
  @Test def startWith(){
    val route = new Route(new Point(1,0,0), new Point(2,20,20))
    assertTrue("startWith should return true", route.startWith(1))
    assertFalse("startWith should return false", route.startWith(10))
    
  }
}