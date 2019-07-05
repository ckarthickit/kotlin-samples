# Compiling Co-Routines in Command-line

- Need [kotlinx-coroutines-core.jar](https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core). Refer to [kotlinx-coroutines-core README](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/README.md)

- Once downloaded, to base-folder, compilation can be done as below.

  ```bash
    #To compile coroutine-enabled code and output class file
    kotlinc-jvm -cp ../kotlinx-coroutines-core-1.3.0-M1.jar HelloCoroutine.kt -d out
    #To run coroutine-enabled code
    kotlin -cp out/:../kotlinx-coroutines-core-1.3.0-M1.jar HelloCoroutineKt
  ```

  Alternatively, we can compile the module as jar and do the following as well, 

  ```bash
    #To compile coroutine-enabled code and output as a single jar including kotlin run-time
    kotlinc-jvm -cp ../kotlinx-coroutines-core-1.3.0-M1.jar HelloCoroutine.kt  -include-runtime -d program.jar
    #To run coroutine-enabled code using kotlin interpretor
    kotlin -cp ../kotlinx-coroutines-core-1.3.0-M1.jar:program.jar HelloCoroutineKt
    #To run coroutine-enabled code using java interpretor (this also works as kotlin runtime is shipped with program.jar)
    java -cp ../kotlinx-coroutines-core-1.3.0-M1.jar:program.jar HelloCoroutineKt
  ```
