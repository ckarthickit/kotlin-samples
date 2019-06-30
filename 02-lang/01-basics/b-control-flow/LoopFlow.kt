
fun main(args: Array<String>) {
  println("\n=== loop-test===")
  loopTest()
}

fun loopTest(): kotlin.Unit {
  iteratorDemo()
  println("==============")
}

fun iteratorDemo() {
  var iterator1: IntRange? = 1..10 // belongs to IntRange which is an IntProgression which is an Iterable
  println("iterator1 is IntRange = ${iterator1 is IntRange}")
  var iterator2: IntProgression? = 6 downTo 0 step 2 //IntProgression which is an Iterable
  println("iterator2 is IntProgression = ${iterator2 is IntProgression}")
  var iterator3: Array<Int>? = Array(5){ it -> it}
  println("iterator3 is Array<*> = ${iterator3 is Array<*>}")
  println("iterator3.indices is IntRange = ${iterator3?.indices is IntRange}")
  var iterator4: Iterable<IndexedValue<Int>>? = iterator3?.withIndex()
  println("iterator4 is Iterable<*> = ${iterator4 is Iterable<*>}")
}
