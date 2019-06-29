# Control Flow

## Statements vs Expressions

- `Expression has a value` and can be used as part of another expression.
- `Statement` is a top-level element in it's enclosing block and `doesn't have it's own value`.
- Java vs Kotlin

| Element | Java | Kotlin |
| ---  | --- | --- |
| Control Structures | All  are `Statements` | Most are `Expressions` except for loops (for, do & do-while)|
| Assignments | Are `Expressions` | Become `Statements` |

## if expression

- `if is an expression` and hence returns a value. Therefore, there is `NO TERNARY OPERATOR`.
- `if-expression` can be `blocks` and the `last expression` is the value of the block.

## when expression

- `when expression` also returns a value.
- `when subject` can be captured in a variable.

  ```kotlin
  when(var num = numFromFunction()) {
      1..10 -> println("${num} is within 1..10")
      else -> println("${num} is not in range)
  }
  ```

## for loops

- iterates through anything that provides `iterator`.
- grammar

  ```kotlin
  'for'
    '(' annotation* (variableDeclaration | multiVariableDeclaration) 'in' expression ')'
    controlStructureBody?
    ;
  ```

- Usages

  ```kotlin
    //using IntRange which is an Iterable
    for (i in 1..3) {
      println(i)
    }
    //using IntProgression which is an Itearable
    for (i in 6 downTo 0 step 2) {
      println(i)
    }
    // Using indices which is IntRange which is an Iterable
    for (i in array.indices) {
      println(array[i])
    }
    //using Iterable<IndexedValue<Int>>
    for ((index, value) in array.withIndex()) {
      println("the element at $index is $value")
    }
  ```

## while , do..while loops

- Same as other languages (like Java, C etc.,)
