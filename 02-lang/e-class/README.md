# Classes

## Grammar

- Declared using `class` keyword.
- Declaration consists of 3 parts
  1. class name.
  2. class header -> specifying it's type parameters, primary constructor etc., __(optional)__
  3. class body -> sorrounded with curly braces. __(optional)__

  ```kotlin
    class Person public @Inject constructor(firstName: String) { ... }
  ```

## Class Members

1. Constructors & Initializer blocks.
2. Functions.
3. Properties.
4. Nested & Inner classes.
5. Object Declarations.

## Constructors & Initializer Blocks

- Kotlin class can have `a primary constructor` (__which cannot contain any code__) and one or more `secondary constructor`. It can have one or more `Initializer Blocks` as well.

### Primary Constructor

- Declares the properties of a class with `val` and `var` keyword.
- __Cannot contain any code__ (Use Initializer Blocks instead).
- __Note:__ On the JVM, if `all of the parameters` of the primary constructor `have default values`, the compiler will generate an `additional parameterless constructor` which will `use the default values`.

### Intiailizer Blocks

- prefixed with `init` keyword
- Code in `Initializer Blocks` effectively become part of `primary constructor`.
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

### Secondary Constructors

- prefixed with `constructor` keyword.
- Each secondary constructor `MUST` delegate to `the primary constructor` either directly (or) indirectly through other secondary constructors.

### Order of Execution

1. Primary Constructor.
2. Intializer Blocks.
3. Secondary Constructor.

## Inheritance

- All classes in kotlin are `final by default` and must be `open`ed for inheritance explicitly.
- All classes in kotlin have a default super-class `class Any`.
  
  ```kotlin
    class Example //Implicitly  inherits from Any
  ```

- We can add explicit super by placing a `colon (:)` after class header.

  ```kotlin
    /***** deriving from a class with primary constructor ***/
    open class Base(p: Int)
    class Derived(p: Int): Base(p) //If derived class have primary constructor, then Base class MUST be initialized right here

    /***** another-demo without explicit primary constructor ***/
    open class AnotherBase
    class AnotherDerived: AnotherBase()
  ```

### Overriding Methods

- All methods in kotlin are `final by default` and must be `open`ed for overriding.
- __A member marked `override` itself is `open`__ by default. We can make it `final` by explicit mention
- Explicit `override` modifier needs to be added for methods overriden from Super class.

  ```kotlin
    open class Base{
      open fun v() { //... }
    }
    class Derived: Base() {
      override fun v() { //... } //overrides Base.v , Derived.v can be overridden as well if Dervived is open
    }
    class AnotherDervied: Base() {
      final override fun v() { //... } //overrides Base.v, prohibits overriding AnotherDervied.v if AnotherDervied is open
    }
  ```

### Overriding Properties

- Works `similar to overrding methods`.
- Why redeclare properties at all ?
  - If we want to customize it's `get`ter and `set`ter for the Derived class
  - override a `val` property with a `var` property. __(ViceVersa is not allowed)__
    - Essentially, we are adding addittional setter and hence it is allowed.

```kotlin
  open class Foo {
      open val x: Int get() = 1
      open val y: Int get() = 0
  }
  
  class Bar : Foo() {
    override val x: Int get() = super.x + 1
    override var y: Int get() = 0
  }
```

### Abstract class

- `abstract` class is `open` by default.

  ```kotlin
  open class Base {
        open fun f() {}
    }

    abstract class Derived : Base() {
        override abstract fun f()
    }
  ```

### Nested & Inner class

- `Nested Class` without `inner` specifier
  - doesn't carry any reference to the object of outer class.
- `Nested Class` with  `inner` specifier.
  - Holds reference to object of outer class.
  - Use `this@Outer` and `super@Outer` to refer to enclosing Outer class's `this` and `super` objects respectively.
- `Anonymous Class`
  - Declared using `object-expression`.
  - Code in object-expression __can access variables from enclosing scope__(even non-final variables).

  ```kotlin
    interface RunnableKt {
        fun run(): kotlin.Unit
    }
    //......... anonymous inner class extending Runnable
    val runnable = object : RunnableKt {
        override fun run() {
            println("Running anonymously")
        }
    }

    //....... anonymous inner class extending a `JAVA FUNCTIONAL INTERFACE`
    // For every JAVA FUNCTIONAL INTERFACE, there is probably a function created
    // with the same name as that of the Interface which accepts a lambda with the same
    // type signature as that of the Function inside that interface.
    // Hence it doesn't have access to the enclosing "this"
    val anotherRunnable = Runnable {
        println("Running anonymously in lambda")
    }
  ```

  - If __super-type__ has constructor  appropriate constructor params must be passed.

    ```kotlin
      open class A(x: Int) {
          public open val y: Int = x
      }

      interface B { ... }

      val ab: A = object : A(1), B {
          override val y = 15
      }
    ```

  - `Anonymous Objects` (or) Objects with no non-trivial super-types

    ```kotlin
        //...... adHoc class not extending from anything
        val adHocClass = object {
            val x = 100;
            val y = 278;
        }
        println("adHocClass.x= ${adHocClass.x}, adHocClass.y= ${adHocClass.y}")
    ```

  - __Anonymous Objects__ `can be used as types only in local and private declarations`.

  ```kotlin
      class C {
          // Private function, so the return type is the anonymous object type
          private fun foo() = object {
              val x: String = "x"
          }

          // Public function, so the return type is Any
          fun publicFoo() = object {
              val x: String = "x"
          }

          fun bar() {
              val x1 = foo().x        // Works
              val x2 = publicFoo().x  // ERROR: Unresolved reference 'x'
          }
      }
  ```

  - `JAVA Singla Abstract Method` interfaces - Special Case
    - Can be implemented as `a lambda` (or) `an object expression`.

    ```kotlin
      class AnotherOuter {
          val runnable1 = object: Runnable {
              override fun run() {
                  //Below Prints class AnotherOuter
                  println("I can access outer this: ${this@AnotherOuter::class}")
                  //Below Prints class AnotherOuter$runnable1$1
                  println("I have my own this: ${this::class}")
              }
          }
          val runnable2 = Runnable {
              //Below Prints class AnotherOuter
              println("I can access outer this: ${this@AnotherOuter::class}")
              //Below Prints class AnotherOuter, LAMBDA DOES NOT HAVE THIS
              println("I done't have my own this: ${this::class}")
          }
      }
    ```

- `Singleton Class`
  - Declared using `object-declaration`.
  - Object declaration's __initialization is thread-safe__.
  
  ```kotlin
    object DataProviderManager {
      fun registerDataProvider(provider: DataProvider) {
          // ...
      }

      val allDataProviders: Collection<DataProvider>
          get() = // ...
    }
  ```

### Companion Class

- `objet-declaration` marked with `companion` keyword becomes __companion class__.
- __Name__ of the companion object can be ommitted in which case, the name `Companion` will be used.

```kotlin
nterface Factory<out T> {
    fun create(): T
}
class MyClass1 {
    companion object Named : Factory<MyClass1> {
        override fun create() : MyClass1 = MyClass1()
    }
}

class MyClass2 {
    companion object : Factory<MyClass2> {
        override fun create() : MyClass2 = MyClass2()
    }
}

fun factoryUsingCompanionDemo() {
    val x = MyClass1()
    val y = MyClass1.Named //Points to companion object
    val z = MyClass1 //Points to companion object
    // y and z point to same object
    println("MyClass1()= $x, MyClass1.Named= $y, MyClass1=$z")

    val a = MyClass2()
    val b = MyClass2.Companion //name Companion can be used if doesn't have explicit name
    val c = MyClass2 //Points to companion object
    // b and c point to same object
    println("MyClass2()= $a, MyClass2.Named= $b, MyClass2=$c")
}
```
