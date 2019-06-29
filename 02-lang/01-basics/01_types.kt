package com.kar.demo;

fun main(args: Array<String>) {
    typesDemo()
}

/**
 * 
 */
fun typesDemo() {
    numericDemo()
}

fun numericDemo() {
    println("\n==autoboxing-demo==")
    autoBoxingDemo()
    println("\n==explicit-conversion-demo==")
    explicitConversionDemo()
    println("\n==bitwise-operation-demo==")
    bitWiseOperationDemo()
}

fun autoBoxingDemo() {
    val i: Int = 10000 //replace this with 10 and see the magic :P need to investigate
    println(i == i)

    val boxedI: Int? = i
    val anotherBoxedI : Int? = i
    println("boxedI == anotherBoxedI: ${boxedI == anotherBoxedI}")
    println("boxedI === anotherBoxedI: ${boxedI === anotherBoxedI}")
}

fun explicitConversionDemo() {
    val myInt: Int = 10;
    val myLong: Long = 10;
    //println("${myInt == myLong}"); // compilation error
    println("myInt.toLong() == myLong=> ${myInt.toLong() == myLong}")
}

fun bitWiseOperationDemo() {
    var x = (1 shl 2)
    println("(1 shl 2).toString(10)=> \"${x.toString(10)}\"")
    println("(1 shl 2).toString(2)=> \"${x.toString(2)}\"")
    println("(1 shl 2).toString(16)=> \"${x.toString(16)}\"")
    x = (x shl 2) - 1
    println("(1 shl 2) - 1.toString(10)=> \"${x.toString(10)}\"")
    println("(1 shl 2) - 1.toString(2)=> \"${x.toString(2)}\"")
    println("(4 shl 2) - 1.toString(16)=> \"${x.toString(16)}\"")
}