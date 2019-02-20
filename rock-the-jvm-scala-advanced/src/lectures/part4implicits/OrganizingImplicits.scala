package lectures.part4implicits

object OrganizingImplicits extends App {
  implicit def reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
//  implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)
  println(List(1, 4, 5, 3, 2).sorted)
  // scala.Predef

  /**
    * Implicits (used as implicit parameters):
    *  - val/var
    *  - objects
    *  - accessor methods = defs with no parentheses
  */

  object AlphabeticNameOrdering {
    // this method is not visible for auto implicits for persons.sorted
    implicit val orderingPersonsByName: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  object AgeOrdering {
    // this method is not visible for auto implicits for persons.sorted
    implicit val orderingPersonsByAge: Ordering[Person] = Ordering.fromLessThan((a, b) => a.age > b.age)
  }

  case class Person(name: String, age: Int)

  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66)
  )

//  import AlphabeticNameOrdering._
  // OR
  import AgeOrdering._
  println(persons.sorted)

  /**
    * Implicit scope
    *  - normal scope = local scope
    *  - imported scope
    *  - companions of all types involved in the method signature
    *     i.e., for method signature:
    *       def sorted[B >: A](implicit ord: Ordering[B]): List[B] = {
    *         - List
    *         - Ordering
    *         - all the types involved = A or any supertype of A    *
    */
  /**
    * IMPLICIT BEST PRACTICES
    *   -  if there is a single possible value for it and you can edit the code of the type - USE an implicit VAL
    *   -  if there is are many possible values for it - USE define implicits on separate containers, as shown as bellow
    */
}
