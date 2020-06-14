package exercises.part3fp

object AnonymousFunctionsExercise {

  /*
    * 2. Rewrite the "danielsAdder" as an anonymous function
    *
    */
  def main(args: Array[String]): Unit = {

    val danielsAdder: Int => Int => Int = v1 => v2 => v1 + v2

  }

}
