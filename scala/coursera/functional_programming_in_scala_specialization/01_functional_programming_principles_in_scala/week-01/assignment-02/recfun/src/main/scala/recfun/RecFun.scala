package recfun

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
//    println("Pascal's Triangle")
//    for (row <- 0 to 10) {
//      for (col <- 0 to row)
//        print(s"${pascal(col, row)} ")
//      println()
//    }

    print(balance("())(".toList))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = ???

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    @scala.annotation.tailrec
    def balance(index: Int, opened: Int, chars: List[Char]): Int = {
      if (index >= chars.length) opened
      else {
        if (chars(index) == '(') balance(index + 1, opened + 1, chars)
        else if (chars(index) == ')' && opened != 0) balance(index + 1, opened - 1, chars)
        else if (chars(index) == ')') balance(Int.MaxValue, -1, chars)
        else balance(index + 1, opened, chars)
      }
    }

    val count = balance(0, opened = 0 ,chars)

    count == 0
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
