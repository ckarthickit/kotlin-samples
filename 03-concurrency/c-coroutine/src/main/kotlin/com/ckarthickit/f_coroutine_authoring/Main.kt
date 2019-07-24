package com.ckarthickit.f_coroutine_authoring



fun main() {
    runBlocking {
        println("class = ${this::class}")
        launch {
            println("class = ${this::class}")
        }
        val job = async {
            println("class = ${this::class}")

        }
        job.await()
    }

    //unresolved reference
//    launch{
//
//    }
}


interface CoroutineScope {
    val context: Any?
}
interface Job{

}
interface Deferred : Job {
    fun await()
}

abstract class AbstractCoroutine(context: Any? = null) : CoroutineScope, Job {
    override val context: Any? = context
}

internal class BlockingCoroutine(context: Any? = null): AbstractCoroutine(context)
internal class StandaloneCoroutine(context: Any? = null) : AbstractCoroutine(context)
internal class DeferredCoroutine(context: Any? = null): AbstractCoroutine(context), Deferred {
    override fun await() {
        println("fake await")
    }
}



fun <T> runBlocking(block: CoroutineScope.() -> T) : T {
    val reciever = BlockingCoroutine()
    return block(reciever)
}

fun CoroutineScope.launch(block: CoroutineScope.() -> Unit): Job {
    val reciever = StandaloneCoroutine()
    reciever.block()
    //block(reciever)
    return reciever
}

fun CoroutineScope.async(block: CoroutineScope.() -> Unit): Deferred {
    val reciever = DeferredCoroutine()
    //reciever.block()
    block(reciever)
    return reciever
}