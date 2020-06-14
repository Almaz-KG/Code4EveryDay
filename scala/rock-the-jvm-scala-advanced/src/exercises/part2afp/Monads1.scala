package exercises.part2afp


/*
  EXERCISE:

  1. Implement a Lazy[T] monad = computation which will only be executed when it's needed
      - unit/apply
      - flatMap
 */
object Monads1 extends App {

  class Lazy[+A](value: => A) {

    private lazy val _value = value
    def use: A = _value

    def flatMap[B](f: (=>A) => Lazy[B]): Lazy[B] = f(_value)
  }

  object Lazy {
    def apply[A](value: => A): Lazy[A] = new Lazy(value)
  }

  val lazyInstance = Lazy {
    println("Today I don't feel like doing anything")
    42
  }

  println(lazyInstance)
  val flatMappedInstance1 = lazyInstance.flatMap(x => Lazy { x * 10})
  println(flatMappedInstance1)

  val flatMappedInstance2 = lazyInstance.flatMap(x => Lazy { x * 10})
  flatMappedInstance1.use
  flatMappedInstance2.use

  /*
      Left-identity
        unit.flatMap(f) = f(v)
        Lazy(v).flatMap(f) = f(v)
   */
  val leftIdentityChecks = Lazy(0)
  println(leftIdentityChecks.flatMap(x => Lazy(x + 1)).use, 0 + 1)
  /*
      Right-identity
        v.flatMap(unit) = v
        Lazy(v).flatMap(x => Lazy(x)) = Lazy(v)
   */
  val rightIdentityChecks = Lazy(0)
  println(rightIdentityChecks.flatMap(x => Lazy(x + 1)).use, 0 + 1)

  /*
      Associativity
        Lazy(v).flatMap(f).flatMap(g) = f(v).flatMap(g)
   */
  val associativityChecks = Lazy(0)
  val anAssociativityChecksResult = associativityChecks.flatMap(x => Lazy(x + 1)).flatMap(y => Lazy(y + 2)) // 3
  println(anAssociativityChecksResult.use, 0 + 1 + 2)
}
