import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.reflect.KProperty


data class CoroutineName(val name: String) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<CoroutineName> by Companion

    companion object {
        val KEY: CoroutineContext.Key<CoroutineName> = object : CoroutineContext.Key<CoroutineName> {

        }

        operator fun getValue(thisRef: Any?, prop: KProperty<*>): CoroutineContext.Key<CoroutineName> {
            return KEY
        }
    }
}
/**
 * Primitive to start a co-routine
 * Note that startCoroutine is done by creating a new Continuation object that has a context.
 */
fun runAsCoroutine(block: suspend () -> Unit) {
    block.startCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext + CoroutineName("runAsCoroutine")

        override fun resumeWith(result: Result<Unit>) {
            println("async resumedWith: ${result.exceptionOrNull()}")
        }

    })
}


suspend fun customDelay(millis: Long) {
    println("suspend_coroutineContext = ${coroutineContext[CoroutineName.KEY]}")
    suspendCoroutine<Unit> {
        thread {
            Thread.sleep(millis)
            println("resume_coroutineContext = ${it.context[CoroutineName.KEY]}")
            it.resumeWith(Result.success(Unit))
        }
    }
}