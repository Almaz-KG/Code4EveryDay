package lectures.part4implicits

import java.util.Date

object JsonSerialization extends App {

  /**
    *  Users, posts, feeds
    *   - how to serialize to JSON
    */
  case class User(name: String, age: Int, email: String)
  case class Post(content: String, createdAt: Date)
  case class Feed(user: User, post: List[Post])

  /**
    * How to do it?
    *   - first, implement intermediate data types: Int, String, List, Date
    *   - type classes for conversion to intermediate data types
    *   - serialize to JSON
   */

  // intermediate data types
  sealed trait JsonValue {
    def toJsonString: String
  }

  final case class JsonObject(values: Map[String, JsonValue]) extends JsonValue {
    def toJsonString: String = values.map { case (key, value) => "\"" + key+ "\":" + value.toJsonString } .mkString("{", ",", "}")
  }
  final case class JsonList(list: List[JsonValue]) extends JsonValue {
    def toJsonString: String = list.map(item => item.toJsonString).mkString("[", ",","]")
  }
  final case class JsonString(value: String) extends JsonValue {
    def toJsonString: String = "\""  + value + "\""
  }
  final case class JsonNumber(number: Double) extends JsonValue {
    def toJsonString: String = number.toString
  }
  final case class JsonDate(date: Date) extends JsonValue {
    def toJsonString: String = "\"" + date.toString + "\""
  }

  val data = JsonObject(Map(
    "user"-> JsonString("Daniel"),
    "posts"-> JsonList(List(
      JsonString("Scala Rocks!"),
      JsonNumber(4533.8)
    ))))

  println(data.toJsonString)

  // type classes
  /**
    * 1 - type class
    * 2 - type class instances (implicits)
    * 3 - pimp library to use type class instances
    */
  implicit class JsonOps[T](value: T){
    def toJsonValue(implicit converter: JsonConverter[T]): JsonValue = converter.toJsonValue(value)
  }

  trait JsonConverter[T] {
    def toJsonValue(value: T): JsonValue
  }
  implicit object StringConverter extends JsonConverter[String] {
    override def toJsonValue(value: String): JsonValue = JsonString(value)
  }
  implicit object NumberConverter extends JsonConverter[Double] {
    override def toJsonValue(value: Double): JsonValue = JsonNumber(value)
  }
  implicit object DateConverter extends JsonConverter[Date] {
    override def toJsonValue(value: Date): JsonValue = JsonDate(value)
  }
  implicit object UserConverter extends JsonConverter[User] {
    override def toJsonValue(user: User): JsonValue = JsonObject(Map(
        "name" -> JsonString(user.name),
        "age" -> JsonNumber(user.age),
        "email" -> JsonString(user.email)
      ))
  }
  implicit object PostConverter extends JsonConverter[Post] {
    override def toJsonValue(post: Post): JsonValue = JsonObject(Map(
      "content" -> JsonString(post.content),
      "created" -> JsonDate(post.createdAt)
    ))
  }
  implicit object FeedConverter extends JsonConverter[Feed] {
    override def toJsonValue(feed: Feed): JsonValue = JsonObject(Map(
      "user" -> feed.user.toJsonValue,
      "posts" -> JsonList(feed.post.map(_.toJsonValue))
    ))
  }

  // call toJson on result

  val userDaniel = User("Daniel", 30, "daniel@rock-the-jvm.com")
  val post1 = Post("Scala is awesome", new Date(2019, 2, 21))
  val post2 = Post("Spark is written on Scala", new Date(2018, 1, 1))
  val feed = Feed(userDaniel, List(post1, post2))

  println(userDaniel.toJsonValue.toJsonString)
  println(post1.toJsonValue.toJsonString)
  println(feed.toJsonValue.toJsonString)
}
