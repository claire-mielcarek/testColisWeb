package api;


class Point(val id:Int, val lat:Double, val lon:Double){
  
  override def toString = id.toString
  
  /**
   * Test the equality with another point
   * Equality is when all the attributes are equals
   */
  override def equals(that: Any): Boolean =
        that match {
            case that: Point => that.id ==this.id && that.lat ==this.lat && that.lon ==this.lon
            case _ => false
     }
  
  /**
   * @return if the id of the point is nb
   */
  def isPointNumber(nb:Int): Boolean =
    nb == this.id
}
