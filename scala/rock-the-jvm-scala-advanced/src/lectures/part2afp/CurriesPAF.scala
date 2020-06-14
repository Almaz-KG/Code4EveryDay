package lectures.part2afp

object CurriesPAF extends App {

  // curried functions
  val aSupperAdder: Int => Int => Int = x => y => x + y

  val add3 = aSupperAdder(3)
  println(add3(5))
  println(aSupperAdder(3)(5)) // curried function

  // METHOD !
  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  val add4: Int => Int = curriedAdder(4)
  println(add4(1))

  // lifting = ETA-Expansion

  // functions != methods (JVM Limitation)
  def inc(x: Int): Int = x + 1
  List(1, 2, 3).map(x => inc(x)) // ETA-expansion

  // Partial function applications
  // val add5 = curriedAdder(5) // NOT WORKS
  val add5 = curriedAdder(5) _ // WORKS, because compiler create ETA-expansion, re-writes for lambda


  // EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  // add7: Int => Int = y => 7 + 7
  val add7_1: Int => Int = curriedAddMethod(7)
  val add7_2 = curriedAddMethod(7) _ // PAF - ETA-expansion
  val add7_3 = curriedAddMethod(7)(_) // PAF - ETA-expansion
  val add7_4: Int => Int = x => simpleAddFunction(7, x)
  val add7_5: Int => Int = x => simpleAddMethod(7, x)
  val add7_6 = simpleAddFunction.curried(7)

  val add7_7 = simpleAddMethod(7, _: Int)  // Compiler re-writes this like y => simpleAddMethod(7, y)
  val add7_8 = simpleAddFunction.curried(7)
  val add7_9 = simpleAddFunction(7, _: Int)

  // underscores are powerful
  def concatenator(a: String, b: String, c: String): String = a + b + c
  val insertName = concatenator("Hello, I'am ", _: String, ", how are you?") // x: String => concatenator("Hello, I'am ", x, ", how are you?")

  println(insertName("Almaz"))

  val fillInTheBlanks = concatenator("Hello, ", _:String, _:String) // (x: String, y: String) => concatenator("Hello, I'am ", x, y)
  println(fillInTheBlanks("Almaz", " Scala is awesome!"))

  /*
    1. Process a list of numbers and return their string representations with different formats
        Use: %4.2f, %8.6f, %14.12f with curriedFormatter function
   */
  println("%4.1f".format(Math.PI))
  def formatter(format: String)(number: Number): String = format.format(number)

  val shortFormatter = formatter("%4.2f") _
  val bigFormatter = formatter("%8.6f") _
  val largeFormatter = formatter("%14.12f") _

  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)
  println(numbers.map(x => shortFormatter(x)))
  println(numbers.map(x => bigFormatter(x)))
  println(numbers.map(x => largeFormatter(x)))

  /*
    2. Difference between
        - functions vs methods
        - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: ()=> Int) = f() + 1

  def method: Int = 42
  def parenthesisMethod(): Int = 42

  byName(23) // OK
  byName(method) // OK
  byName(parenthesisMethod()) // OK
  byName(parenthesisMethod) // OK, but be aware => byName(parenthesisMethod())
//  byName(() => 42) // does not work
  byName((() => 42)()) // OK

//  byName(parenthesisMethod _) // does not work
//
//  byFunction(45)  // does not work
//  byFunction(method) // does not work
  byFunction(method _) // does not work
  byFunction(parenthesisMethod) // compiler does ETA-expansion

  byFunction(() => 42)

  byFunction(parenthesisMethod _)
}
