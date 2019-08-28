package exercises.part4implicits

object TypeClasses3 extends App {
  case class User(name: String, age: Int, email: String)

  trait HtmlSerializer[T] {
    def serialize(value: T): String
  }

  object HtmlSerializer {
    def serialize[T](value: T)(implicit serializer: HtmlSerializer[T]): String = serializer.serialize(value)

    def apply[T](implicit serializer: HtmlSerializer[T]): HtmlSerializer[T] = serializer
  }

  implicit object IntSerializer extends HtmlSerializer[Int] {
    override def serialize(value: Int): String = s"<div>This is int: $value</div"
  }

  implicit object UserSerializer extends HtmlSerializer[User] {
    override def serialize(user: User): String = s"<div>This is ${user.name} and he is ${user.age} years old </div"
  }

  object PartialUserSerializer extends HtmlSerializer[User] {
    override def serialize(user: User): String = s"<div>This is ${user.name}</div"
  }

  implicit class HtmlEnrichment[T](value: T) {
    def toHtml(implicit serializer: HtmlSerializer[T]): String = {
      serializer.serialize(value)
    }
  }

  def htmlBoilerplate[T](content: T)(implicit serializer: HtmlSerializer[T]): String = {
    s"<html><body>${content.toHtml(serializer)}</body></html>"
  }

  // context bounding
  def htmlSugar[T : HtmlSerializer](content: T): String = {
    val serializer = implicitly[HtmlSerializer[T]]

    s"<html><body>${content.toHtml(serializer)}</body></html>"
  }

  // implicitly method
  case class Permissions(mask: String)
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // in some other part of the code
  val standardPerms = implicitly[Permissions]

  val john = User("John", 33, "john-super-user@email.com")
  println(john.toHtml)
  println(john.toHtml(PartialUserSerializer))
  println(42.toHtml)
}
