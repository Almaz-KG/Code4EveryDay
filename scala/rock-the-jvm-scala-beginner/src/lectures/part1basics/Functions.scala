package lectures.part1basics

object Functions extends App {

  def aFunction(a: Int, b: String): String = a + " " + b

  def aParameterLessFunction(): Int = 42

  println(aParameterLessFunction)
  println(aParameterLessFunction())


  def aRepeatedFunction(text: String, n: Int, separator: String = " ") : String = {
    if (n == 1) text else text + separator + aRepeatedFunction(text, n - 1, separator)
  }

  println(aRepeatedFunction("Hello", 3, "\n"))

  // When you need loops, use recursion

  def aFunctionWithSideEffects(aString: String): Unit = println(aString)

  def aBigFunction(a: Int): Int = {
    def aSmallerFunction(a: Int, b: Int) : Int = a + b

    aSmallerFunction(a, a-1)
  }
}
