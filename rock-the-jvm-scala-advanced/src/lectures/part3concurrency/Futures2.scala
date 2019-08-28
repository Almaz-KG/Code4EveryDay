package lectures.part3concurrency

import scala.concurrent.Future
import scala.util.{Failure, Random, Success}
import scala.concurrent.ExecutionContext.Implicits.global


object Futures2 extends App {
  // mini social network
  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile): Unit = {
      println(s"${this.name} poking ${anotherProfile.name}")
    }
  }

  object SocialNetwork {
    // database
    val names = Map (
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.0-dummy" -> "Dummy"
    )

    val friends = Map (
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )

    val random = new Random()

    // API
    def fetchProfile(id: String): Future[Profile] = Future {
      // fetching from database
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchBestFriends(profile: Profile): Future[Profile] = Future {
      // fetching from database
      Thread.sleep(random.nextInt(400))
      val id2 = friends(profile.id)
      Profile(id2, names(id2))
    }


  }
  // client: mark to poke bill
  val zuck = SocialNetwork.fetchProfile("fb.id.1-zuck")
  zuck.onComplete {
    case Success(zuckProfile) => {
      val billProfile = SocialNetwork.fetchBestFriends(zuckProfile)
      billProfile.onComplete {
        case Success(bp) => zuckProfile.poke(bp)
        case Failure(e) => e.printStackTrace()
      }
    }
    case Failure(e) => e.printStackTrace()
  }

  // Functional composition of futures
  // map, flatMap, filter
  val nameOnWall = zuck.map(profile => profile.name)
  val zucksBestFriends = zuck.flatMap(profile => SocialNetwork.fetchBestFriends(profile))
  zuck.filter(/* SOME CONDITION HERE */ _ => true)

  for {
    zuck <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriends(zuck)
  } zuck.poke(bill)

  Thread.sleep(2000)

  val aUnknownProfile = SocialNetwork.fetchProfile("unknown id").recover {
    case _: Throwable => Profile("fb.id.0-dummy", "Forever alone")
  }

  val aFetchecProfile = SocialNetwork.fetchProfile("unknown id").recoverWith {
    case _: Throwable => SocialNetwork.fetchProfile("fb.id.0-dummy")
  }

  val fallbackResult = SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile("fb.id.0-dummy"))

}
