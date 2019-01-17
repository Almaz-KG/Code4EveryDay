package exercises.part3fp

object WhatsAFunctionExercise1 {

  /***
    *
    * 1. Write a function which takes 2 strings and concatenates them
    * 2. Write a function the MyPredicate and MyTransformer into function types    *
    * 3. Define a function with takes an int and returns another function which takes an int and returns an int
    *   - what is the type of this function
    *   - how to do it
    */
  def main(args: Array[String]): Unit = {

    // example 1
    def function1(v1: String, v2: String): String = v1 + v2
    def concatenator: (String, String) => String = (v1, v2) => v1 + v2

    // example 3
    def myAdder(value: Int): Int => Int = {
      def innerFunction(arg1: Int)(arg2: Int): Int = arg1 + arg2
      innerFunction(value)
    }

    val danielsAdder: Int => Int => Int = v1 => v2 => v1 + v2

    println(concatenator("Hello ", "Scala"))
    println(function1("Hello ", "Scala"))
    val myIncrementer = myAdder(1)
    println(myIncrementer(4))
    println(myAdder(1)(4))

    val adder3 = danielsAdder(3)
    println(adder3(5))
    println(danielsAdder(3)(5))  // curried function
  }
}
