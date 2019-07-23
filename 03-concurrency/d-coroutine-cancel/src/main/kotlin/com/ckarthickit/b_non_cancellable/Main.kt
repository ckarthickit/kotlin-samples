package com.ckarthickit.b_non_cancellable

import com.ckarthickit.getContext
import com.ckarthickit.printContext
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

// Note : When delay in main() method suspends it's co-routine,
// the other co-routine (launchNonCancellableJob) enters the Main thread and executes.
fun main() = runBlocking(context = EmptyCoroutineContext + CoroutineName("runBlocking")) {
    val job = launchNonCancellableJob(this)
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting! ${getContext()}")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit. ${getContext()}")
}

fun launchNonCancellableJob(scope: CoroutineScope): Job {
    var startTime = System.currentTimeMillis()
    // Removing Dispatchers.Default from the context will provide the same result,
    // but cancelAndJoin would not have been executed at all before the "launchNonCancellableJob" completes it's loop
    // as they both run in same thread and the main has already yielded it's control by invoking delay()
    return scope.launch(EmptyCoroutineContext + CoroutineName("launchNonCancellableJob") + Dispatchers.Default) {
        var nextPrimeTime = startTime
        var i = 0

        /**
         * The below loop doesn't cooperate for cancellation as,
         * it neither checks CoroutineScope.isActive extension property
         * nor calls one of "kotlinx.coroutines" suspend methods.
         */
        while (i < 5) { // 0..4
            if (System.currentTimeMillis() >= nextPrimeTime) {
                println("job: I'm sleeping $i ...${getContext()}")

                i++ //incremented every half-a-second
                nextPrimeTime += 500L
            }else {
                try {
                    //yield()
                } catch (e: CancellationException) {
                    e.printStackTrace()
                    printContext()
                    throw e
                }
            }
        }

    }
}
