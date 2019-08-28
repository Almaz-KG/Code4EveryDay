package lectures.part3fp

object MapFlatMapFilterFor {

  def main(args: Array[String]): Unit = {

    // print all combinations between two lists
    val numbers = List(1, 2, 3, 4)
    val chars = List('a', 'b', 'c', 'd')

    // List('a1', 'a2', ..., 'd4')

    //val combinations: List[String] = numbers.flatMap(n => { for (elem <- chars) yield s"$n$elem" })
    val combinations: List[String] = numbers.flatMap(n => chars.map(s => s"$n$s"))
    println(combinations)

    // for-comprehensions
    val forCombinations = for {
      n <- numbers if n % 2 == 0
      c <- chars
    } yield s"$n$c"
    println(forCombinations)

    // syntax overload
    numbers.map(_ * 2).foreach(print)
    println()
    numbers.map { x => x * 2 }.foreach(print)
  }
}
