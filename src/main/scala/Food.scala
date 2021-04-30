case class Food(x: Int, y: Int)
object Food {
  def apply(positions: (Int, Int)): Food = Food(positions._1, positions._2)
}
