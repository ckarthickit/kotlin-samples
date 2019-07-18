val stringPlus: (String, String) -> String = String::plus
val stringPlusReciever : (String).(String) -> String = String::plus

val sumLambda: Int.(Int) -> Int = { other -> this.plus(other) } //function literal with reciever
val sum = fun Int.(other: Int): Int = this + other //anonymous function syntax

fun main() {
    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("Hello, ", "world!")) 
    println("Hello, ".stringPlusReciever("world!")) 
    println("5.sumLambda(3) is ${5.sumLambda(3)}")
    println("4.sum(3) is ${4.sum(3)}")
}