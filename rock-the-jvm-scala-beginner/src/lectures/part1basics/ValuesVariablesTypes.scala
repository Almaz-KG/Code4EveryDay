package lectures.part1basics

object ValuesVariablesTypes extends App {

  // val means VALUE
  val x: Int = 42

  // val is not allowed to reassign value
  // x = 21  will not compile

  // Compiler can infer types
  val y /* : Int */= 42

  val aChar: Char = 'A'
  val aString: String = "Hello, World"
  val aBoolean: Boolean = true // false
  val aShort: Short = 32767 // Values from -32768 to 32767
  val aInt: Int = 2
  val aLong: Long = 1231231L
  val aFloat: Float = 1.17f
  val aDouble: Double = 3.14


  // var means VARIABLE, and variables allowed to reassign values
  var aVal: Int = 0
  aVal = 1

  println(aVal)
}
