# Kotlin Command Line Compilation / Execution

## Compilation

- `kotlinc-jvm` is the Kotlin compiler for JVM.
- `-d` option is used to specify Destination for generated class files. It can be the name of a  directory or JAR file.

- Execute the below command from project's root folder.

  ```bash
  kotlinc-jvm 01-command-line/02-basics/01_hello.kt -d out
  ```

- The out directory will have the following files genereated.

  ```bash
  -out
    -com
      -kar
        -demo
          -_01_helloKt.clas
    -META-INF
      - main.kotlin_module
  ```

## Execution

- `kotlin` executable is the Kotlin interpretor.
- Execute the below command to run the above compiled code.

  ```bash
  $ kotlin -cp out/ com.kar.demo._01_helloKt
  Hello World
  ```
  