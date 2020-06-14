package exercises.part4implicits

/**
  * Exercise
  * - improve the Equal Type class with an implicit conversion class, which has following methods
  *   - ===(anotherValue: T): Boolean
  *   - !==(anotherValue: T): Boolean
  */
object TypeClasses2 extends App {
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

  implicit class TypeSafeEqual[T](value: T) {
    def === (anotherValue: T) (implicit equality: Equality[T]): Boolean = equality.equals(value, anotherValue)

    def !== (anotherValue: T)(implicit equality: Equality[T]): Boolean = ! equality.equals(value, anotherValue)
  }

  val john = User("John", 30, "john-super-user@email.com")
  val john1 = User("John", 40, "john-super-user@email.com")
  val daniel = User("Daniel", 30, "daniel-super-user@email.com")

  println(john === john1)
  println(john === daniel)
}
