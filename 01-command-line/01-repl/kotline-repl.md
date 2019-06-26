# KOTLIN REPL (Read, Eval, Print Loop)

- Can be invoked by calling `kotlinc-jvm` (if PATH variable is set to point to `$KOTLIN_HOME/bin`)

  ```bash
    $ kotlinc-jvm
    Welcome to Kotlin version 1.3.31 (JRE 12.0.1+12)
    Type :help for help, :quit for quit
    >>> fun printHello(){}
    WARNING: An illegal reflective access operation has occurred
    WARNING: Illegal reflective access by com.intellij.openapi.util.JDOMUtil$2 to constructor com.sun.xml.internal.stream.XMLInputFactoryImpl()
    WARNING: Please consider reporting this to the maintainers of com.intellij.openapi.util.JDOMUtil$2
    WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
    WARNING: All illegal access operations will be denied in a future release
    >>> fun printHello(){
    ... println("Hello World")
    ... }
    >>> printHello()
    Hello World
    >>>
  ```
