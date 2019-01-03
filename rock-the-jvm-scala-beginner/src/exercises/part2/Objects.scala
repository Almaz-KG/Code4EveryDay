package exercises.part2

object Objects extends App {

  // Scala does not have class-level functionality ("static")

  object Person {
    // type + only one instance
    // class-level functionality
    val N_EYES = 2

    def canFlay: Boolean = false

    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }

  class Person(val name: String){
    // instance-level functionality
    override def toString = s"Person($name)"
  }

  // COMPANIONS

  println(Person.N_EYES)
  println(Person.canFlay)

  // Scala object is singleton instance

  val mary = new Person("Mary")
  val john = new Person("John")
  println(mary == john)

  val bobbie = Person(mary, john)
  println(bobbie)


  // Scala applications = Scala Object with
  //  + def main(args: Array[String]): Unit
}
