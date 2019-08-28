package lectures.part3fp

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure {


  def main(args: Array[String]): Unit = {
    // create success and failure

    val aSuccess = Success(3)
    val aFailure = Failure(new RuntimeException("SUPER FAILURE"))

    def unSafeMethod(): String = throw new RuntimeException("NO STRING FOR YOU")
    def safeMethod(): String = "A valid result"

    println(aSuccess)
    println(aFailure)

    val potentialFailure = Try(unSafeMethod())
    println(potentialFailure)

    // syntax sugar
    val anotherPotentialFailure = Try {
      throw new RuntimeException("Oooooops!")
    }

    println(anotherPotentialFailure)

    println(anotherPotentialFailure.isSuccess)
    println(aSuccess.isSuccess)
    println(aSuccess.isFailure)

    val fallbackTry = Try(unSafeMethod()).orElse(Try(safeMethod()))
    println(fallbackTry.getOrElse("Ooooops!"))

    // If you design the API
    def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException())
    def betterSafeMethod(): Try[String] = Success("A valid result")
    val betterFallbackTry = betterUnsafeMethod orElse betterSafeMethod
    println(betterFallbackTry)

    // map, flatMap, filter
    println(aSuccess.map(_ * 2))
    println(aSuccess.flatMap(x => Success(x * 10)))
    println(aSuccess.filter(x => x > 10))

    // for-comprehensions
    val url = "index.html"
    val hostname = "localhost"
    val port = "8080"
    def renderHTML(page: String): Unit = println(page)

    class Connection {
      def get(url: String): String =
        if (new Random(System.nanoTime()).nextBoolean())
          "This is valid HTML page"
        else throw new RuntimeException("Some internal error")


      def getSafe(url: String): Try[String] = Try(get(url))
    }

    object HttpService {
      def getConnection(host: String, port: String): Connection =
        if (new Random(System.nanoTime()).nextBoolean()) new Connection
        else throw new RuntimeException("Unable to connect to the server")

      def getConnectionSafe(host: String, port: String): Try[Connection] = Try(getConnection(hostname, port))
    }

    Try(HttpService.getConnection(hostname, port))
      .map(c => Try(c.get(url)))
      .map(x => x.get)
      .foreach(renderHTML)

    HttpService.getConnectionSafe(hostname, port).flatMap(c => c.getSafe(url)).foreach(renderHTML)

    for {
      connection <- HttpService.getConnectionSafe(hostname, port)
      page <- connection.getSafe(url)
    } renderHTML("3 " + page)
  }
}
