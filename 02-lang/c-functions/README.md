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

## Operator Overloading

- done by:
  1. Providing `member functions` or `extension functions` with a __fixed name__ and
  2. Marking them with `operator` modifier.
- Signatures are checked for proper compatibility. Eg:
  - Unary operators on type `T` can have __any return value__ `R`.
  - Increment operators (++, --) on type `T` should __return sub-type__ of `T`.
- The `left-most` variable will be the `reciever` and all others will be `params` to reciever object's `operator function`.

### Operator to function Mapping
  
- (Let `T` be type of __a__, `PT` be type of __b__, `IT` type of __i__ be and `R` be __return-type__)

  | Operator Type | Operator | Function                           | Comments                       |
  | ---           | :---:    | :---                               | :---                           |
  | Unary         | +a       | operator fun unaryPlus(): R        |                                |
  |               | -a       | operator fun unaryMinus(): R       |                                |
  |               | !a       | operator fun not(): R              |                                |
  |               | a++      | operator fun inc(): R              | <li>`R` should be sub-type of `T`. <li>__Shouldn't mutate the object on which it was invoked.__ <li>`Suffix-form` stores current value in temporary variable `a0` and assigns result of `a.inc()` to `a` then returns `a0` as result of `expression`.<li> `Prefix-form` assigns result of `a.inc()` to `a` then returns `a` as result of `expression`.  |
  |               | a--      | operator fun dec(): R              | <li>`R` should be sub-type of `T`. <li>__Shouldn't mutate the object on which it was invoked.__ <li>`Suffix-form` stores current value in temporary variable `a0` and assigns result of `a.dec()` to `a` then returns `a0` as result of `expression`.<li> `Prefix-form` assigns result of `a.dec()` to `a` then returns `a` as result of `expression`.  |
  | Binary        | a + b    | operator fun plus(other: PT): R       |                               |
  | Binary        | a - b    | operator fun minus(other: PT): R      |                               |
  | Binary        | a * b    | operator fun times(other: PT): R      |                               |
  | Binary        | a / b    | operator fun div(other: PT): R        |                               |
  | Binary        | a % b    | operator fun rem(other: PT): R        |                               |
  | Binary        | a..b     | operator fun rangeTo(other: PT): R    |                               |
  | Binary        | a in b   | operator fun contains(element: PT): R | `R` is usually `Boolean`<br> __Few standard library implementations:__ <li>operator fun <T> Array<out T>.contains(element: T): Boolean <li>operator fun <T> Iterable<T>.contains(element: T): Boolean <li> operator fun <K, V> Map<out K, V>.contains(key: K): Boolean <li>operator fun CharSequence.contains(other: CharSequence, ignoreCase: Boolean = false): Boolean|
  | Binary        | a !in b   | operator fun contains(element: PT): R | Translates to `!b.contains(a)`|
  | Index Access  | a[i]      | opeartor fun get(index: IT): R         |Translated to `a.get(i)`      |
  | Index Access  | a[i_1, ..., i_n]      | opeartor fun get(i_1: IT1, ... , i_n: ITN): R |Translated to `a.get(i_1, ..., i_n)`      |
  | Index Access  | a[i] = b   | operator fun set(index: IT, element: PT): PT | Translated to a.set(i, b) |
  | ...           |  ...       | ...      | ... |
  | Invoke Operator | a()      | operator fun invoke(): R | Translated to `a.invoke()` |
  |                 | a(i)       | operator fun invoke(param: PT): R | Translated to `a.invoke(i)` |
  | Augmented assignments | a += b | operator fun plusAssign(other: PT): R| a.plusAssign(b) |
  | Augmented assignments | a -= b | | a.minusAssign(b) |
  | Augmented assignments | a *= b | | a.timesAssign(b) |
  | Augmented assignments | a /= b | | a.divAssign(b) |
  | Augmented assignments | a %= b | | a.remAssign(b) |
  | Equality Operator | a == b | operator fun equals(other: Any?): Boolean | Translated to `a?.equals(b) ?: (b === null)` |
  | Inequality Operator | a != b | operator fun equals(other: Any?): Boolean | Translated to `!(a?.equals(b) ?: (b === null))` |
  | Comparison          | a > b | operator fun compareTo(other: T): Int | Transaltes to `a.compareTo(b) > 0` |
  | Comparison          | a < b | operator fun compareTo(other: T): Int | Transaltes to `a.compareTo(b) < 0` |
  | Comparison          | a >= b | operator fun compareTo(other: T): Int | Transaltes to `a.compareTo(b) >= 0` |
  | Comparison          | a <= b | operator fun compareTo(other: T): Int | Transaltes to `a.compareTo(b) <= 0` |
  | Property Delegation | val `<property_name>`: `<Type>` by `<expression>` | operator fun getValue(thisRef: Any?, property: KProperty<*>): R | `getValue` is __Invoked when property is `read`__ |
  | Property Delegation | var `<property_name>`: `<Type>` by `<expression>` | operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) | `setValue` is __Invoked when property is `written` into__|
  | Property Delegation | var `<property_name>`: `<Type>` by `<expression>` | operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): R | `provideDelegate` is invoked on the Delegate object for `each delegating property` upon __creation of instance of the delegating property__.|
  | Object Destructuring | val/var (<val1,val2,..valN>) = `<object>` | operator fun componentN():PTN | Destructures an Object into local variables |

## Component Functions

- Used for Destructoring an Object of a Class.

  ```kotlin
      class Name(firstNameVal:String, secondNameVal:String, ageVal: Int){
         val firstName: String = firstNameVal
         val secondName: String = secondNameVal
         val age : Int = ageVal

         operator fun component1(): String {
            return firstName;
         }
         operator fun component2(): String {
            return secondName;
         }
         operator fun component3(): Int {
            return age;
         }
      }
      class Animal(val breed: String, val age: Int)
      operator fun Animal.component1(): String {
         return breed
      }
      operator fun Animal.component2(): Int {
         return age
      }

      fun main(){
         val (personFName , personSName , personAge) = Name("Johnny","Walker",50);
         println("personFName is $personFName, personSName is $personSName, personAge is $personAge")//prints "personFName is Johnny, personSName is Walker, personAge is 50"

         val (animalName, animalAge) = Animal("Leopard", 30)
         println("animalName is $animalName, animalAge is $animalAge")//prints "animalName is Leopard, animalAge is 30"
      }
  ```
