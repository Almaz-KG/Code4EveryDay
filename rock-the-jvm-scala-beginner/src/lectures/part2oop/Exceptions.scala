package lectures.part2oop

object Exceptions extends App {

  val x: String = null
  // println(x.length) produces NPE

  val exception: Exception = new NullPointerException

  // big logic
  // other logic
  // some logic
  // and finally throws
  // what is the thrown code line ?

  //  throw exception


  // How to catch exceptions ?

  try {
//    throw new InterruptedException
    def getInt(str: String): Int = str.toInt

    // produce stack overflow error
    def stackoverflowError(): Int = 1 + stackoverflowError()

    println(getInt("1"))
//    println(getInt("1E"))

//    stackoverflowError()
//    throw new RuntimeException

  } catch {
      case _: RuntimeException =>
        println("Caught a Runtime exception")

        // finaly block will not work
        System.exit(-1)

      case _: Exception => println("Caught a some exception")
  } finally {
    println("Finally block")
  }


  val potentiallyResult: Int = try {
    def getInt(boolean: Boolean): Int = {
      if (boolean) 42
      else throw new RuntimeException("Oooops!")
    }

    getInt(false)
  } catch {
    case e: RuntimeException => 43
  } finally {
    println("Finally block 2")

    // No effects for result
    54
  }

  println(potentiallyResult)

  // Use finally block of try-catch for only side-effects

}
