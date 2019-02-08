package lectures.part4pm

object PatternsEverywhere {

  def main(args: Array[String]): Unit = {

    // big idea #1
    try {
      // code
    } catch {
      case r: RuntimeException => "runtime"
      case n: NullPointerException => "NPE"
      case _ => "something else"
    }
    /*
        try {
            // code
        } catch (e) {
          e match {
            case RuntimeException => "runtime"
            case NullPointerException => "NPE"
            case _ => "something else"
          }
        }
     */

    // big idea #2
    val list = List(1, 2, 3, 4)
    val evenOnes = for {
      x <- list if x % 2 == 0
    } yield 10 * x

    // generators are also based on pattern matching
    val tuples = List((1, 2), (3, 4))
    val filterdTuples = for {
      (first, second) <- tuples
    } yield first * second

    // case classes, :: operators, ...

    // big idea #3
    val tuple = (1, 2, 3)
    val (a, b, c ) = tuple
    println(b)

    // multiple value definitions based on pattern matching
    val head :: tail = list
    println(head)
    println(tail)

    // big idea #4
    // partial function literal based on pattern matching
    val mappedList = list.map {
      case v if v % 2 == 0 => v + " is even"
      case 1 => "the one"
      case _ => "something else"
    }

    println(mappedList)
  }
}
