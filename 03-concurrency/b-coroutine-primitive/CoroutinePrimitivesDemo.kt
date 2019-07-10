import kotlin.coroutines.*
import kotlin.concurrent.*

fun main(args: Array<String>) {
    exec1 {
        exec2()
    }
    Thread.sleep(2000)
}

fun exec1(block : suspend () -> Unit) {
    val ret = block.startCoroutine(object : Continuation<Unit>{
		override val context= EmptyCoroutineContext
		fun Continuation<Unit>.resume(value: Unit) = println("exec1 resume")
		fun Continuation<Unit>.resumeWithException(exception: Throwable) = println("exec1 resumeWithException $exception")
        override fun resumeWith(result: Result<Unit>): Unit = Unit
	})
    println("rer class is ${ret::class}")
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