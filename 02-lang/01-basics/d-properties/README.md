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
