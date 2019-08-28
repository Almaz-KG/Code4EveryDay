package lectures.part4implicits

object TypeClasses1 extends App {

  trait HtmlWritable {
    def toHtml: String
  }

  case class User(name: String, age: Int, email: String) extends HtmlWritable {
    override def toHtml: String = s"<div>$name ($age years old) <a href=$email /></div>"
  }

  val john = User("John", 32, "john@rockthejvm.com")
  /**
    *  1 - It forks for the types WE write
    *  2 - ONE implementation out of quite a number
    */

  // Use pattern matching
  object HtmlSerializerPM {
    def serialize(value: Any): String = value match {
      case User(name, age, email) => "Some user html here"
//      case java.util.Date => "Some date html here"
      case _ => "Empty html"
    }
  }

  trait HtmlSerializer[T] {
    def serialize(value: T): String
  }

  object UserSerializer extends HtmlSerializer[User] {
    override def serialize(user: User): String = {
      s"<div>${user.name} (${user.age} years old) <a href=${user.email}/></div>"
    }
  }

  println(UserSerializer.serialize(john))

  trait MyTypeClassTemplate[T] {
    def action1(value: T): String
  }

  /**
    * Equality example
    */
  trait Equality[T] {
    def equals(firstValue: T, secondValue: T): Boolean
  }

  object UserEqualityByName extends Equality[User] {
    override def equals(firstValue: User, secondValue: User): Boolean = firstValue.name == secondValue.name
  }

  object UserEqualityByNameAndAge extends Equality[User] {
    override def equals(firstValue: User, secondValue: User): Boolean =
      firstValue.name == secondValue.name && firstValue.age == secondValue.age
  }
}
