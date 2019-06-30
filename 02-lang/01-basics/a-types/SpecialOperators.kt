fun main(args: Array<String>) {
    println("=== operators-demo ===")
    operatorsDemo()
}

class Something{}
fun operatorsDemo() {
    println("class name is: ${Something::class}")
    println("class qualified name using reflection is: ${Something::class.qualifiedName}")
}