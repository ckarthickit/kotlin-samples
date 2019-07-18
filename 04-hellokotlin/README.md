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

