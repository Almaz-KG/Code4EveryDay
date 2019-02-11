package lectures.part2afp

object PartialFunction extends App {

  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] == Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if(x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
    case _ => throw new FunctionNotApplicableException
  }

  // {1, 2, 5} => Int
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } // partial function value

  println(aPartialFunction(2))
  // println(aPartialFunction(54684)) => MatchError

  // partial function utilities
  println(aPartialFunction.isDefinedAt(54684))

  // lift to proper function
  val lifted = aPartialFunction.lift
  println(lifted(2))
  println(lifted(54684))

  val partialFunctionChain = aPartialFunction.orElse[Int, Int] {
    case 54684 => 67
  }

  println(partialFunctionChain(2))
  println(partialFunctionChain(54684))

  // Partial functions extend normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // Higher order functions accept partial function as well
  val aMappedList = List(1, 2, 3, 4).map {
    case 1 => 42
    case 2 => 31
    case _ => 0
  }
  println(aMappedList)

  /*
    Note: Partial functions can only have ONE parameter type
   */
}
