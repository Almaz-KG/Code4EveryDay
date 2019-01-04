package exercises.part2


/*
    1. Crash your JVM by OutOfMemoryError
    2. Crash your JVM by StackOverflowError
    3. PocketCalculator
        + add(x, y)
        + subtract(x, y)
        + multiply(x, y)
        + divide(x, y)
       exceptions:
        - OverflowException if add(x, y) exceeds Int.MAX_VALUE
        - UnderflowException if subtract(x, y) exceeds Int.MIN_VALUE
        - MathCalculationException for division by 0
*/
object ExceptionExercise1 extends App {

  def crashJvmByOutOfMemoryError(): Unit = {

    new Array[Int](1024 * 1024 * 1024)

  }

  def crashJvmByStackOverflowError(): Int = 1 + crashJvmByStackOverflowError()

  object PocketCalculator {

    class PocketCalculatorException(message: String) extends Exception(message)

    class OverflowException(message: String) extends PocketCalculatorException(message)

    class UnderflowException(message: String) extends PocketCalculatorException(message)

    class MathCalculationException(message: String) extends PocketCalculatorException(message)

    def add(x: Int, y: Int) : Int = {
      val result = x + y

      if (x > 0 && y > 0 && result < 0)
        throw new OverflowException(s"Int.MAX_VALUE for add($x, $y)")
      else if (x < 0 && y < 0 && result > 0)
        throw new UnderflowException(s"Int.MIN_VALUE for add($x, $y)")
      else result
    }

    def subtract(x: Int, y: Int) : Int = {
      val result = x - y

      if (x > 0 && y < 0 && result < 0)
        throw new OverflowException(s"Int.MAX_VALUE for add($x, $y)")
      else if (x < 0 && y > 0 && result > 0)
        throw new UnderflowException(s"Int.MIN_VALUE for add($x, $y)")
      else result
    }

    def multiply(x: Int, y: Int) : Int = {
      val result = x * y

      if (x > 0 && y < 0 && result > 0)
        throw new UnderflowException(s"Int.MIN_VALUE for add($x, $y)")
      else if (x < 0 && y > 0 && result > 0)
        throw new UnderflowException(s"Int.MIN_VALUE for add($x, $y)")
      else if (x > 0 && y > 0 && result < 0)
        throw new OverflowException(s"Int.MAX_VALUE for add($x, $y)")
      else if (x < 0 && y < 0 && result < 0)
        throw new OverflowException(s"Int.MAX_VALUE for add($x, $y)")
      else result

    }

    def divide(x: Int, y: Int) : Int =
      if (y == 0) throw new MathCalculationException("Division by 0")
      else x / y
  }


  // crashJvmByOutOfMemoryError()
  // crashJvmByStackOverflowError()

//  println(PocketCalculator.add(Int.MaxValue, Int.MaxValue))
  println(PocketCalculator.divide(2, 0))
}
