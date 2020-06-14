package exercises.part2afp

object PartialFunctions extends App {

  /*
    Exercises

    1 - construct a partial functions instance yourself (an anonymous class)
    2 - implement chatbot as a partial function
   */
  val aPartialFunction: PartialFunction[String, String] = new PartialFunction[String, String] {
    override def apply(v1: String): String = {
      v1 match {
        case "Hello" => "Hi, I am a simple chatbot"
        case "Goodbye" => "Oh, no. You are leaving me"
        case "How are you" => "I am fine, thank you! What about you?"
      }
    }

    override def isDefinedAt(x: String): Boolean = x match {
      case "Hello" => true
      case "Goodbye" => true
      case "How are you" => true
      case _ => false
    }
  }


  print("-> ")
  scala
    .io
    .Source
    .stdin
    .getLines()
    .map(aPartialFunction)
    .foreach(line => print(line + "\n->"))
  }
