import kotlinx.coroutines.*

fun main(args: Array<String>) {
    GlobalScope.launch { //launch a new coroutine in background
        delay(1000L) // non-blocking delay of 1 second
        println("World!")
    }
    println("Hello, ")
    Thread.sleep(2000) //block main thread for 2 seconds.
}