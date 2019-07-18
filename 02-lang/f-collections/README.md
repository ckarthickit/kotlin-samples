# Collections

## Hierarchy

![Collection Hierarchy][collections_diagram]

## Collections Construction Functions

- `fun <T> listOf(vararg elements: T): List<T>` -> read-only list of given elements.
- `fun <T> setOf(vararg elements: T): Set<T>` -> read-only set with the given elements.
- `fun <K, V> mapOf(vararg pairs: Pair<K, V>): Map<K, V>` -> read-only map with the specified contents.
- `fun <T> mutableListOf(): MutableList<T>` -> returns a `MutableList`.
- `fun <T> mutableSetOf(): MutableSet<T>` -> returns a `MutableSet` (Underlying concrete class  is LinkedHashSet).
- `fun <K, V> mutableMapOf(): MutableMap<K, V>` -> returns a `MutableMap` (Underlying concrere class is LinkedHashMap).

## Empty Collection Functions

- `fun <T> emptyList(): List<T>`.
- `fun <T> emptySet(): Set<T>`.
- `fun <K, V> emptyMap(): Map<K, V>`.

---
[collections_diagram]: ./collections-diagram.png
