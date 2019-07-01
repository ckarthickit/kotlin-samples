# Classes

## Grammar

- Declared using `class` keyword.
- Declaration consists of 3 parts
  1. class name.
  2. class header -> specifying it's type parameters, primary constructor etc.,
  3. class body -> sorrounded with curly braces.

  ```kotlin
    class Person public @Inject constructor(firstName: String) { ... }
  ```

## Constructors

- Kotlin class can have `a primary constructor` (__which cannot contain any code__) and one or more `secondary constructor`.
- Intiailizer Blocks
  - prefixed with `init` keyword
  - Can have multiple initializer blocks. They are executed in the same order as they appear in class body.

    ```kotlin
        class InitOrderDemo(name: String) {
            val firstProperty = "First property: $name".also(::println)
            init {
                println("First initializer block that prints ${name}")
            }
            val secondProperty = "Second property: ${name.length}".also(::println)
            init {
                println("Second initializer block that prints ${name.length}")
            }
        }
    ```
