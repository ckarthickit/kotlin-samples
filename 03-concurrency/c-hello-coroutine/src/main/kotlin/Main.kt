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
    runBlocking(EmptyCoroutineContext + CoroutineName("Launch")) {
        println("Start (${coroutineContext[CoroutineName.KEY]}")
        val job = GlobalScope.launch( EmptyCoroutineContext + CoroutineName("global_launch")) {
            delay(500)
            println("Hello (${coroutineContext[CoroutineName.KEY]})")
        }
        joinAll(job)
        println("Stop (${coroutineContext[CoroutineName.KEY]}")
    }
}

fun launchUsingCoroutinePrimitive() {
    runAsCoroutine {
        println("primitive_start (${coroutineContext[CoroutineName.KEY]}")
        customDelay(2000)
        println("primitive_stop (${coroutineContext[CoroutineName.KEY]})")
    }
}
