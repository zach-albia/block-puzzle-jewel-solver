package solver

case class Heuristic(blocksOccupied: Int, closednessIndex: Int) extends Ordered[Heuristic] {
  override def compare(that: Heuristic): Int = {
    val comparison = this.blocksOccupied compare that.blocksOccupied
    if (comparison != 0)
      comparison
    else
      that.closednessIndex compare this.closednessIndex
  }
}
