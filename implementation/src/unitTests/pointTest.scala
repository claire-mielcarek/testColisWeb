package unitTests

import org.junit.Test
import org.junit.Assert._
import _root_.api._

class pointTest {
  
  @Test def equals(){
    val p1 = new Point(1,0,0)
    val p2 = new Point(3, 2.3, 59.8)
    val p3 = new Point(1,0,0)
    assertTrue("Point is equal to itself", p1.equals(p1))
    assertFalse("Point isn't equal to another different", p1.equals(p2))
    assertTrue("Point is equal to another with fields of same value", p1.equals(p3))
  }
  
  @Test def number(){
    val p2 = new Point(3, 2.3, 59.8)
    assertTrue("isPointNumber with good number", p2.isPointNumber(3))
    assertFalse("isPointNumber with wrong number", p2.isPointNumber(1))
  }
}