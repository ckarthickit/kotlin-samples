package com.ckarthickit.d_fan_out_in

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce

fun main() = runBlocking {
    println("==== fan-out ====")
    fanOutDemo()
    println("==== fan-in ====")
    fanInDemo()
}
/**********************************************************/
suspend fun CoroutineScope.fanOutDemo() {
    val producer = produceNumbers()
    repeat(5) {
        //Fan-Out
        launchConsumers(it, producer)
    }
    delay(950)
    producer.cancel()
}

suspend fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1
    while (true) {
        send(x++)
        delay(100)
    }
}

fun CoroutineScope.launchConsumers(id: Int, channel: ReceiveChannel<Int>) = launch {
    for(msg in channel) {
        println("Processor #$id received $msg")
    }
}
/**********************************************************/

suspend fun CoroutineScope.fanInDemo() {
    val channel = Channel<String>()
    launch { sendString(channel, "FOO", 400) }
    launch { sendString(channel, "Bar!", 300) }
    repeat(6) {
        println("Recieved ${channel.receive()}")
    }
    coroutineContext.cancelChildren()
}

suspend fun sendString(channel: SendChannel<String>, string: String, timeout: Long) {
    while (true) {
        channel.send(string)
        delay(timeout)
    }
}