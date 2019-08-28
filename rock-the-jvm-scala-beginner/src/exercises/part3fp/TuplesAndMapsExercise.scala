package exercises.part3fp

import scala.annotation.tailrec


object TuplesAndMapsExercise {

  def main(args: Array[String]): Unit = {
    /**
      * 1. What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900 and run these code bellow
      *     mymap.map(pair => pair._1.toLowerCase -> pair._2))
      *
      * 2. Overly simplified social network based on maps
      *     Person = String
      *     - add a person to the network
      *     - remove a person
      *     - friend (mutual)
      *     - unfriend
      *
      *     - number of friends of a person
      *     - person with most friends
      *     - how many people have NO friends
      *     - if there is a social connection between two people (direct or not)
      */

    val myMap = Map("Jim" -> 900, "JIM" -> 555)
    println(myMap.map(pair => pair._1.toLowerCase -> pair._2))

    var facebook = SocialNetworkOnMaps()
    facebook = facebook.add("Almaz")
    facebook = facebook.add("Zhenish")
    facebook = facebook.add("Akiles")
    facebook = facebook.add("Sanam")
    facebook = facebook.add("Muktar")
    facebook = facebook.add("Ololosha")
    facebook = facebook.friend("Almaz", "Akiles")
    facebook = facebook.friend("Almaz", "Zhenish")
    facebook = facebook.friend("Almaz", "Sanam")
    facebook = facebook.friend("Zhenish", "Akiles")
    facebook = facebook.friend("Zhenish", "Muktar")
    facebook = facebook.friend("Zhenish", "Almaz")
    facebook = facebook.friend("Zhenish", "Sanam")
    facebook = facebook.unFriend("Almaz", "Akiles")
    facebook = facebook.friend("Zhenish", "Muktar")
    facebook = facebook.friend("Sanam", "Almaz")
    facebook = facebook.friend("Akiles", "Muktar")
    facebook = facebook.friend("Muktar", "Almaz")

    facebook = facebook.add("AAAAA")
    facebook = facebook.friend("Muktar", "AAAAA")
    facebook = facebook.friend("AAAAA", "Almaz")
    facebook = facebook.remove("AAAAA")

    println("Social network: " + facebook)
    println("Almaz's friends: " + facebook.friends("Almaz").mkString(", "))
    println(s"Ololosha has ${facebook.friendsCount("Ololosha")} friends")
    println(s"Alikes has ${facebook.friendsCount("Akiles")} friends")
    println(s"Sanam has ${facebook.friendsCount("Sanam")} friends")
    println(s"The friendliest person on social network is ${facebook.theFriendliestPerson._1} with ${facebook.theFriendliestPerson._2} friends")
    println(s"Persons with NO friends ${facebook.personWithNoFriends} ")
    println(s"Connection from Sanam to Muktar ${facebook.socialConnection("Sanam", "Muktar")} ")
    println(s"Connection from Almaz to Muktar ${facebook.socialConnection("Almaz", "Muktar")} ")
    println(s"Connection from Akiles to Sanam ${facebook.socialConnection("Akiles", "Sanam")} ")
    println(s"Connection from Sanam to Ololosha ${facebook.socialConnection("Sanam", "Ololosha")} ")
  }

  case class SocialNetworkOnSets(persons: Set[String] = Set(), network: Set[(String, String)] = Set()) {

    def add(name: String): SocialNetworkOnSets = SocialNetworkOnSets(persons + name,  this.network)

    def remove(name: String): SocialNetworkOnSets = {
      val newPersons = persons - name
      val newNetwork = network.filter {case (person, friend) => person == name || friend == name }
      SocialNetworkOnSets(newPersons, newNetwork )
    }

    def friend(name: String, friend: String): SocialNetworkOnSets = {
      checkExists(name)
      checkExists(friend)

      SocialNetworkOnSets(persons, network + (name -> friend))
    }

    def unFriend(name: String, friend: String): SocialNetworkOnSets = {
      SocialNetworkOnSets(persons, network - (name -> friend))
    }

    def friends(name: String): Set[String] = {
      checkExists(name)

      network.filter { case (_name, _) => _name == name}.map { case (_, friend) => friend}
    }

    def friendsCount(name: String): Int = friends(name).size

    def theFriendliestPerson: (String, Int) = {
      persons.map(p => (p, friendsCount(p))).maxBy { case (_, friends) => friends }
    }

    def personWithNoFriends: Set[String] = {
      persons
        .map(p => (p, friendsCount(p)))
        .filter { case (_, friendsCount) =>  friendsCount == 0 }
        .map { case (name, _) => name }
    }

    def socialConnection(start: String, finish: String): Option[Set[String]] = {
      def directionHelper(head: String, goal: String, path: Set[String], graph: Map[String, Boolean]): Option[Set[String]] = {
        if (!graph.exists{ case (_, status) => status})
          None
        else if (network.contains((head, goal)))
          Some(path + goal)
        else {
          val allFriends = friends(head).filter(name => graph.contains(name) && !graph(name))

          for (newHead <- allFriends) {
            val newPath = path + newHead
            val newGraph = graph + (newHead -> true)

            val result = directionHelper(newHead, goal, newPath, newGraph)

            if (result.isDefined)
              return result
          }
          None
        }
      }

      checkExists(start)
      checkExists(finish)

      directionHelper(start, finish, Set(start), persons.map(p => (p, false)).toMap + (start -> true))
    }

    private def checkExists(name: String): Unit = {
      if (!persons.contains(name))
        throw new IllegalArgumentException(s"Unable find $name")
    }
  }

  case class SocialNetworkOnMaps(network: Map[String, Set[String]] = Map()) {

    def add(name: String): SocialNetworkOnMaps = {
      if (!network.contains(name))
        SocialNetworkOnMaps(network + (name -> Set()))
      else
        this
    }

    def remove(name: String): SocialNetworkOnMaps = {
      checkExists(name)

      SocialNetworkOnMaps(network.filter{ case (_name, _) => _name != name }.mapValues(l => l.filter(n => n != name)))
    }

    def friend(name: String, friend: String): SocialNetworkOnMaps = {
      checkExists(name)
      checkExists(friend)
      val newFriends = network(name) + friend
      SocialNetworkOnMaps(network + (name -> newFriends))
    }

    def unFriend(name: String, friend: String): SocialNetworkOnMaps = {
      checkExists(name)
      checkExists(friend)

      val newFriends = network(name) - friend
      SocialNetworkOnMaps(network + (name -> newFriends))
    }

    def friends(name: String): Set[String] = {
      checkExists(name)
      network(name)
    }

    def friendsCount(name: String): Int = friends(name).size

    def theFriendliestPerson: (String, Int) = {
      val (name, friends) = network.maxBy { case (_, frs) => frs.size }

      (name, friends.size)
    }

    def personWithNoFriends: Set[String] = {
      network.filter { case (_, friends) => friends.isEmpty}.map {case (name, _) => name}.toSet
    }

    def socialConnection(start: String, target: String): Option[Set[String]] = {
//      @tailrec
//      def bfs(target: String, consideredPersons: Set[String], discoveredPersons: Set[String]): Boolean = {
//        if (discoveredPersons.isEmpty) false
//        else {
//          val person = discoveredPersons.head
//
//          if (person == target) true
//          else if (consideredPersons.contains(person)) bfs(target, consideredPersons, discoveredPersons.tail)
//          else bfs(target, consideredPersons + person, discoveredPersons.tail ++ network(person))
//        }
//      }
//      bfs(target, Set(), network(start) + start)


      @tailrec
      def directionHelper(goal: String, path: Set[String], graph: Map[String, Boolean]): Option[Set[String]] = {
        if (!graph.exists { case (_, status) => status})
          None
        else {
          val friends = network(path.last).filter(fr => !graph.contains(fr) || !graph(fr))
          if (friends.isEmpty)
            None
          else {
            val newHead = friends.head
            if (network(newHead).contains(goal))
              Some(path + newHead + goal)
            else {
              val newGraph = friends.tail.map(p => (p, false)).toMap ++ graph + (newHead -> true)
              directionHelper(goal, path + newHead, newGraph)
            }
          }
        }
      }

      checkExists(start)
      checkExists(target)

      val graph = network(start).map(p => (p, false)).toMap + (start -> true)
      directionHelper(target, Set(start), graph)
    }

    private def checkExists(name: String): Unit = {
      if (!network.contains(name))
        throw new IllegalArgumentException(s"Unable find $name")
    }
  }
}
