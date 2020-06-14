package example

object SqrtExample extends App {

  def sqrt(x: Double): Double = {
    def abs(x: Double): Double = if(x > 0) x else -x

    @scala.annotation.tailrec
    def sqrtIter(guess: Double): Double =
      if (isGoodEnough(guess)) guess
      else sqrtIter(improve(guess))

    def isGoodEnough(guess: Double): Boolean = abs(guess * guess - x) / x < 0.001

    def improve(guess: Double): Double = (guess + x / guess) / 2

    if (x < 0)
      throw new IllegalArgumentException("Sqrt of negative number")
    sqrtIter(1.0)
  }

  println(sqrt(1))

  println(sqrt(2))

  println(sqrt(4))

  println(sqrt(1e-6))

  println(sqrt(1e60))

  println(sqrt(16))

//  println(sqrt(-6))

}
