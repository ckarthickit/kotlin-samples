package com.ckarthickit.b_channel_producer_consumer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val squares = produceSquares()
    squares.consumeEach {
        println(it)
    }
    println("Done")
}

suspend fun CoroutineScope.produceSquares(): ReceiveChannel<Int>{
    return produce {
        //ProducerScope which inherits CoroutineScope and SendChannel
        for(x in 1..5) this.send(x * x)
    }
}