package com.ckarthickit.b_async_await

import kotlinx.coroutines.*

fun main(){
    val start = System.currentTimeMillis()
    spawnMillionCoroutines()
    println("spawnMillionCoroutines took ${System.currentTimeMillis() - start} sec")
}


fun spawnMillionCoroutines() {
    val deferred: List<Deferred<Long>> = (1..1_000_000L).map { n: Long ->
        GlobalScope.async {
            workLoad(n)
        }
    }
    runBlocking {
        val sum = deferred.sumBy { it.await().toInt() }
        println("sum is $sum")
    }
}

suspend fun workLoad(n : Long): Long {
    delay(1000)
    return n
}