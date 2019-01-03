package lectures.part1basics

object CallByNameCallByValue extends App {

  def calledByValue(x: Long): Unit = {
    println(s"By value: $x")
    println(s"By value: $x")
  }

  def calledByName(x: => Long): Unit = {
    println(s"By name:  $x")
    println(s"By name:  $x")
  }

  def infinite(): Int = 1 + infinite()

  def printFirst(x: Int, y: => Int) : Unit = println(x)

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())

//  printFirst(infinite(), 34)
  printFirst(34, infinite())
}
