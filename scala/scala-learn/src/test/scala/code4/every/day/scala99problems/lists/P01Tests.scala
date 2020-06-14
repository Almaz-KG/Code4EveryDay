package code4.every.day.scala99problems.lists

import org.scalatest.FlatSpec

class P01Tests extends FlatSpec with P01 {

  "List without elements " should "return nothing" in {
    val maybeValue = last(List())

    assert(maybeValue.isEmpty)
  }

  "List with one element " should "return those element" in {
    val maybeValue = last(List(1))

    assert(maybeValue.contains(1))
  }

  "List with several elements " should "return the penultimate element" in {
    val maybeValue = last(List(1, 2, 3, 4))

    assert(maybeValue.contains(4))
  }

  "If null list provided, method " should "return nothing" in {
    val maybeValue = last(null)

    assert(maybeValue.isEmpty)
  }

  "List with several uncomparable elements " should "return the penultimate element" in {
    val maybeValue = last(List(new T("First"), new T("Second"), new T("Last")))

    assert(maybeValue.map(t => t.name).contains("Last"))
  }
}