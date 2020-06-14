package lectures.part3concurrency

object JvmConcurrencyProblems extends App {

  def runInParallel = {
    var x = 0
    val thread1 = new Thread(() => { x = 1})
    val thread2 = new Thread(() => { x = 2})

    thread1.start()
    thread2.start()

    print(x + " ")
  }

  for (_ <- 1 to 10000) runInParallel
  println()

  // rice condition
  class BankAccount(@volatile var amount: Int){
    override def toString: String = " " + amount
  }

  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
//    println(s"I have bought $thing")
//    println(s"My account is now $account")
  }

  for (_ <- 1 to 1000){
    val account = new BankAccount(50000)

    val thread1 = new Thread(() => buySafe(account, "shoes", 3000))
    val thread2 = new Thread(() => buySafe(account, "iPhone12", 4000))

    thread1.start()
    thread2.start()

    Thread.sleep(20)

    if (account.amount != 43000)
      println("Aha, got it: " + account)
  }
  println("Completed")

  // Option #1: use synchronized
  def buySafe(account: BankAccount, thing: String, price: Int): Unit = {
    account.synchronized {
      // no two threads can evaluate this at the same time
      account.amount -= price
    }
  }

  // Option #2: use @volatile


}
