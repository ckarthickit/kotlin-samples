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

- `Scope Builders`
  - Every __coroutine builder__, adds an instance of CoroutineScope to the scope of its code block.
  - An outer coroutine __does not complete until all the coroutines launched in its scope complete__.(No need for explicit `join`).
  - Few Scope Builders...
    1. [runBlocking][run_blocking] -> blocsk the current Thread until all launched children complete.
    2. [coroutineScope][coroutine_scope] -> When any child coroutine in this scope fails, this scope fails and all the rest of the children are cancelled.
        - The provided scope inherits its coroutineContext from the outer scope, but overrides the context’s Job.
    3. [supervisorScope][supervisor_scope] -> A failure of a child does not cause this scope to fail and does not affect its other children.
        - The provided scope inherits its coroutineContext from the outer scope, but overrides context’s Job with SupervisorJob.

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
