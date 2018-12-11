package code4.every.day.scala99problems.lists

import org.scalatest.FlatSpec

class P02Tests extends FlatSpec with P02 {

  "List without elements " should "return nothing" in {
    val maybeValue = penultimate(List())

    assert(maybeValue.isEmpty)
  }

  "List with one element " should "return those element" in {
    val maybeValue = penultimate(List(1))

    assert(maybeValue.contains(1))
  }

  "List with several elements " should "return the last element" in {
    val maybeValue1 = penultimate(List(1, 2, 3, 4))
    assert(maybeValue1.contains(3))


    val maybeValue2 = penultimate(List(1, 1, 2, 3, 5, 8))
    assert(maybeValue2.contains(5))
  }

  "If null list provided, method " should "return nothing" in {
    val maybeValue = penultimate(null)

    assert(maybeValue.isEmpty)
  }

  "List with several uncomparable elements " should "return the last element" in {
    val maybeValue = penultimate(List(new T("First"), new T("Second"), new T("Last")))

    assert(maybeValue.map(t => t.name).contains("Second"))
  }

  "Null value in passed list " should "not break logic" in {

    val list = List(new T("First"), new T("Second"), new T("Third"), null)

    val maybeValue = penultimate(list)

    assert(maybeValue.map(t => t.name).contains("Third"))
  }

  "Null values in passed list " should "not break logic" in {

    val list = List(null, null, null, null, null, null)

    val maybeValue = penultimate(list)

    assert(maybeValue.contains(null))
  }

}
