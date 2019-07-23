package com.ckarthickit.c_cooperative_coroutine

import com.ckarthickit.getContext
import com.ckarthickit.printContext
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking(EmptyCoroutineContext + CoroutineName("runBlocking")) {
    val job = launchNonCancellableJob(this)
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting! ${getContext()}")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit. ${getContext()}")
}

fun launchNonCancellableJob(scope: CoroutineScope): Job {
    var startTime = System.currentTimeMillis()
    // Add Dispatchers.Default to the context
    return scope.launch(context = EmptyCoroutineContext + CoroutineName("launchNonCancellableJob") + Dispatchers.Default) {
        printContext()
        var nextPrimeTime = startTime
        var i = 0

        /**
         * The below loop cooperates for cancellation as,
         * it  checks CoroutineScope.isActive extension property.
         */
        while (isActive) { // 0..4
            if (System.currentTimeMillis() >= nextPrimeTime) {
                println("job: I'm sleeping $i ... ${getContext()}")

                i++ //incremented every half-a-second
                nextPrimeTime += 500L
            }else {
                try {
                    //yield()
                } catch (e: CancellationException) {
                    e.printStackTrace()
                    println("caught cancellation ${getContext()}")
                    throw e
                }
            }
        }

    }
}