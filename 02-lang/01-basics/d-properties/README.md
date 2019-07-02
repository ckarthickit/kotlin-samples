# Properties

- A `class entity` that has:
  1. Backing Field (`field`)
  2. getter (if it's a `val` (or) `var`)
  3. setter (if it's a `var`)

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

## Overriding Properties

## Delegating Properties

