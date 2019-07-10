import kotlin.coroutines.*
import kotlin.concurrent.*

fun main(args: Array<String>) {
    exec1 {
        println("========")
        exec2()
    }
    exec1 {
        println("========")
        exec3()
    }
}

fun exec1(block : suspend () -> Unit) {
    val ret = block.startCoroutine(object : Continuation<Unit>{
		override val context= EmptyCoroutineContext
		// fun Continuation<Unit>.resume(value: Unit) = println("exec1 resume")
		// fun Continuation<Unit>.resumeWithException(exception: Throwable) = println("exec1 resumeWithException $exception")
        override fun resumeWith(result: Result<Unit>): Unit =  println("exec1 resumedWith ${result.exceptionOrNull()}")
	})
    println("ret class is ${ret::class}")
}

suspend fun exec2(): Unit = suspendCoroutine { cont->
    println("exec2 started")
    thread {
        println("exec2 canceled")
        cont.resumeWithException(Exception("oops"))
    }
    Thread.sleep(1000)
    println("exec2 finished")
//  cont.resume(Unit)
}

suspend fun exec3() {
    println("exec3 started")
    exec3Cancel()
    Thread.sleep(1000)
    println("exec3 finished")
}

suspend fun exec3Cancel() = suspendCoroutine<Unit> { cont ->
    thread {
        val isAbnormalExit = true
        //The below code can easily be return in if-else as well.
        //But chose to use when for the sake of demonstrating how powerful "when" is .
        when {
            isAbnormalExit -> {
                println("exec3 canceled")
                cont.resumeWithException(Exception("oops"))
            }
            !isAbnormalExit -> {
                cont.resume(Unit)
            }
        }
    }
}