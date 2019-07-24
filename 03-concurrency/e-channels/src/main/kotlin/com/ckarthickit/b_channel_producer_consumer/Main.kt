package com.ckarthickit.b_channel_producer_consumer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val channel = produceSquares()
    channel.consumeEach {  }
    async {

    }
}

suspend fun CoroutineScope.produceSquares(): ReceiveChannel<Int>{
    return produce {
        //ProducerScope which inherits CoroutineScope and SendChannel
        println("this is ${this::class}")
        for(x in 1..5) this.send(x * x)
    }
}