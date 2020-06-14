package exercises.part4implicits


object TypeClasses1 extends App {
  case class User(name: String, age: Int, email: String)

  trait Equality[T] {
    def equals(firstValue: T, secondValue: T): Boolean
  }

  object Equality {
    def apply[T](a: T, b: T)(implicit equality: Equality[T]): Boolean = equality.equals(a, b)
  }

  object UserEqualityByName extends Equality[User] {
    override def equals(firstValue: User, secondValue: User): Boolean = firstValue.name == secondValue.name
  }

  implicit object UserEqualityByAge extends Equality[User] {
    override def equals(firstValue: User, secondValue: User): Boolean = firstValue.age == secondValue.age
  }

//  implicit object UserEqualityByNameAndAge extends Equality[User] {
//    override def equals(firstValue: User, secondValue: User): Boolean =
//      firstValue.name == secondValue.name && firstValue.age == secondValue.age
//  }

  val john = User("John", 33, "john-super-user@email.com")
  val daniel = User("Daniel", 33, "daniel-super-user@email.com")

  // AD-HOC polymorphism
  println(Equality(john, daniel))
}
