# Kotlin Hello World

## Root Build Script

```groovy
buildscript {
    ext.kotlin_version = '1.3.31'
    
    dependencies {
       classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}
```

## App build script

```groovy
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

dependencies {
  implementation"org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
  implementation 'androidx.core:core-ktx:1.0.2'
}
```

## Android KTX  - Part of Android Jetpack.

- A set of Extensions (Extension functions, Extension Properties, Lambdas, etc.,) 
  that provides/enables a concise, idiomatic kotlin syntax for Android APIs (at call-site).
 

- Refer to [Android KTX modules][android_ktx] for more details. 
  - Core KTX 
    
    ```kotlin
      // SharedPreferences.edit extension function signature from Android KTX - Core
      // inline fun SharedPreferences.edit(
      //         commit: Boolean = false,
      //         action: SharedPreferences.Editor.() -> Unit)
      
      // Commit a new value asynchronously
      sharedPreferences.edit { putBoolean("key", value) }
        
      // Commit a new value synchronously
      sharedPreferences.edit(commit = true) { putBoolean("key", value) }
    ```

  - Fragment KTX
    
    ```kotlin
        fragmentManager().commit {
           addToBackStack("...")
           setCustomAnimations(
                   R.anim.enter_anim,
                   R.anim.exit_anim)
           add(fragment, "...")
        }
    ```
  - Palette KTX
  - SQLite KTX
  
    ```kotlin
        db.transaction {
            // insert data
        }
    ```
    
  - Collection KTX
  
    ```kotlin
        // Combine 2 ArraySets into 1.
        val combinedArraySet = arraySetOf(1, 2, 3) + arraySetOf(4, 5, 6)
        
        // Combine with numbers to create a new sets.
        val newArraySet = combinedArraySet + 7 + 8
    ```
  - ViewModel KTX
  - Reactive Streams KTX
  - Navigation KTX
  - WorkManager KTX

## Kotlin Standard  Library

- Provides
  - Higher order functions for idiomatic patterns 
    - Scope Functions such as [let][let], [with][with], [run][run], [apply][apply], [also][also]
  
---

[android_ktx]: https://developer.android.com/kotlin/ktx
[let]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/let.html
[apply]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/apply.html
[use]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/use.html
[run]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/run.html
[with]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/with.html
[synchronized]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/synchronized.html
[also]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/also.html
[fold]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/fold.html
[isInitialized]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/is-initialized.html
[takeIf]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/take-if.html
[takeUnless]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/take-unless.html
[to]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/to.html
[TODO]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-t-o-d-o.html
[lazy]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/lazy.html
