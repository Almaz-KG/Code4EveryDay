package exercises.part3fp

import scala.annotation.tailrec

/*
  * HOF - Higher order functions
  */
object HigherOrderFunctionsAndCurries {

  def main(args: Array[String]): Unit = {

    val superFunction: (Int, (String, Int => Boolean) => Int) => Int => Int = null
    // higher order function (HOF) - because this method receives a function as a parameter

    // Examples of HOF is map, flatMap, filter, etc

    // function that applies a function n times over a value x
    // nTimes(f, n, x)
    // nTimes(f, 3, x) = f(f(f(x)))

    @tailrec
    def nTimes(f: Int => Int, n: Int, x: Int): Int = {
      if (n <= 0)
        x
      else
        nTimes(f, n - 1, f(x))
    }

    val plusOne = (x: Int) => x + 1
//    val doubler = (x: Int) => x * 2
    println(nTimes(plusOne, 10, 1))

    def nTimesBetter(f: Int => Int, n: Int): Int => Int = {
      if (n <= 0) (x: Int) => x
      else
        (x: Int) => nTimesBetter(f, n - 1)(f(x))
    }

    val increment10 = nTimesBetter(plusOne, 200)
    println(increment10(2))

    // Int => (Int => Int)
    // curried functions
    val supperAdder = (x: Int) => (y: Int) => x + y
    val add3 = supperAdder(3)
    println(add3(4))

    // functions with multiple parameter list
    def curriedFormatter(c: String)(x: Double): String = c.format(x)

    val standardFormat: Double => String = curriedFormatter("%4.2f")
    val preciseFormat: Double => String = curriedFormatter("%10.8f")

    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))
  }
}
