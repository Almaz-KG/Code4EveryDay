package lectures.part4implicits

object TypeClasses2 extends App {

  case class User(name: String, age: Int, email: String)

  trait HtmlSerializer[T] {
    def serialize(value: T): String
  }

  object HtmlSerializer {
    def serialize[T](value: T)(implicit serializer: HtmlSerializer[T]): String = serializer.serialize(value)

    def apply[T](implicit serializer: HtmlSerializer[T]) = serializer
  }

  implicit object IntSerializer extends HtmlSerializer[Int] {
    override def serialize(value: Int): String = s"<div>This is int: $value</div"
  }

  implicit object UserSerializer extends HtmlSerializer[User] {
    override def serialize(user: User): String = s"<div>This is ${user.name} and he is ${user.age} years old </div"
  }

  println(HtmlSerializer.serialize(42))
  println(HtmlSerializer.serialize(User("John", 33, "john-super-user@email.com")))

  // access to the entire class members
  println(HtmlSerializer[User].serialize(User("John", 33, "john-super-user@email.com")))


}
