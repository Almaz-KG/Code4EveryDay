package exercises

import scala.annotation.tailrec

object RecursionExercise1FactorialNumberResearch extends App {


  def factorial(number: Int): BigInt = {

    @tailrec
    def factorial(number: Int, accumulator: BigInt): BigInt = {
      if (number <= 1) accumulator
      else factorial(number - 1, number * accumulator) // tail recursion
    }

    factorial(number, 1)
  }

  def countOfEndZeros(number: String): Int = {
    def countOfEndZeros(number: String, index: Int, accumulator: Int) : Int = {
      if (number.charAt(index) != '0') accumulator
      else if (index - 1 < 0) accumulator
      else countOfEndZeros(number.substring(0, index), index - 1, accumulator + 1)
    }

    countOfEndZeros(number, number.length - 1, 0)
  }

  val countExamples = 1000
  val inputs: Stream[Int] = Stream.from(1)

  inputs
    .map(i => (i, factorial(i).toString()))
    .map(t => (t._1, countOfEndZeros(t._2)))
    .take(countExamples)
    .foreach(println)
}
