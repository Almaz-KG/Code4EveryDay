package exercises.part1

object FunctionExercises extends App {

  def greeting(name: String, age: Int): String = s"Hi, my name is $name and I am $age years old"

  def factorial(number : Int ): Long = if (number <= 0) 1 else number * factorial(number - 1)

  def fibonacci(number: Int): Int = {
    if (number <= 2)
      1
    else
      fibonacci(number - 1) + fibonacci(number - 2)
  }

  def isPrimeNumber(number: Int): Boolean = {
//    def isPrimeUntil(t: Int): Boolean = {
//      if (t <= 1) true
//      else
//        number % t != 0 && isPrimeUntil(t - 1)
//    }
//    isPrimeUntil(number / 2)


    // My own another solution
    def isPrimeNumber(divider: Int) : Boolean = {
      if (divider == 1) true
      else if (number % divider == 0) false
      else
        isPrimeNumber(divider - 1)
    }

    isPrimeNumber(number / 2)

  }

  private[this] def checkGreetingFunction(): Unit = {
    println(greeting("Almaz", 25))
    println(greeting("Zhenish", 23))
    println(greeting("Akiles", 22))
    println(greeting("Unhandled exception", -1))
  }

  private[this] def checkPrimeNumbers(): Unit = {
    val primeNumbers = Array(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
                        61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131,
                        137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199)

    for (elem <- primeNumbers) {
      if (!isPrimeNumber(elem))
        throw new IllegalStateException(s"isPrimeNumber function is not working correctly for element $elem")
    }

    val nonPrimeNumbers = Array(4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30)
    for (elem <- nonPrimeNumbers) {
      if (isPrimeNumber(elem))
        throw new IllegalStateException(s"isPrimeNumber function is not working correctly for element $elem")
    }
  }

  private[this] def checkFibonacciNumbers(): Unit = {
    val fibSet = Array(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377,
                       610, 987, 1597, 2584, 4181, 6765, 10946, 17711)

    for ((expected, index) <- fibSet.zipWithIndex) {
      val actual = fibonacci(index + 1)
      if (expected != actual)
        throw new IllegalStateException(s"fibonacci function is not working correctly for number: ${index+1}" +
          s"Expected: $expected, Actual: $actual")
    }
  }

  private[this] def checkFactorialNumbers(): Unit = {
    val data = Array(1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800,
      479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L,
      6402373705728000L, 121645100408832000L, 2432902008176640000L)


    for ((expected, index) <- data.zipWithIndex) {
      val actual = factorial(index)

      if (actual != expected)
        throw new IllegalStateException(s"factorial function is not working correctly for number: $index." +
          s"Expected: $expected, Actual: $actual")
    }
  }

  checkGreetingFunction()
  checkFactorialNumbers()
  checkFibonacciNumbers()
  checkPrimeNumbers()
}
