package lectures.part1basics

object Recursion extends App {

  def factorial(number: Int) : BigInt = {
    if (number <= 1) 1
    else {
      println(s"Computing factorial of $number. I first need factorial of ${number - 1}")
      val result = number * factorial(number - 1)
      println(s"Computed result for factorial $number is $result ")
      result
    }
  }


  def smartFactorial(number: Int): BigInt = {
    def factorial(number: Int, accumulator: BigInt) : BigInt = {
      if (number <= 1) accumulator
      else
        factorial(number - 1, number * accumulator)
    }

    factorial(number, 1)
  }

  println(smartFactorial(40))
  //  println(factorial(5000))

  // When you need loops, use TAIL recursion
}
