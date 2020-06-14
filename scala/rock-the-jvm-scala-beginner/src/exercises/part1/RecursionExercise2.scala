package exercises.part1

import scala.annotation.tailrec

object RecursionExercise2 extends App {

  // Concatenate string using tail recursion
  def stringConcatenation(text: String, number: Int, separator: String = " "): String = {

    @tailrec
    def stringConcatenationHelper(text: String, number: Int, separator: String, accumulator: String): String = {
      if (number <= 0) accumulator
      else stringConcatenationHelper(text, number - 1, separator, accumulator + separator + text)
    }

    stringConcatenationHelper(text, number, separator, "")
  }

  println(stringConcatenation("A", 5, "\n"))

  // isPrimeNumber function with tail recursion
  def isPrimeNumber(number: Int): Boolean = {
    @tailrec
    def isPrimeNumber(divisor: Int): Boolean = {
      if (divisor <= 1 ) true
      else if (number % divisor == 0) false
      else isPrimeNumber(divisor - 1)
    }

    isPrimeNumber(number / 2)
  }

  println(s"Is 2003 prime number: ${isPrimeNumber(2003)}")
  println(s"Is 629 prime number: ${isPrimeNumber(629)}")


  // Fibonacci numbers function with tail recursion
  def fibonacci(index: Int): Int = {
    @tailrec
    def fibonacci(i: Int, last: Int, nextToLast: Int): Int = {
      if (i >= index) last
      else fibonacci(i + 1, last + nextToLast, last)
    }

    if (index <= 2) 1
    else fibonacci(2, 1, 1)
  }

  println(fibonacci(8))
}
