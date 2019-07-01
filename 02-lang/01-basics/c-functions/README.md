# Functions

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
