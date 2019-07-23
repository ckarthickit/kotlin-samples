package com.ckarthickit.d_finally_on_cancel

import com.ckarthickit.getContext
import com.ckarthickit.printContext
import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launchAJob(scope = this)
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting! ${getContext()}")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.${getContext()}")
}

suspend fun launchAJob(scope: CoroutineScope): Job {
    return scope.launch(context = CoroutineName("launchAJob")) {
        try{
            repeat(1000) {
                println("job: I'm sleeping $it ...${getContext()}")
                delay(500)
            }
        }finally {
            println("I am running finally ${getContext()}")
        }
    }
}