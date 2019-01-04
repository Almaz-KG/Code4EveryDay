package lectures.part2oop

object CaseClasses extends App {
  /*
      1. equals and hashCode implemented out of the box OOTB
      2. toString
      3. class parameters are class fields
      4. case classes have handy copy methods
      5. case classes have companion objects
      6. case classes are serializable
      7. case classes have extractor patterns = case classes can be used in pattern matching
   */

  //  class Person(name: String, age: Int)
  case class Person(name: String, age: Int)

  val jim = new Person("Jim", 34)

  println(jim)
  println(jim.hashCode())
  println(new Person("Jim", 34).hashCode())
  println(jim == new Person("Jim", 34))

  val jim2 = jim.copy(age = jim.age + 1)
  println(jim2)
  println(jim2.hashCode())
  println(new Person("Jim", 35).hashCode())
  println(jim2 == new Person("Jim", 35))

  val thePerson = Person
  val mary = Person("Mary", 23)


  // case object
  case object UnitedKingdom {
    def name: String = "The United Kingdom of Great Britain and Northern Ireland"
  }
}
