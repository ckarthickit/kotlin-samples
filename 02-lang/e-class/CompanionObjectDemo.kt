/*************************/
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }

    init {
        println("MyClass initialized")
    }

    fun printMsg() {
        println("MyClass message");
    }
}

fun factoryUsingCompanionDemo(){
    val myClassObj = MyClass.create()
    myClassObj.printMsg()
}
/*************************/
fun main(){
    println("===== factory-using-companion-demo ====")
    factoryUsingCompanionDemo()
}