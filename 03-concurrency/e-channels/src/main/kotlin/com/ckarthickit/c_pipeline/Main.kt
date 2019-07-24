package com.ckarthickit.c_pipeline

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.ContinuationInterceptor

fun main()= runBlocking {
    pipeLineOfNumbersToSquares()
    println("==============")
    pipeLineOfNumbersToPrimes()
    println("==============")
    pipeLineOfNumbersToPrimesUsingIterator()
}

/**********************************************************/
suspend fun pipeLineOfNumbersToSquares() {
    try {
        coroutineScope {
            val intStream = produceLong()
            val squareStream = produceSquares(intStream)

            withTimeout(600L) {
                squareStream.consumeEach { num ->
                    println("num is $num")
                }
            }
        }
    }catch (e: TimeoutCancellationException) {
        e.printStackTrace()
    }
}

fun CoroutineScope.produceLong() = produce<Long> {
    println("context executor is ${coroutineContext[ContinuationInterceptor.Key]!!::class}")
    var x = 1L;
    while(true) {
        send(x++)
        delay(20)
    }
}

fun CoroutineScope.produceSquares(numbers: ReceiveChannel<Long>) = produce<Long> {
    println("context executor is ${coroutineContext[ContinuationInterceptor.Key]!!::class}")
    for(x in numbers) send(x * x)
}
/**********************************************************/

suspend fun CoroutineScope.pipeLineOfNumbersToPrimes() {
    var cur = numbersFrom(2)
    var isSwapped = false
    for (i in 1..10) {
        val afterPrime = cur.receive()
        //println("recieved $afterPrime from $cur")
        println(afterPrime)

        if(!isSwapped) {
            //launch a new coroutine for each prime number
            cur = filterPrimeNumbersFrom(cur, afterPrime)
            //uncommenting the below line will get the same result but from the same co-routine rather than the newly launched
            //co-routines
            //isSwapped = true
        }
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish
}


fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) send(x++) // infinite stream of integers from start
}

fun CoroutineScope.filterPrimeNumbersFrom(numbers: ReceiveChannel<Int>, afterPrime: Int) = produce<Int> {
    for (x in numbers) {
        if (x % afterPrime != 0) send(x)
    }
}

/**********************************************************/

fun CoroutineScope.pipeLineOfNumbersToPrimesUsingIterator() {
    var cur = iterateNumFrom(2)
    var isSwapped = false
    for (i in 1..10) {
        val afterPrime = cur.next()
        //println("recieved $afterPrime from $cur")
        println(afterPrime)

        if(!isSwapped) {
            //launch a new coroutine for each prime number
            cur = iteratePrimeNumbersFrom(cur, afterPrime)
            //uncommenting the below line will get the same result but from the same co-routine rather than the newly launched
            //co-routines
            //isSwapped = true
        }
    }
    coroutineContext.cancelChildren() // cancel all chil
}

fun iterateNumFrom(start: Int) = iterator {
    var x = start
    while (true) yield(x++) // infinite stream of integers from start
}

fun iteratePrimeNumbersFrom(numbers: Iterator<Int>, afterPrime: Int) = iterator {
    for (x in numbers) {
        if (x % afterPrime != 0) yield(x)
    }
}