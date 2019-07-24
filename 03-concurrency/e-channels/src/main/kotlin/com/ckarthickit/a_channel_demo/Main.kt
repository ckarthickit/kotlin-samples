package com.ckarthickit.a_channel_demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    produceAndConsumeUsingReceive()
    produceAndConsumeUsingIterator()
}

suspend fun CoroutineScope.produceAndConsumeUsingReceive() {
    val channel: Channel<Int> = Channel()
    println("-----produceAndConsumeUsingReceive-----")
    produceValues(channel)
    consumeValuesUsingReceive(channel)

}

suspend fun CoroutineScope.produceAndConsumeUsingIterator() {
    println("-----produceAndConsumeUsingIterator-----")
    val channel: Channel<Int> = Channel()
    produceValues(channel)
    consumeValuesUsingIterator(channel)
}

suspend fun CoroutineScope.produceValues(channel: Channel<Int>) {
    launch {
        for (x in 1..5) {
            println("sending.. $x")
            channel.send(x)
        }
        channel.close()
    }
}

suspend fun consumeValuesUsingReceive(channel: Channel<Int>) {
    do {
        try {
            val value = channel.receive()
            println("Receive: value is $value")
        } catch (e: ClosedReceiveChannelException) {
            e.printStackTrace()
            println("Caught Close")
            break
        }
    } while (true)
//    }while (!channel.isClosedForReceive)
}

suspend fun consumeValuesUsingIterator(channel: Channel<Int>) {
    for(value in channel) {
        println("Iterate value is $value")
    }
}