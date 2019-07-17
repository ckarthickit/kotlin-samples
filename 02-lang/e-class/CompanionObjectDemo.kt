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

fun companionDemo(){
    val myClassObj = MyClass.create()
    myClassObj.printMsg()
}
/*****************************/
interface Factory<out T> {
    fun create(): T
}
class MyClass1 {
    companion object Named : Factory<MyClass1> {
        override fun create() : MyClass1 = MyClass1()
    }
}

class MyClass2 {
    companion object : Factory<MyClass2> {
        override fun create() : MyClass2 = MyClass2()
    }
}

fun factoryUsingCompanionDemo() {
    val x = MyClass1()
    val y = MyClass1.Named
    val z = MyClass1 // y and z point to same object
    println("MyClass1()= $x, MyClass1.Named= $y, MyClass1=$z")

    val a = MyClass2()
    val b = MyClass2.Companion
    val c = MyClass2 // b and c point to same object
    println("MyClass2()= $a, MyClass2.Named= $b, MyClass2=$c")
}
/*****************************/
fun main(){
    println("===== companion-demo ====")
    companionDemo()
    println("===== factory-using-companion-demo ====")
    factoryUsingCompanionDemo()
}