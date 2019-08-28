package lectures.part3fp

import scala.util.Random

object Options {

  def main(args: Array[String]): Unit = {
    val myFirstOption: Option[Int] = Some(4)

    println(myFirstOption)

    // unsafe methods
    def unsafeMethod(): String = null
//    val result = Some(unsafeMethod()) // WRONG
    val result = Option(unsafeMethod()) // Some or None

    println(result)

    // chained methods
    def backupMethod(): String = "A valid result"
    val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
    println(chainedResult)

    
    // DESIGN unsafe APIs
    def betterUnsafeMethod: Option[String] = None
    def betterBackupMethod: Option[String] = Some("A valid result")

    val betterChainedResult = betterUnsafeMethod orElse betterBackupMethod
    println(betterChainedResult)

    // Functions on options
    println(myFirstOption.isEmpty)
    println(myFirstOption.isDefined)
    println(myFirstOption.get) // UNSAFE - DO NOT USE THIS

    // Map, flatMap, filter
    println(myFirstOption.map (_ * 2))
    println(myFirstOption.filter (_  > 10))
    println(myFirstOption.flatMap(x => Option(x * 10)))

    // for-comprehensions

    /*
      Exercise
     */
    val config: Map[String, String] = Map(
      "host" -> "123.123.1.1",
      "port" -> "8080"
    )

    class Connection {
      def connect = "Connected"
    }

    object Connection {
      val random = new Random(System.nanoTime())

      def apply(host: String, port: String): Option[Connection] = if (random.nextBoolean()) Some(new Connection()) else None
    }

    // try to establish a connection, if so - call the connect method
    val host = config.get("host")
    val port = config.get("port")

    val connection = host.flatMap(h => port.flatMap(p => Connection(h, p)))
    val connectionStatus = connection.map(c => c.connect) getOrElse "Not connected"
    println(connectionStatus)

    // chained calls
    val connectionStatus1 = config.get("host")
      .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(c => c.connect)) getOrElse "Not connected"

    println(connectionStatus1)

    val connectionStatus2 = (for {
      host <- config.get("host")
      port <- config.get("port")
      maybeConnection <- Connection(host, port)
    } yield maybeConnection.connect ) getOrElse "Not connected"

    println(connectionStatus2)
  }
}
