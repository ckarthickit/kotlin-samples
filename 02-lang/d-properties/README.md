# Properties

- An `entity` that has:
  1. Backing Field (`field`) - (__optional__, there are computed and delegated properties)
  2. getter (if it's a `val` (or) `var`)
  3. setter (if it's a `var`)
- Can be declared `top-level` (Outside of any class)
- __Local variables__ (eventhough they are marked with `var` and `val`) are __NOT properties__.

## Grammar

- full-syntax:

  ```kotlin
    var <propertyName>[: <PropertyType>] [= <property_initializer>]
        [<getter>]
        [<setter>]
  ```

  - propertyName is `mandatory`.
  - PropertyType is `optional if inferred from property_initializer (or) getter`
  - property_initializer is `optional` (should be initialized using constructor if this is not specified)
  - getter is `optional`.
  - setter is `optional`.

## Property Type Inference

- Property type maybe omit-ted if it can be inferred from `get`ter.

## Backing Field

- `Field`s cannot be directly declared in Kotlin.
- Kotlin automatically provides a `backing field` to a `Property` if
  1. Property has an initializer.
  2. Property has `default implementation` of `atleast one` of the accessors (`get`ter or `set`ter).
  3. Property's `custom accessor` accesses the `field` identifier.
      - `field` __identifier can only be used inside acessors.__
- If there is no `backing field`, then it is probably a `computed property`.
  
  ```kotlin
    val isEmpty: Boolean get() = this.size == 0 //has no backing field.
  ```

## Backing Property

- If we `don't want implicit backing field` , we can fall-back to having a `backing property`.

  ```kotlin
    private var _table: Map<String, Int>? = null //backing property
    public val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap() // Type parameters are inferred
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }
  ```

## Compile-Time constant Properties

- Properties whose value is known at compile time can be marked `const`
- Requirements for `const`
  - Top-Level / member of object declaration / member of companion object
  - Initialized with `String` or `primitive types`.
  - No Custom getter.
- Eg.,

  ```kotlin
    const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"
  ```

## Late Initialized Properties

- `Non-Null` properties __typically__ must be initialized in the constructor (or) initializer.
- __However__, the typical case might __not be ideal__ for some situations. E.g.,
  - properties can be initialized through `dependency injection`
  - properties can be initialized in `setup` method of a `unit test case`.
- `lateinit` modifier is used for the above situation and only `when` __property doesn't have custom getter and setter__.
  - used only on `var` properties.
  - `cannot` be used in __primary-constructor__.
  - property should be `non-null` and `non-primitive`.
- __Checking if `lateinit` property is Initialized__.
  - use `.isInitialized` on the `reference to that property`

    ```kotlin
      class TestSubject
      class MyTest {
        lateinit var subject: TestSubject

        fun setup() {
          subject = TestSubject()
        }

        fun test() {
          if(::subject.isInitialized) { //isInitialized is invoked on the property reference
            subject.perform()
          }
        }
      }
    ```

## Overriding Properties

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

## Delegating Properties

- Few common patterns on how a property might be used
  - Lazy values (the value gets computed only upon first access, __using `backing nullable property` probably__).
  - Database Access (Internally handling the connection etc.,)
  - Notify Listeners on Access
  - Storing properties in a map, instead of a separate field for each property.
- Property Delegate objects must implement `setValue` and `getValue` __operator functions__.
  - `get` and `set` corresponding to a delegated property will be delegated to it's (object after `by`) getValue and setValue functions respectively.'
  - __The delegated property should not be initialized as they don't have a backing field in themselves. It is handled by delegate__.

  ```kotlin
    class Person() {
      var name: String by NameDelegate()
    }
    class NameDelegate: Any() {
        private val _propHolder : MutableMap<String,String> = HashMap()
        operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
            return "${prop.name}: ${_propHolder.get(prop.name)?:"no-data"}"
        }
        operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String): Unit {
            _propHolder[prop.name] = value
        }
    }
    ```

- __Can declare local variables as delegated properties__.

  ```kotlin
    class Foo {
        init {
            println("new Foo")
        }
        fun doSomething() {
            println("foo is doing something")
        }
    }
    fun localPropertyWithDelegate(computeFoo: ()->Foo) {
        val memoizedFoo by lazy(computeFoo)
        memoizedFoo.doSomething(); //accessing memoizedFoo
    }

    fun localDelegatedPropTest() {
        println("\n===== local-delegated-property-test====")
        var _backingFoo : Foo? = null
        val lateInitFoo = {
            if(_backingFoo == null) _backingFoo = Foo()
            _backingFoo as Foo
        }
        val alwasyNewFoo = {Foo()}

        localPropertyWithDelegate(lateInitFoo)
        localPropertyWithDelegate(lateInitFoo)
        localPropertyWithDelegate(lateInitFoo)

        localPropertyWithDelegate(alwasyNewFoo)
        localPropertyWithDelegate(alwasyNewFoo)
        localPropertyWithDelegate(alwasyNewFoo)
    }
  ```

- __Standard Delegates__
  - `lazy()` function takes a `lambda arguement` and returns `instance of Lazy<T>` which serves as delegate for implementing a lazy propery.
    - First call to `get` executes the lambda and remembers the result.
    - Subseuquent calls to `get` returns the remembered result.

    ```kotlin
      val lazyValue: String by lazy {
      println("computed!")
      "Hello"
    }

    fun main() {
        println(lazyValue)
        println(lazyValue)
    }
    ```

  - `Delegates.observable` => a property delegate that calls a specified callback when changed
  - `Delegates.vetoable` => a property delegate that calls a specified callback when changed, allowing the callback to __veto__ the modification.

- __Map as a property delegate__
  - `interface Map` provides `getValue` __operator extension function__ (because Map doesn't/needn't extend from `interface ReadOnlyProperty`).
  - `interface MutableMap` provides both `getValue` and `setValue` __operator extension functions__ (because MutableMap doesn't/needn't extend from `interface ReadWriteProperty`).
  - The underlying Map Delegate stores value for each property using the `name of the property as key`.
  - E.g.,

  ```kotlin
    class User(val map: Map<String, Any?>) {
        val name: String by map
        val age: Int     by map
    }
    //usage , also note the use of `to` infix function
    val user = User(mapOf(
        "name" to "John Doe",
        "age"  to 25
    ))
  ```

## Property Delegate Translation (Under the Hood)

- For Every Delegated property, an `auxiliary property` (__holding the delegate itself__) is created and accessors delegate to it
  
  ```kotlin
    class C {
        var prop: Type by MyDelegate()
    }

    // this code is generated by the compiler instead:
    class C {
        private val prop$delegate = MyDelegate()
        var prop: Type
            get() = prop$delegate.getValue(this, this::prop)
            set(value: Type) = prop$delegate.setValue(this, this::prop, value)
    }
  ```

## Providing the Delegate

- `provideDelegate` operator function is invoked on the Delegate object for `each delegating property` upon __creation of instance of the delegating property__.
- In essence , `provideDelegate` is called to initialize the `auxiliary property` and affect that property only.
  
  ```kotlin
      class C {
          var prop: Type by MyDelegate()
      }

      // this code is generated by the compiler
      // when the 'provideDelegate' function is available:
      class C {
          // calling "provideDelegate" to create the additional "delegate" property
          private val prop$delegate = MyDelegate().provideDelegate(this, this::prop)
          var prop: Type
              get() = prop$delegate.getValue(this, this::prop)
              set(value: Type) = prop$delegate.setValue(this, this::prop, value)
      }
  ```

- __Use Cases:__
  - Check a `delegated property's consistency` when it is created.
  - Check the `delegating propery's name` __before binding__ to underlying datastructure of the Delegate.
