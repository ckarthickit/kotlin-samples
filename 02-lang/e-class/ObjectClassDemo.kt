package objclassdemo;

/*************** OBJECT EXPRESSION ***********/
interface Behaviour{
    fun behaviour1();
    fun behaviour2();
}

open class Outer {
    val name: String = "Outer"
    fun printName() {
        println(this.name)

        val anonymousIner = object: Behaviour {
            override fun behaviour1() {
                println("behaviour1")
            }
            override fun behaviour2() {
                println("behaviour2")
            }
        }

        anonymousIner.behaviour1();
        anonymousIner.behaviour2();

        val anotherAnonymousInner = object: Outer() {
            fun printExtName() {
                println(this.name)
            }
        }
        anotherAnonymousInner.printExtName()
    }
}
/***********OBJECT EXPRESSION MULTIPLE SUPERTYPES*************/
open class A(val x: Int) {}
interface B{
    val y: Int
    fun printX()
    fun printY()
}

fun mulipleSuperTypeDemo() {
    val localVar = 30
    val obj = object: A(10), B {
        override val y:Int = 20
        override fun printX() {
            println("X is ${this.x}");
        }
        override fun printY() {
            println("X is ${this.y}");
        }
         
        fun printLocalVar() {
            println("localVar is $localVar")
        }
    }
    obj.printX()
    obj.printY()
    obj.printLocalVar()
}
/*********SINGLETON CLASS OR OBJECT DECLARATION****************/
data class Data(val x: Int, val y: Int)
object DataManager {
    private var  data :List<Data> = ArrayList()
    fun registerData(data: Data) {
        this.data += data
    }

    fun getRegisteredData(): List<Data> {
        return this.data
    }
}
/************JAVA FUNCTIONAL INTERFACE USING "OBJECT EXPRESSION"  AND "LAMBDA"***************/
class AnotherOuter {
    val runnable1 = object: Runnable {
        override fun run() {
            println("I can access outer this: ${this@AnotherOuter::class}")
            println("I have my own this: ${this::class}")
        }
    }
    val runnable2 = Runnable {
        println("I can access outer this: ${this@AnotherOuter::class}")
        println("I done't have my own this: ${this::class}")
    }
}

fun demoJavaSAM() {
    val outer = AnotherOuter()
    outer.runnable1.run()
    outer.runnable2.run()
}
/********************************/

fun main() {
    println("===== object-expression-demo ===")
    val outerObj = Outer()
    outerObj.printName()
    println("===== multiple-super-type-demo ===")
    mulipleSuperTypeDemo()
    println("===== object-class-demo ===")
    DataManager.registerData(Data(1,3))
    DataManager.registerData(Data(2,4))
    println("allRegisteredData: ${DataManager.getRegisteredData()}")
    println("===== java-SAM-demo ===")
    demoJavaSAM()
}