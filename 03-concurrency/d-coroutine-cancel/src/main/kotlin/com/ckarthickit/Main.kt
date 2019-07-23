package com.ckarthickit

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

/**
 * Root main
 */
fun main() {
    com.ckarthickit.a_cancel.main()
    sleepBetweenExercises()
    println("======NON-CANCELLABLE/ NON-COOPERATIVE-COROUTINE==========")
    com.ckarthickit.b_non_cancellable.main()
    sleepBetweenExercises()
    println("==========COOPERATIVE-COROUTINE========")
    com.ckarthickit.c_cooperative_coroutine.main()
    sleepBetweenExercises()
    println("==========FINALLY-ON-CANCEL========")
    com.ckarthickit.d_finally_on_cancel.main()
    sleepBetweenExercises()
    println("==========NON-CANCELLABLE-CONTEXT========")
    com.ckarthickit.e_non_cancelable_context.main()
    sleepBetweenExercises()
    println("==========COROUTINE-WITH-TIMEOUT========")
    com.ckarthickit.f_timeout.main()
}

fun sleepBetweenExercises() {
    //Thread.sleep(2000)
}


/******************* UTILS **************************/
suspend fun printContext() {
    println(getContext())
}

suspend fun getContext(): String {
    return "[thread= ${Thread.currentThread().id}, context=${coroutineContext[CoroutineName.Key]}, isActive=${coroutineContext.isActive}]"
}
