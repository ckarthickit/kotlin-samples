# Exceptions

- All exceptions are descendants of `Throwable` class.
  
  ```kotlin
    try {
        // some code
    }
    catch (e: SomeException) {
        // handler
    }
    finally {
        // optional finally block
    }
  ```

## The `try-expression`

- The return value is either the `last expression in try block` (or) the `last expression of catch block`.
- `finally block` does not affect the result of the expression.

  ```kotlin
    val a: Int? = try { parseInt(input) } catch (e: NumberFormatException) { null }
  ```

## The `throw-expression`

- It always returns the special type `Nothing`.
  
  ```kotlin
    val s = person.name ?: throw IllegalArgumentException("Name required")
  ```
