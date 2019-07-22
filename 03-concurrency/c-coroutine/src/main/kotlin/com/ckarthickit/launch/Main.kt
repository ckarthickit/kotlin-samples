package com.ckarthickit.launch

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

fun main() {
    launchAJob()
    println("======")
    launchUsingCoroutinePrimitive()
    println("=== Sleeping before termination ===")
    Thread.sleep(5000)
}

fun launchAJob() {
    runBlocking(EmptyCoroutineContext + CoroutineName("run_blocking")) {
        println("Start (${coroutineContext[CoroutineName.KEY]}")
        val job = GlobalScope.launch( EmptyCoroutineContext + CoroutineName("global_launch")) {
            delay(500)
            println("Hello (${coroutineContext[CoroutineName.KEY]})")
        }
        joinAll(job)
        println("Stop (${coroutineContext[CoroutineName.KEY]}")
    }
    //control won't reach here until the co-routine completes
    println("launchAJob-method control won't reach here until runBlocking completes")
}

fun launchUsingCoroutinePrimitive() {
    runAsCoroutine(EmptyCoroutineContext + CoroutineName("runAsCoroutine")) {
        println("primitive_start (${coroutineContext[CoroutineName.KEY]}")
        customDelay(2000)
        println("primitive_stop (${coroutineContext[CoroutineName.KEY]})")
    }
}
