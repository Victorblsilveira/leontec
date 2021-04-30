sealed trait Move{}
case class LeftMove() extends Move{}
case class RightMove() extends Move{}