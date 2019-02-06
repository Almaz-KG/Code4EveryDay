package lectures.part3fp

object TuplesAndMaps {

  def main(args: Array[String]): Unit = {
    // tuples - finite ordered "lists"
    //    val aTuple = new Tuple2(2, "Hello, Scala") // Tuple[Int, String] = (Int, String)
    //    val aTuple = Tuple2(2, "Hello, Scala") // Tuple[Int, String] = (Int, String)
    val aTuple2 = (2, "Hello Scala") // Tuple[Int, String] = (Int, String)

    println(aTuple2._1)
    println(aTuple2._2)

    println(aTuple2.copy(_2 = "Goodbye, Java"))
    println(aTuple2.swap)


//  Maps
//    val aMap: Map[String, Int] = Map(("London", 9), ("Moscow", 14), ("New York", 7))
    val aMap: Map[String, Int] = Map("London" -> 9, "Moscow" -> 14, "New York" -> 7)
    println(aMap)

    println(aMap.contains("London"))
    println(aMap("London"))
//    println(aMap("WRONG KEY"))

    val mapWithDefault: Map[String, Int] = Map("London" -> 9, "Moscow" -> 14, "New York" -> 7).withDefaultValue(-1)
    println(mapWithDefault("WRONG KEY"))

//  add a pairing
    val newPairing = "Paris" -> 8
    val newMap = aMap + newPairing
    println(newMap)

//  functions - map/flatmap/filter
    println(newMap.map(pair => pair._1.toLowerCase -> pair._2))
    println(newMap.filterKeys(key => key.startsWith("M")))
//  mapValues
    println(newMap.mapValues(number => "Population is " + number + " citizens"))

//  conversions to other collections
    println(newMap.toList)
    println(newMap.toVector)
    println(newMap.toSet)
    println(List(("London", 9), ("Moscow", 14)).toMap)

    val names = List("Moscow", "New York", "Madrid", "Barcelona", "Buenos Aires")
    println(names.groupBy(x => x.charAt(0)))

    val cities = Map("Moscow" -> ("Russia", 13), "Saratov" -> ("Russia", 1), "Novosibirsk" -> ("Russia", 4),
                     "New York" -> ("USA", 8), "San Diego" -> ("USA", 4), "Washington" -> ("USA", 7),
                     "Madrid" -> ("Spain", 5), "Barcelona" -> ("Spain", 3))

    val population: Map[String, (String, Int)] => Int = map => map.toList.map { case (_, (_, pop)) => pop }.sum
    val groupedByCountry = cities.groupBy { case (_, (country, _)) => country }
    val populationByCountry = groupedByCountry.map { case (country, map) => (country, population(map))}

    println(populationByCountry)
  }
}
