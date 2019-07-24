# Kotlin Concurrency Programming

## Coroutines

`Coroutines` are computer programs that genealize sub-routines for `non-preemptive multitasking`, by allowing __execution__ to be **suspended and resumed**.

- Well suited for implementing program components such as
  - co-opertive tasks
  - exceptions
  - event loops
  - iterators
  - infinite lists
  - pipes

- `Threads vs Coroutines`

  | Coroutine | Thread |
  | ---       | ---    |
  | Cooperatively multi-tasked | Preemptively multi-taskes |
  | Switching between co-routines need not involve any system calls or blocking calls<br> They `yield` to another co-routine. | Switching between threads usuaylly involves __system calls__ and might __block__|
  | No synchronization primitives needed?? | Synchronization primitives such as mutex, semaphores are needed|

---

## Kotlin Coroutines Building Blocks

- `Coroutine Scope` -> every __builder__ is an __extension on CoroutineScope__.

  ```kotlin
    /**
    * Defines a scope for new coroutines. Every coroutine builder
    * is an extension on [CoroutineScope] and inherits its [coroutineContext]     [CoroutineScope.coroutineContext]
    * to automatically propagate both context elements and cancellation.
    */
    public interface CoroutineScope {
        /**
        * The context of this scope.
        * Context is encapsulated by the scope and used for implementation of coroutine builders that are extensions on the scope.
        * Accessing this property in general code is not recommended for any purposes except accessing the [Job] instance for advanced usages.
        *
        * By convention, should contain an instance of a [job][Job] to enforce structured concurrency.
        */
        public val coroutineContext: CoroutineContext
    }
  ```

  - Application code usually __should use an application-defined `[CoroutineScope]`__. Using [async][CoroutineScope.async] or [launch][CoroutineScope.launch] on the instance of `[GlobalScope]` is __highly discouraged__.

- `Coroutine builders` ([source][coroutine_builders]) -> Bridge between `non-coroutine` and `co-routine` worlds. Also, every co-routine builder __adds an instance of CoroutineScope to the scope of its code block__.
  - [CoroutineScope.launch][CoroutineScope.launch] -> Returns a [Job][job]
  - [CoroutineScope.async][CoroutineScope.async] -> Returns [`Deferred<T>`][deferred]
  - [CoroutineScope.produce][produce] -> Returns [ReceiveChannel][recieve_channel]
  - [CoroutineScope.actor][actor] -> Returns [SendChannel][send_channel]
  - [CoroutineScope.broadcast][broadcast] -> Returns [BroadcastChannel][broadcast_channel]

- `Coroutine Context` -> Persistent context for the coroutine. It is an indexed __set of__ `[Element]` instances, each of which has a unique `[Key]`
  - Coroutine Context `[Element]`s:
    - Job
    - CoroutineDispatcher ([Dispatchers][Dispatchers])
      - `Dispatchers.Default` - backed by a shared pool of threads on JVM.
      - `Dispatchers.Main` - a dispatcher confined to main thread operating on UI Objects
      - `Dispatchers.Unconfined` - not confined to any specific thread.
    - CoroutineExceptionHandler
    - ContinuationInterceptor
      - intercepts coroutine continuations
      - has `interceptContinuation` method that returns continuation that wraps the original `[continuation]`, thus intercepting all resumptions.
    - CoroutineName

- `Continuation` -> represents continutation after a suspension point.
  
  ```kotlin
    public interface Continuation<in T> {
        /**
        * The context of the coroutine that corresponds to this continuation.
        */
        public val context: CoroutineContext

        /**
        * Resumes the execution of the corresponding coroutine passing a successful or failed [result] as the
        * return value of the last suspension point.
        */
        public fun resumeWith(result: Result<T>)
    }
  ```

- `AbstractCoroutine` -> Abstract base class for implementation of coroutines in coroutine builders

    ```kotlin
        public abstract class AbstractCoroutine<in T>(
            /**
            * The context of the parent coroutine.
            */
            @JvmField
            protected val parentContext: CoroutineContext,
            active: Boolean = true
        ) : JobSupport(active), Job, Continuation<T>, CoroutineScope{
            //...
        }
    ```

  - Known sub-classes
    1. `StandaloneCoroutine` -> created by `launch` __builder__ function.
    2. `DeferredCoroutine` -> created by `async` __builder__ function.
    3. `BlockingCoroutine<T>` -> created by `runBlocking` __builder__ function.
    4. `ScopeCoroutine` -> created by `coroutineScope` __suspend__ function.
    5. `SupervisorCoroutine`-> created by `supervisorScope` __suspend__ function.
    6. `DispatchedCoroutine` -> created by `withContext` __suspend__ function if the `ContinuationInterceptor` of new context is different from the old context.
    7. `LazyStandaloneCoroutine` etc.,

- `Scope Builders` - A way to __Structured-Concurreny__.
  - Every __coroutine builder__, adds an instance of CoroutineScope to the scope of its code block.
  - An outer coroutine __does not complete until all the coroutines launched in its scope complete__.(No need for explicit `join`).
  - Few Scope Builders...
    1. [runBlocking][run_blocking] -> blocsk the current Thread until all launched children complete.
    2. [coroutineScope][coroutine_scope] -> When any child coroutine in this scope fails, this scope fails and all the rest of the children are cancelled.
        - The provided scope inherits its coroutineContext from the outer scope, but overrides the context’s Job.
    3. [supervisorScope][supervisor_scope] -> A failure of a child does not cause this scope to fail and does not affect its other children.
        - The provided scope inherits its coroutineContext from the outer scope, but overrides context’s Job with SupervisorJob.

---

## Idea of Coroutine Implementation

> The following code snippet will give us an idea of how Coroutine is __scoped__ and how __co-routine builders__ are constructed.

```kotlin
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

}

interface CoroutineScope {
    val context: Any?
}
interface Job{

}
interface Deferred : Job {
    fun await()
}

//Every concretet Coroutine object implements(is-a) CoroutineScope , a Job and a Continuation
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


//runBlocking is the only co-routine builder that is not an extension of CoroutineScope.
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
```

If run, the above code will print

> class = class com.ckarthickit.BlockingCoroutine (Kotlin reflection is not available)  
> class = class com.ckarthickit.StandaloneCoroutine (Kotlin reflection is not available)  
> class = class com.ckarthickit.DeferredCoroutine (Kotlin reflection is not available)  
> fake await

---

## Extracting functionality into Suspending Functions

Functionality inside a coroutine can be `extracted into a seperate suspend function`.
>If the __extracted suspend function__ `contains a coroutine builder which is launched in the current scope`, we need to pass scope explicity (or) implicitly as per requirement.

Options to make CorotuineScope available for suspend functions :

- Make __CoroutineScope__ a field in the class containing the suspend function.
- Make the oute class implement  __CoroutineScope__ (recommended way is by delegation).
- Make the __suspend function__ an Extension function on __CoroutineScope__.
- As a last resort `fun CoroutineScope(context: CoroutineContext)` can be used to create a scope.

> `CoroutineScope` should be implemented (or used as a field) on __entities with a well-defined lifecycle__ that are responsible for launching children coroutines. Example of such entity on Android is Activity.

  ```kotlin
    class MyActivity : AppCompatActivity(), CoroutineScope by MainScope() {
        override fun onDestroy() {
            cancel() // cancel is extension on CoroutineScope
        }

        /*
        * Note how coroutine builders are scoped: if activity is destroyed or any of the launched coroutines
        * in this method throws an exception, then all nested coroutines are cancelled.
        */
            fun showSomeData() = launch { // <- extension on current activity, launched in the main thread
            // ... here we can use suspending functions or coroutine builders with other dispatchers
            draw(data) // draw in the main thread
            }
        }
  ```

---

## Coroutine Cancellation and Timeouts

 The [job][job] of a Coroutine can be used to __cancel the running-coroutine__.

> Cancellation is `co-operative` in Coroutine world i.e., the runnig coroutine/job has to check for cancellation whenever it can.

- __Methods to check for cancellation__
    1. Periodically invoke a suspending function that checks for cancellation (`yied`, `delay`).
        - All suspending functions inside `kotlinx.coroutines` package are __cancellable__.
    2. Explicity check for cancellation status by invoking `CoroutineScope.isActive`.

> If a co-routine is NOT active, we cannot invoke any __suspend functions__ on it. Doing so will result in `CancellationException`.

- __Finally block after cancellation__
  - `finally` block is executed irrespecitve of co-routine's `isActive` state.
  - To __call suspend functions__ in `finally` block of a __cancelled co-routine__, we need to wrap the corresponding code in `withContext(NonCancellable)`.
    - NonCancellable is a singleton `object NonCancellable`.

    ```kotlin
        scope.launch(CoroutineName("launchAJob")) {
            try {
                repeat(1000) { index ->
                    println("job: I'm sleeping $index ... ${getContext()}")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally ${getContext()}")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable ${getContext()}")
                }
            }
        }
    ```

- __Timeout for Co-Routines__
  - `withTimeout` cancells the wrapping-coroutine/job (TimeoutCoroutine) after a passed __time-out__ and raised a `TimeoutCancellationException`.
  - `withTimeoutOrNull` __returns null__ on timeout instead of raising an exception.

---

## Channels

- Provides a convenient way to `transfer a stream of values` __between coroutines__, as opposed to `Deferred` which provides a convenient way to transfer a single value between coroutines.
- `Channel` and `BlockingQueue` are __Conceptually Similar__.
  
  | Channels | Blocking Queue |
  | ---      | --             |
  | Suspending [send][SendChannel.send] | blocking `put` |
  | Suspending [recieve][RecieveChannel.receive] | blocking `take` |
  | __Can be closed__ to indicate no more elements are coming | No such option |

- __Producer-Consumer__ using Channels
  - Can use `class Channel`'s  __send__ and __recieve__  methods in different co-routines
  - Can create `ProducerCoroutine` using `CoroutineScope.produce` builder and start `consume` from the caller co-routine.
- __Pipeline__ using Channels
  - A __Pattern__ where one coroutine is producing (possiblly infinite) stream of values and another coroutine (or) coroutines are __consuming__ the stream doing some __processing__ and producing some __other results__.

---

## References

- [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)

---
[coroutine_builders]: https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/Builders.common.kt
[CoroutineScope.launch]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html
[CoroutineScope.async]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html
[produce]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/produce.html
[actor]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/actor.html
[broadcast]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/broadcast.html

[job]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
[deferred]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html
[recieve_channel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/index.html
[send_channel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/index.html
[broadcast_channel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-broadcast-channel/index.html

[run_blocking]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html
[coroutine_scope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html
[supervisor_scope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/supervisor-scope.html

[Dispatchers]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html

[SendChannel.send]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/send.html
[RecieveChannel.receive]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/receive.html
