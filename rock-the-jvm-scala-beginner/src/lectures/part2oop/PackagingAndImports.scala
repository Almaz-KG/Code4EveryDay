package lectures.part2oop

object PackagingAndImports extends App {

  // package members are accessible by their simple name
  val person: Person = new Person("Almaz", 26)

  // use fully qualified class name
  val writer = new exercises.part2.Writer("Jorge", "Acetozi", 1990)

  // or import the package
  import exercises.part2.Novel
  val novel: Novel = new Novel("Love is life", 2018, writer)

  // packages are in hierarchy
  // matching folder structure

  // package object
  sayHello()      // it is from package object
  SPEED_OF_LIGHT  // it is from package object


  // grouped imports
  import exercises.part2.{Writer, Counter}
  val counter = new Counter()
  val writer2 = new Writer("Writer2", "Writer", 1789)

  import java.util.{Date => JavaUtilDate}
  import java.sql.{Date => SqlDate}

  val date = new JavaUtilDate()
  val sqlDate1 = new java.sql.Date(1L)
  val sqlDate2 = new SqlDate(1L)


  // default imports
  // java.lang.*  - String, Object, Exceptions ...
  // scala.*      - Int, Nothing, Unit, etc ...
  // scala.Predef.* - println, ???, etc ...
}
