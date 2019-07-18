/*************** VISIBILITY-DEMO ***********/
open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4  // public by default
    var e = 10
    get() {
        return field
    }
    private set(value: Int){
        field = value
    }
    /******************/
    open fun mutateE() {
        e = 20;    
    }

    open protected class Nested {
        public val e: Int = 5
    }
}

class Subclass : Outer() {
    // a is not visible
    // b, c and d are visible
    // Nested and e are visible

    override val b = 5   // 'b' is protected

    override fun mutateE() {
        //e = 30; // error setter is invisible as it is private in super
    }
    protected class SubNested: Nested()
}

class Unrelated(o: Outer) {
    //protected class UnrelatedNested: Nested() //compilation Error
    // o.a, o.b are not visible
    // o.c and o.d are visible (same module)
    // Outer.Nested is not visible, and Nested::e is not visible either 
}

/*************** CONSTRUCTOR-VISIBILITY-DEMO ***********/
open class PrivateConstruct private constructor(val a: Int) {
    
    private constructor():this(0){

    }
    companion object Factory{
        fun create(a: Int): PrivateConstruct {
            return PrivateConstruct(a)
        }
        fun create(): PrivateConstruct {
            return PrivateConstruct()
        }
    }
}

//class SubPrivateConstruct: PrivateConstruct(10) //can't access as it is private primary

fun main(){
    println("====== constructor-visibility-demo =======")
    var privateConstructObj = PrivateConstruct.create()
    println("privateConstructObj.a is ${privateConstructObj.a}")
    privateConstructObj = PrivateConstruct.create(20)
    println("privateConstructObj.a is ${privateConstructObj.a}")
    println("====== end =======")
}