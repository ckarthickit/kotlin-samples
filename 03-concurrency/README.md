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
  | No synchronization primitives needed | Synchronization primitives such as mutex, semaphores are needed|

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

  - Application code usually __should use an application-defined `[CoroutineScope]`__. Using [async][CoroutineScope.async] or [launch][CoroutineScope.launch] on the instance of `[GlobalScope]` is ___highly discouraged__.

- `Coroutine builders` ([source][coroutine_builders]) -> Bridge between `non-coroutine` and `co-routine` worlds.
  - [CoroutineScope.launch][launch] -> Returns a [Job][job]
  - [CoroutineScope.async][async] -> Returns [Deferred<T>][deferred]
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

---

## References

- [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)

---
[CoroutineScope.async]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html
[CoroutineScope.launch]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html
[Dispatchers]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html
[coroutine_builders]: https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/Builders.common.kt
[launch]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html
[async]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html
[job]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
[deferred]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html
