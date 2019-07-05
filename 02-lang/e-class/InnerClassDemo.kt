package innerclass;
/*************NESTED STANDALONE CLASS***************/
class Outer {
    val x = 1
    fun method() = 1
    class Inner {
        fun method() = 2
    }
} 


/**********INNER CLASS ACESSING OUTER CONTEXT*************/
open class Foo {
    open fun printMsg() {
        println("foo-print")
    }
}

class Bar(barX :Int = 10): Foo() {
    var barX: Int;
    init{
        this.barX = barX //barX parameter from primary constructor accessed here.
    }
    override fun printMsg() {
        println("bar-print")
    }
    inner class Baz {
        fun printMsg() {
            super@Bar.printMsg()
            this@Bar.printMsg()
        }
    }
}
/********ANONYMOUS_INNER_CLASS***************/
interface Runnable {
    fun run(): kotlin.Unit
}
/**********************************************/

fun main() {
    val bar = Bar(20);
    println("bar.barX = ${bar.barX}")
    val baz = bar.Baz()
    baz.printMsg()
    println("==========")
    println("Outer.Inner().method(): ${Outer.Inner().method()}")
    println("==========")
    val runnable = object : Runnable {
        override fun run() {
            println("Running anonymously")
        }
    }
    println("runnable run: ${runnable.run()}")
    val anotherRunnable = Runnable {
        println("Running anonymously in lambda")
    }
    println("anotherRunnable.run: ${anotherRunnable.run()}")
    val adHocClass = object {
        val x = 100;
        val y = 278;
    }
    println("adHocClass.x= ${adHocClass.x}, adHocClass.y= ${adHocClass.y}")
}