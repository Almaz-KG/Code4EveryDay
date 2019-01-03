package lectures.part1basics

import scala.annotation.tailrec

object DefaultArgs extends App {

  @tailrec
  def factorial(n: Int, accumulator: BigInt = 1) : BigInt = {
    if (n <= 1) accumulator
    else factorial(n - 1, n * accumulator)
  }

  println(factorial(10))
  println(factorial(10, 2))
  println(factorial(n = 10, accumulator =  2))
  println(factorial(accumulator =  2, n = 10))
}
