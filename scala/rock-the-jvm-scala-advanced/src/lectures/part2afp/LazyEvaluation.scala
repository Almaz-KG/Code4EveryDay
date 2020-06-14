package lectures.part2afp

object LazyEvaluation extends App {

  // lazy: DELAYS the evaluation of values
  lazy val x: Int = {
    println("Hello")

    42
  }
  println(x)
  println(x)

  // examples of implications
  def sideEffectCondition: Boolean = {
    println("Booo")
    true
  }

  def simpleCondition: Boolean = false
  lazy val lazyCondition = sideEffectCondition

  println(if (simpleCondition && lazyCondition) "yes" else "no")

  // in conjunction with call by name
  def byNameMethod(n: => Int): Int = {
    // CALL BY NEED
    lazy val t = n
    t + t + t + 1
  }

  def retrieveMagicValue = {
    // side effect or long computation
    println("Waiting")
    Thread.sleep(1000)
    42
  }
  // use lazy vals
  println(byNameMethod(retrieveMagicValue))


  // filtering with lazy vals
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30? ")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is bigger than 20? ")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)
//  val lt30 = numbers.filter(lessThan30) // List(1, 25, 5, 23)
//  println(lt30)
//  val gt20 = lt30.filter(greaterThan20) // List(25, 40, 23)
//  println(gt20)

  val lt30lazy = numbers.withFilter(lessThan30) // lazy vals under the hood
  val gt20Lazy = lt30lazy.withFilter(greaterThan20)
  gt20Lazy.foreach(println)

  // for-comprehensions use withFilter with guards
  for {
    a <- List(1, 2, 3) if a % 2 == 0 // use lazy vals!
  } yield a + 1
  // equals
  List(1, 2, 3).withFilter(_ % 2 == 0).map(_ + 1)





}
