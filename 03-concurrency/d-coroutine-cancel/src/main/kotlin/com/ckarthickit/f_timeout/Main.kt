package com.ckarthickit.f_timeout

import com.ckarthickit.getContext
import kotlinx.coroutines.*

fun main() = runBlocking(CoroutineName("runBlocking")) {
    try {
        withTimeout(1300L) {
            repeat(1000) { index ->
                println("I'm sleeping $index ... ${getContext()}")
                delay(500L)
            }
            "Done"
        }
    }catch (e: TimeoutCancellationException) {
        e.printStackTrace()
    }
    println("I'm done ${getContext()}")
}