import com.typesafe.scalalogging.Logger

import scala.annotation.tailrec
import scala.util.Random

case class SnakeGame(width: Int, height: Int) {
  val logger: Logger = Logger("Default Logger")

  def startGame(): Unit = {
    logger.info("Starting the game")
    val snake = startSnake()
    val food = nextFood(snake, None)
    gameLoop(snake, food)
  }

  private def startSnake(): Snake = {
    val head = (width/2, height/2, Right())
    val body = (width/2 -1, height/2) :: Nil
    Snake(head, body)
  }

  private def moveXLimits(x: Int): Int = {
    if (x > width) 0
    else x
  }

  private def moveYLimits(y: Int): Int = {
    if (y > width) 0
    else y
  }

  private def randomMove(): Move = {
    val rand = Random.between(0,2)
    if (rand == 0) LeftMove()
    else RightMove()
  }

  // Point of improvement -- for performance improvements we should avoid to redo
  // some calculations or/and checks like currentFood.isEmpty on every recursive call
  @tailrec
  private def nextFood(snake: Snake, currentFood: Option[Food]): Option[Food] = {
    def nextFoodPosition(): (Int, Int) = {
      val x = Random.between(0, width+1)
      val y = Random.between(0, height+1)
      (x, y)
    }

    def hasConflict(foodPosition: (Int, Int)): Boolean = {
      val snakePositions = (snake.head()._1, snake.head()._2) :: snake.startBody
      snakePositions.contains(foodPosition)
    }

    if (currentFood.isEmpty) {
      val foodPosition = nextFoodPosition()
      if (hasConflict(foodPosition))  nextFood(snake, None)
      else Some(Food(foodPosition))
    } else currentFood
  }

  @tailrec
  private def gameLoop(snake: Snake, food: Option[Food]): Unit = {
    val stopCondition = snake.body().size > 3
    snake.move(moveXLimits, moveYLimits)
    snake.changeDirection(randomMove())
    if (stopCondition) {
      logger.info("Stopping the game")
      snake.prettyPrint(logger)
    } else if (canEat(snake, food)) {
      snake.eat()
      gameLoop(snake, nextFood(snake, None))
    } else gameLoop(snake, food)
  }

  private def canEat(snake: Snake, food: Option[Food]): Boolean = {
    val snakeHead = snake.head()
    food.exists { f =>
      snakeHead._1 == f.x &&
        snakeHead._2 == f.y
    }
  }
}
