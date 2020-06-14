package lectures.part1advancedscala

object AdvancedPatternMatching extends App {

  val numbers = List(1)

  val description = numbers match {
    case head :: Nil => println(s"The only element is $head")
    case _ =>
  }

  /*
    - constants
    - wildcards
    - case-classes
    - tuples
    - some special magics like above
   */

  class MyPerson(val name: String, val age: Int)
  object MyPerson {
    def unapply(arg: MyPerson): Option[(String, Int)] = if (arg.age > 18) Some((arg.name, arg.age)) else None
    def unapply(age: Int): Option[String] = Some(if (age < 18) "minor"  else "major")
  }

  val person = new MyPerson("Bob", 23)
  val personDescription = person match {
    case MyPerson(name, _) if name.startsWith("B") => s"This is b-$name"
    case MyPerson(name, age) if age < 21 => s"This is $name and I can't drink in USA"
  }

  val personAgeDescription = person.age match {
    case MyPerson(status) => s"My legal status is $status"
  }
  println(personDescription)
  println(personAgeDescription)
}
