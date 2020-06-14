package example

object FactorialTailRecursion extends App {

  def factorial(x: BigInt): BigInt = {
    @scala.annotation.tailrec
    def factorial(acc: BigInt, x: BigInt): BigInt = {
      if (x == 0) acc
      else factorial(acc * x, x - 1)
    }

    factorial(1, x)
  }


  print(factorial(4))

}
