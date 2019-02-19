package lectures.part3concurrency

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Futures3 extends App {

  case class User(name: String)
  case class Transaction(sender: String, receiver: String, amount: Double, status: String)

  object BankingApp {
    val name = "Rock the JVM banking"

    def fetchUser(name: String): Future[User] = Future {
      // simulate fetching data from DB
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
      // simulate other process
      Thread.sleep(600)
      Transaction(user.name, merchantName, amount, "SUCCESS")
    }

    def purchase(userName: String, item: String, merchant: String, cost: Double): String = {
      // fetch the user from DB
      // create a transaction
      // WAIT for the transaction to finish

      val transactionStatusFuture = for {
        user <- fetchUser(userName)
        transaction <- createTransaction(user, merchant, cost)
      } yield transaction.status

      Await.result(transactionStatusFuture, 2.seconds) // implicits conversion -> pimp my library
//      Await.result(transactionStatusFuture, 1.seconds) // timeout example
    }
  }

  println(BankingApp.purchase("Almaz", "iPhone 12", "re:Store", 3000))

  // promises

}
