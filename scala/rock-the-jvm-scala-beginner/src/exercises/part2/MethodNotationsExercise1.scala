package exercises.part2

object MethodNotationsExercise1 extends App {

  class Person(val name: String, val favoriteMovie: String, val age: Int = 0) {

    def likes(movie: String): Boolean = favoriteMovie == movie

    def learns(classes: String): String = s"${this.name} learns $classes"

    def learnsScala: String = this learns "Scala"

    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"

    def +(nickName: String): Person = new Person(s"$name ($nickName)", favoriteMovie)

    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)

    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"

    def apply(n: Int): String = s"$name watched $favoriteMovie $n times"

    override def toString = s"Person($name, $favoriteMovie, $age)"
  }


  val mary = new Person("Mary", "Inception")
  println(mary.name)

  println((mary + "The rock star")())

  println(+mary )

  println(mary learns "Java")

  println(mary learnsScala)

  println(mary(10))

}
