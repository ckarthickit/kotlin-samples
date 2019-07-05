# Functions

- Declared using `fun` keyword.

## Function Parameters

- Defined using `pascal notation` i.e.,__(name: Type)__.
- Each parameter __MUST__ be explicitly typed.

   ```kotlin
   fun powerOf(number: Int, exponent: Int) { //... }
   ```

- __Default Parameters__
  - which are specified using `=` __after type along with value__.

   ```kotlin
      fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size) { //... }
   ```

  - `Overriding methods` use same default parameter __values as the base method__.

   ```kotlin
      open class A {
         open fun foo(i: Int = 10) { ... }
      }

      class B : A() {
         override fun foo(i: Int) { ... }  // no default value allowed
      }
   ```

  - If __the last parameter is a `lambda`__, it can be passed as `named arguement` or `outside the parameter`.

- __Named Arguements__
  - __Function parameters can be named__ while calling functions.

   ```kotlin
      //declaration
      fun reformat(str: String,
         normalizeCase: Boolean = true,
         upperCaseFirstLetter: Boolean = true,
         divideByCamelHumps: Boolean = false,
         wordSeparator: Char = ' ') {
         //...
      }
      //...
      //invocation
      reformat("some value",
         normalizeCase = true,
         upperCaseFirstLetter = true,
         divideByCamelHumps = false,
         wordSeparator = '_'
      )
   ```

  - __Cannot be used while calling `java functions`__.

- __Variable Arguements__
  - `vararg` used to pass  a variable list of `comma seperated` arguements to a function.
  - `vararg param of type T` is visible inside the function as `Array<out T>`.
  - __remember that T will be out parameter and hence the Array cannot consume anything. It can only produce values__.
  - `Spread Operator`
  - Used to spread the value of an `class Array` in an arguement list when invoking a vararg function.

   ```kotlin
      fun <T> printValues(vararg values: T) {
         for(v in values) {
            print("$v\t")
         }
         println()
      }
      //invocation using spread operator
      val arrayValues = arrayOf(4, 5, 6, 7)
      printValues(1, 2, 3, *arrayValues, 8, 9 ) //pass normal values + pass array using spread operator
   ```

## Function Returns

### Unit Returns

- __Used when__ a function `doesn't return any useful value`.
- Equivalent to `void in java`
- __Unit__ return type declarations `can be ommitted`.

### Nothing Returns

- __Used when__ a function `never returns` (or) `never returns normally` (eg., Always throws exceptions).

## Infix Functions

- Functions marked with the `infix` keyword can also be called using the infix notation (`omitting the dot and the parentheses for the call`).
  - Must be `member functions` (or) `extension functions`
  - Must have `single parameter` which will come on the right hand side of the infix notation
  - __NO__ `variable arguments` and __NO__ `default value`.

  ```kotlin
   infix fun Int.shl(x: Int): Int { ... }

   // calling the function using the infix notation
   1 shl 2

   // is the same as
   1.shl(2)
  ```

- __Have lower precedence than__
  - Arithmetic operators (+ , - , * , /)
  - Type Casts (obj as Set<*>)
  - rangeTo operator

## Local Functions

## Member Functions

## Extension Functions & Properties

- `Prefix` a function name with `reciever Type`.

  ```kotlin
    fun MutableList<Int>.swap(index1: Int, index2: Int) {
      val tmp = this[index1] // 'this' corresponds to the list
      this[index1] = this[index2]
      this[index2] = tmp
   }
  ```

## Generic Functions

## Inline Functions

## Higher-Order Functions

- A function that `takes a functions as parameters (or) returns a function`.

## Lambdas

## Tail-Recursive Functions

## Function Expressions

- Used when function returns a single expression (by omitting the curly braces).

  ```kotlin
    fun double(x: Int): Int = x * 2
  ```

## Scope Functions

- Functions that are called on an object with `lambda expression` forming a temporary scope.

   | Function |  Context Object Pass Method | Return value |  Usage | Is Extension Function |
   | ---      |  ---                        | ---          | ---    | ---                   |
   | let | `as lambda arguement` (default is `it`) | `Lambda Result` | `let` the following `actions be invoked` on the `arguement` and the `result be returned`. (Used to invoke one or more functions on results of call-chains ) | Yes |
   | run |   `as lambda reciever`( by keyword __this__) | `Lambda Result` | `run` these on the `context-object` and return the result of last expression | Yes |
   | run (invoked without context object) |   - | `Lambda Result` | `run` these statements and return `an expression` | No. Called without context-object |
   | with | `as lambda reciever`( by keyword __this__) | `Lambda Result` | `with` this `arguement as reciever` do the following and return the result | No. Takes context-object as arguement |
   | apply |`as lambda reciever`( by keyword __this__) | `Context Object`| `apply` the following actions/operations on the `context-object`|   Yes |
   | also | `as lambda arguement` (default is `it`)  | `Context Object` | `also` do the following (__removal__ of __also__ calls __usually doesn't__ affect the program logic) | Yes |

- If the code-block contains a `single function` __with__ `it` as `arguement`, we can use the `method-reference` __instead of a lambda__.

  ```kotlin
    val numbers = mutableListOf("one", "two", "three", "four", "five")
    numbers.map { it.length }.filter { it > 3 }.let(::println) //has single function "println" with "it" as arguement.
  ```

## Special Functions

- `takeIf` ->  returns `the object` if it __matches the predicate__, else `null`.
  
   ```kotlin
    val caps = str.takeIf { it.isNotEmpty() }?.toUpperCase()
   ```

- `takeUnless`-> returns `the object` if it __doesn't match the predicate__ and `null` if it does.

  ```kotlin
    val caps = str.takeUnless { it.length() < 3 }?.toUpperCase()
  ```
