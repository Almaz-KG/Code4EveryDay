package exercises.part2

object OOPBasicsExercise1 extends App {
/*
    Writer
      - first name
      - surname
      - year of birth
      + fullName(): String
    Novel
      - name
      - year of release
      - author
      + copy(new release year) : Novel
      + authorAge(): Int
      + isWrittenBy(author): Boolean
   */


  /*
    Counter class
      - receives and int value
      + currentValue(): Int
      + increment(): Counter
      + decrement(): Counter
      + overload increment / decrement to receive an amount
   */
  val author = new Writer("Charles", "Dickens", 1812)
  val imposter = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great expectations", 1861, author)

  println(author.fullName())
  println(novel.authorAge)
  println(novel.isWrittenBy(author))
  println(novel.isWrittenBy(imposter))
  println(novel.copy(2018).yearOfRelease)


  val counter = new Counter()
  println(counter
    .increment()
    .increment()
    .increment(3)
    .currentValue)



}

class Writer(val firstName: String, val surName: String, val yearOfBirth: Int) {

  def fullName(): String = s"$firstName $surName"

}


class Novel(val name: String, val yearOfRelease: Int, val author: Writer) {

  def copy(newReleaseYear: Int): Novel = new Novel(this.name, newReleaseYear, this.author)

  def authorAge: Int = this.yearOfRelease - author.yearOfBirth

  def isWrittenBy(author: Writer): Boolean = this.author == author

}


class Counter(val currentValue: Int = 0) {

  def increment(): Counter = new Counter(currentValue + 1)

  def decrement(): Counter = new Counter(currentValue - 1)

  def increment(step: Int): Counter = new Counter(currentValue + step)

  def decrement(step: Int): Counter = new Counter(currentValue - step)

}









