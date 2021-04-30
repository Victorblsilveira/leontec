sealed trait Direction {
  def toLeft: Direction
  def toRight: Direction
}
case class Up() extends Direction {
  def toLeft: Direction = Left()
  def toRight: Direction = Right()
}

case class Down() extends Direction {
  def toLeft: Direction = Right()
  def toRight: Direction = Left()
}

case class Left() extends Direction {
  def toLeft: Direction = Down()
  def toRight: Direction = Up()
}

case class Right() extends Direction {
  def toLeft: Direction = Up()
  def toRight: Direction = Down()
}