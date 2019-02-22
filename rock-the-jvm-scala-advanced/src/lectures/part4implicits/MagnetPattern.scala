package lectures.part4implicits

import scala.concurrent.Future

object MagnetPattern extends App {

  /**
    * Method overloading
    */

  class P2PRequest

  class P2PResponse

  class Serializer[T]

  trait Actor {
    def receive(statusCode: Int): Int

    def receive(request: P2PRequest): Int

    def receive(request: P2PResponse): Int

    def receive[T: Serializer](message: T): Int

    def receive[T: Serializer](message: T, statusCode: Int): Int

    def receive(future: Future[P2PRequest]): Int

    //    def receive(future: Future[P2PResponse]): Int  NOT COMPILED
    // others
  }

  /**
    * There are some problems
    * 1 - Type erasure
    * 2 - Lifting doestn't work for all overloads
    * val receiveFV = receive _
    * 3 - Code duplication
    * 4 - Type inference and default args
    */

  trait MessageMagnet[Result] {
    def apply(): Result
  }

  def receive[R](magnet: MessageMagnet[R]): R = magnet()

  implicit class FromP2PRequest(request: P2PRequest) extends MessageMagnet[Int] {
    override def apply(): Int = {

      // logic for handling a P2PRequest
      println("handling a P2P request")
      42
    }
  }

  implicit class FromP2PResponse(request: P2PResponse) extends MessageMagnet[Int] {
    override def apply(): Int = {

      // logic for handling a P2PRequest
      println("handling a P2P response")
      24
    }
  }

  receive(new P2PResponse)
  receive(new P2PRequest)

  /**
    * Benefits
    * - no more type erasure problem
    */
  implicit class FromResponseFuture(response: Future[P2PResponse]) extends MessageMagnet[Int] {
    override def apply(): Int = 2
  }

  implicit class FromRequestFuture(response: Future[P2PRequest]) extends MessageMagnet[Int] {
    override def apply(): Int = 3
  }

  import scala.concurrent.ExecutionContext.Implicits.global
  println(receive(Future {
    new P2PRequest
  }))
  println(receive(Future {
    new P2PResponse
  }))

  /**
    * Benefits
    * - Lifting works
    */

  trait MathLib {
    def add1(x: Int): Int = x + 1
    def add1(x: String): Int = x.toInt + 1
  }

  trait AddMagnet {
    def apply(): Int
  }

  def add1(magnet: AddMagnet): Int = magnet()

  implicit class AddInt(x: Int) extends AddMagnet {
    override def apply(): Int = x + 1
  }

  implicit class AddString(x: String) extends AddMagnet {
    override def apply(): Int = x.toInt + 1
  }

  val addFunctionFV = add1 _
  println(addFunctionFV(1))
  println(addFunctionFV("3"))

  /**
    * Drawbacks
    * - more verbose
    * - hard to read
    * - you can't name or place default arguments
    * - call by name doesn't work correctly
    */

  class Handler {
    def handle(s: => String): Unit = {
      println(s)
      println(s)
    }

    // other overloads
  }

  trait HandleMagnet {
    def apply(): Unit
  }

  def handle(magnet: HandleMagnet): Unit = magnet()

  implicit class StringHandle(s: => String) extends HandleMagnet {
    override def apply(): Unit = {
      println(s)
      println(s)
    }
  }

  def sideEffectMethod(): String = {
    println("Hello Scala")
    "magnet"
  }

  //  handle(sideEffectMethod())

  handle {
    println("Hello Scala")
    "magnet"
  }
}