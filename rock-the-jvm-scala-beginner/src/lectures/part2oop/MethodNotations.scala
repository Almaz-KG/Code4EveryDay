package lectures.part2oop

object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String) {

    def likes(movie: String): Boolean = favoriteMovie == movie

    def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"

    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"

    def -(person: Person): String = s"${this.name} is not hanging out with ${person.name}"

    def unary_! : String = s"$name, what the heck?!"

    def isAlive: Boolean = true

    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"

  }

  val mary = new Person("Mary", "Inception")

  println(mary.likes("Inception"))
  println(mary likes "Inception")
  // Infix notations = operator notation (syntactic sugar)

  val tom = new Person("Tom", "Fight club")
  val jemmy = new Person("Jemmy", "Tom & Jerry")
  println(mary hangOutWith tom)
  println(mary + tom)
  println(mary - jemmy)


  1 + 2
  println(1 + 2)
  println(1.+(2))

  // All operators are methods
  // Akka actors have ! ? operations

  // Prefix notations
  val x = -1
  val y = 1.unary_-
  println(y)
  // unary_ prefix operator works with only several operators: - + ~ !

  println(!mary)


  // Postfix notations
  println(mary.isAlive)
  println(mary isAlive)

  // apply
  println(mary.apply())
  println(mary())
}


