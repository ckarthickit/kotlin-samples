package com.ckarthickit.e_non_cancelable_context

import com.ckarthickit.getContext
import kotlinx.coroutines.*

fun main() = runBlocking(CoroutineName("runBlocking")) {
    val job = launchAJob(this)
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting! ${getContext()}")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit. ${getContext()}")
}

suspend fun launchAJob(scope: CoroutineScope): Job {
    return scope.launch(CoroutineName("launchAJob")) {
        try {
            repeat(1000) { index ->
                println("job: I'm sleeping $index ... ${getContext()}")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally ${getContext()}")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable ${getContext()}")
            }
        }
    }
}