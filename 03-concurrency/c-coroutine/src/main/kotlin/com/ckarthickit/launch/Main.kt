package com.ckarthickit.launch

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

fun main() {
    launchAJob()
    println("======")
    launchAnotherJob()
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

fun launchAnotherJob() {
    runBlocking(EmptyCoroutineContext + CoroutineName("another_run_blocking")) {
        println("Start (${coroutineContext[CoroutineName.KEY]}")
        println("Scope is ${this::class}")
        this.launch( EmptyCoroutineContext + CoroutineName("run_blocking_launch")) {
            delay(500)
            println("Hello (${coroutineContext[CoroutineName.KEY]})")
        }
        println("Stop (${coroutineContext[CoroutineName.KEY]}")
    }
    //control won't reach here until the co-routine completes
    println("launchAnotherJob-method control won't reach here until runBlocking completes")
}

fun launchUsingCoroutinePrimitive() {
    runAsCoroutine(EmptyCoroutineContext + CoroutineName("runAsCoroutine")) {
        println("primitive_start (${coroutineContext[CoroutineName.KEY]})")
        println("suspend_in_thread = ${Thread.currentThread().id} (${coroutineContext[CoroutineName.KEY]})")
        customDelay(2000)
        println("resume_in_thread ${Thread.currentThread().id} (${coroutineContext[CoroutineName.KEY]})")
        println("primitive_stop (${coroutineContext[CoroutineName.KEY]})")
    }
}
