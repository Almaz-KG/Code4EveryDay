package recfun

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
   println("Pascal's Triangle")
   for (row <- 0 to 10) {
     for (col <- 0 to row)
       print(s"${pascal(col, row)} ")
     println()
   }

    // print(balance("())(".toList))
  }

  /**
   * Exercise 1
   */

  //     1
  //    1 1
  //   1 2 1
  //  1 3 3 1
  // 1 4 6 4 1

  def pascal(c: Int, r: Int): Int = {
    def pairedSum(l: List[Int]): List[Int] = {
      if (l.length < 2) l 
      else {
        for ((f, s) <- l zip l.drop(1)) yield(f + s)
      }
    }

    def buildRow(row: Int, prevRow: List[Int]): List[Int] = {
      if (row > r) prevRow
      else {
        val currRow = 1 :: (pairedSum(prevRow) :+ 1)

        buildRow(row + 1, currRow)
      }

    }

    if (r < 2) 1
    else {
      val row = buildRow(2, List(1, 1))
      row(c)
    }
  }  

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
  def countChange(money: Int, coins: List[Int]): Int = {
    if (coins.filter(x => x <=0).headOption.isDefined)
      throw new java.lang.IllegalArgumentException("IllegalArgumentException: " + coins)

    val coinsSorted = coins.sorted.reverse

    def f(money: Int, coins: List[Int]): Int = {
      if (money == 0) 1
      else if (money < 0) 0
      else if (coins.isEmpty && money > 0) 0
      else f(money, coins.tail) + f(money - coins.head, coins)
    }


    f(money, coinsSorted)
  }

}
