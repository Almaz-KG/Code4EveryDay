package exercises.part3concurrency

object JvmThreadLiveLock extends App {

  case class Friend(name: String){
    var side = "right"

    def switchSide(): Unit = {
      side = if (side == "right") "left" else "right"
    }

    def pass(other: Friend): Unit = {
      while(this.side == other.side) {
        println(s"$this: oh, but please, $other, feel free to pass...")
        switchSide()
        Thread.sleep(1000)
      }
    }
  }

  val sam = Friend("Sam")
  val pierre = Friend("Pierre")

  new Thread(() => sam.pass(pierre)).start()
  new Thread(() => pierre.pass(sam)).start()
}
