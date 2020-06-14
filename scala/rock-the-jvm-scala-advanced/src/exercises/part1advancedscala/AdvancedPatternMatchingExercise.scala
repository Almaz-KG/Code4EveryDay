package exercises.part1advancedscala

object AdvancedPatternMatchingExercise extends App {

  /*
      Rewrite this pattern-matching code with more elegant style
   */
  val n : Int = 45
  val mathProperty = n match {
    case x if x < 10 && x > -10 => "single digit"
    case x if x % 2 == 0 => "an even number"
    case _ => "no property"
  }
  println(mathProperty)

  object MyNumber {
    def unapply(x: Int): Option[String] =
      if (x < 10 && x > -10)
        Some("single digit")
      else if (x % 2 == 0)
        Some("an even number")
      else Some("no property")
  }

  val mathPropertyElegant = n + 1 match {
    case MyNumber(description) => s"$description and another text"
    case _ => "nothing"
  }

  println(mathPropertyElegant)

  // More right way
  object even {
    def unapply(x: Int): Option[String] = if (x % 2 == 0) Some("an even number") else None
  }

  object singleDigit {
    def unapply(x: Int): Option[String] = if (x < 10 && x > -10) Some("single digit") else None
  }

  val mathPropertyMoreClear = n / 10 match {
    case singleDigit(d) => d
    case even(d) => d
    case _ => "no property"
  }

  println(mathPropertyMoreClear)

}
