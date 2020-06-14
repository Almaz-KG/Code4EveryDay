package lectures.part2oop

object OOPBasics extends App {

  val person = new Person("Zhenish", age = 23)

  person.greets("Akiles")

}

class Person(var name: String, val age: Int) {  // Constructor
  // class parameters, without keyword var or val, are NOT class fields

  // multiple constructor
  def this(name: String) = this(name, 0)

  def this() = this("Anonymous")

  private val x: Int = 1

  def greets(name: String): Unit = println(s"${this.name} says: Hi, $name")

  def greets(): Unit = println(s"Hi, I am $name")

}