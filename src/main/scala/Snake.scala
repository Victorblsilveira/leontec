import com.typesafe.scalalogging.Logger
import Snake._

case class Snake(startHead: SnakeHead, startBody: List[SnakePiece]) {
  private var eating: Boolean = false
  private var snakeHead: SnakeHead = startHead
  private var snakeBody: List[SnakePiece] = startBody

  def eat(): Unit = { eating = true  }
  def head(): SnakeHead = { snakeHead }
  def body(): List[SnakePiece] = { snakeBody }

  def move(moveOnX: MoveFunc, moveOnY: MoveFunc): Unit = {
    moveBody()
    moveHead(moveOnX, moveOnY)
  }

  def prettyPrint(logger: Logger): Unit = {
    logger.info(s"HEAD: [x: ${snakeHead._1}, y:${snakeHead._2}, direction: ${snakeHead._3.getClass}")
    logger.info(s"BODY: ${snakeBody.map(t => s"[x: ${t._1}, y: ${t._2}]").mkString(",")}")
  }

  def changeDirection(move: Move): Unit = {
    val (x, y, direction) = startHead
    move match {
      case _:LeftMove => snakeHead = (x, y, direction.toLeft)
      case _:RightMove => snakeHead = (x, y, direction.toRight)
    }
  }

  private def moveBody(): Unit = {
    val (x, y, _) = head()
    // If eating we grow up, so there is no need to drop the last index
    if (eating) {
      snakeBody = (x, y) :: snakeBody
    } else {
      snakeBody = (x, y) :: snakeBody.drop(1)
    }
    eating = false
  }

  private def moveHead(moveOnX: Int => Int, moveOnY: Int => Int): Unit = {
    val (x, y, direction) = head()
    direction match {
      case _:Up => snakeHead = (x,moveOnY(y+1), direction)
      case _:Down => snakeHead = (x,moveOnY(y-1), direction)
      case _:Left => snakeHead =  (moveOnX(x-1),y, direction)
      case _:Right => snakeHead = (moveOnX(x+1),y, direction)
    }
  }
}

object Snake {
  type SnakeHead = (Int, Int, Direction)
  type SnakePiece = (Int, Int)
  type MoveFunc = Int => Int
}