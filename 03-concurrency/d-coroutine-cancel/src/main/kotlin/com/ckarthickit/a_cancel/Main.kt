package com.ckarthickit.a_cancel

import com.ckarthickit.getContext
import com.ckarthickit.printContext
import kotlinx.coroutines.*
import java.io.BufferedOutputStream
import java.io.PrintStream
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking(EmptyCoroutineContext + CoroutineName("runBlocking")) {
    val job = launchJob(this)
    delay(1300L)
    job.cancel()
    job.join()
    println("Now i can quit ${getContext()}")
}

suspend fun launchJob(scope: CoroutineScope): Job {
    return scope.launch(context = EmptyCoroutineContext + CoroutineName("launchJob")) {

        repeat(1000) { i ->
            println("job: I'm sleeping $i ...${getContext()}")
            try {
                delay(500) //This CoroutineScope extension API throws CancellationException on job cancel
            } catch (e: CancellationException) {
                e.printStackTrace()
                println("caught cancellation  ${getContext()}")
                throw e //Commenting out this throw will cause delay to throw the exception all the time after cancel
            }//All Suspend functions in co-routine library are cancellable
        }

    }
}