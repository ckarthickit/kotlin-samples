package types;


/************operators-demo************** */
class Something{}
fun operatorsDemo() {
    println("=== operators-demo ===")
    println("class name is: ${Something::class}")
    println("class qualified name using reflection is: ${Something::class.qualifiedName}")
}
/************************************/
fun spreadOpeartorDemo() {
    val numbers = arrayOf(1,2,3,4)
    
}
/************************************/

fun main(args: Array<String>) {
    
    operatorsDemo()
}

