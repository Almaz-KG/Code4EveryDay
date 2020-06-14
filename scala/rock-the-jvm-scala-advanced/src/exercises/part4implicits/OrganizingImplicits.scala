package exercises.part4implicits

object OrganizingImplicits extends App {
  /*
      Exercise

      Write the ordering methods for those purposes
        - totalPrice = most used
        - by unit count
        - by unit price
   */
  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((f, s) => (f.nUnits * f.unitPrice) < (s.nUnits * s.unitPrice))
  }

  object UnitCountOrdering {
    implicit val byUnitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan((f, s) => f.nUnits > s.nUnits)
  }

  object UnitPriceOrdering {
    implicit val byUnitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((f, s) => f.unitPrice > s.unitPrice)
  }

  val purchases = List(Purchase(20, 0.5), Purchase(1, 1), Purchase(8, 103), Purchase(2, 7), Purchase(16, 20), Purchase(7, 80))
//  import UnitPriceOrdering._
  import UnitCountOrdering._
  println(purchases.sorted)
}
