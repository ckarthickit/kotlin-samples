class RandomClass

fun main() {
    val aList = listOf("a","b")
    println("listOf underlying class is ${aList::class}")
    
    val aMutableList = mutableListOf("a","b")
    println("mutableListOf underlying class is ${aMutableList::class}")

    val aSet = setOf("a","a")
    println("setOf underlying class is ${aSet::class}")

    val aMutableSet = mutableSetOf("a","a")
    println("setOf underlying class is ${aMutableSet::class}")

    //infix fun <A, B> A.to(that: B): Pair<A, B> -> a generic function is available
    val aMap = mapOf(RandomClass() to 1, RandomClass() to 2)
    println("mapOf underlying class is ${aMap::class}")

    var aMutableMap = mutableMapOf(1 to "1", 2 to "2")
    println("mutableMapOf underlying class is ${aMutableMap::class}")
}