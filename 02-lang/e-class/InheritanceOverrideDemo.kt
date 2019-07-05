/****************CLASS_INHERITANCE****************/
open class Base {
    open fun openFun() {
        println("Base::openFun")
    }
    /*final*/ fun closedFun() {
        println("Base::closedFun")
    }
}
open class Derived: Base() {
    override fun openFun() {
        super.openFun()
        println("Derived::openFun")
    }

    //Un-commenting the below code will result in compilation error as non-open functions are final by default.
    /*fun closedFun() {
        println("Base::closedFun")
    }*/
}

class DeriveDerived: Derived() {

    //functions with override keyword are open by default
    override fun openFun() {
        super.openFun()
        println("DeriveDerived::openFun")
    }
}
/*************INTERFACE_IMPL**************/

interface Able{} //interface and it's methods are open by default.

class ConcreteAble: Able {

}
/*************PROPERTY_OVERRIDE**************/

open class Foo {
    open val x = 10;
    open var y: Int = 1
      get(){
        return field
      }
      set(value) {
          field = value
      }
}

class Bar(override var x:Int = 1): Foo() {
    //overriding from val to var
    //override var x = 1;

    //overriding getters and setters
    override var y = 0
            get(){
                println("get_trace(y) ${field}")
                return field;
            }
            set(value) {
                field = value
                println("set_trace(y) ${field}")
            }
}

/*************MULTIPLE INHERITANCE**************/
open class ABase {
    open fun printMsg() {
        println("ABase")
    }
}
interface AAble{
    /*open*/ fun printMsg() {
        println("AAble")
    }
}

class ADerived: ABase(), AAble {

    override fun printMsg() {
        super<ABase>.printMsg()
        super<AAble>.printMsg()
        println("ADerived")
    }
}
/***************************************/

fun main() {
    var obj = Derived()
    obj.openFun()
    println();
    obj.closedFun()
    println("==========")
    obj = DeriveDerived()
    obj.openFun()
    println();
    obj.closedFun()
    println("==========")
    println("===fooBar-Demo===")
    var fooBar = Bar()
    fooBar.x = 10;
    println("fooBar::class = ${fooBar::class}, fooBar.x= ${fooBar.x}")
    fooBar.y = 20;
    println("fooBar::class = ${fooBar::class}, fooBar.y= ${fooBar.y}")
    println("===ADerived inheriting same method from multiple definitions====")
    var aDerived = ADerived()
    aDerived.printMsg()
}