package proptest;
import kotlin.reflect.KProperty;
import kotlin.properties.Delegates;

/**************************************/
class TestSubject : Any() {
    fun perform() {
        println("performing")
    }
}
class MyTest {
    lateinit var subject: TestSubject
    fun setup() {
        subject = TestSubject()
    }
    fun test() {
        if(::subject.isInitialized) {
            subject.perform()
        }else {
            println("subject not initialized yet")
        }
    }
}
/**************************************/
class Person() {
    var name: String by NameDelegate()
}
class NameDelegate: Any() {
    private val _propHolder : MutableMap<String,String> = HashMap()
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
        return "${prop.name}: ${_propHolder.get(prop.name)?:"no-data"}"
    }
    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String): Unit {
        _propHolder[prop.name] = value
    }
}
/************LAZY PROPERTY DELEGATE*******************/
class LazyClass {
    val lazyProperty: String by lazy{
        println("computed")
        "LazyProperty"
    }
}
/************OBSERVABLE PROPERTY DELEGATE*****************/
class ObservableProp {
    
    //Uses lambda notation
    var observableProp : String by Delegates.observable("<no-data>") {
        _, old, new -> println("old is ${old}, new is ${new}")
    }

    //Uses Anonymous Function notation
    var observableProp2 : String by Delegates.observable<String>("<no-data>", onChange = fun(prop: KProperty<*>, old: String, new: String){
        println("old is ${old}, new is ${new}")
    })
}
/***********VETOABLE PROPERTY DELEGATE*************/

class VetoableProp {
    var vetoableProp: Int by Delegates.vetoable(0) {
        _, oldValue, newValue -> newValue > oldValue
    }
}
/************MAP AS PROPERTY DELEGATE****************/
class User(val map: Map<String,Any?>) {
    val name: String by map
    val age : Int by map
}
/************LOCAL DELEGATED PROPERTIES***************/
class Foo {
    init {
        println("new Foo")
    }
    fun doSomething() {
        println("foo is doing something")
    }
}
fun localPropertyWithDelegate(computeFoo: ()->Foo) {
    val memoizedFoo by lazy(computeFoo)
    memoizedFoo.doSomething(); //accessing memoizedFoo
}
/**************************************/
fun main() {
    lateInitTest()
    propertyDelegateTest()
    lazyPropertyDelegateTest()
    observablePropTest()
    vetoablePropTest()
    localDelegatedPropTest()
}

fun lateInitTest() {
    println("\n===== late-init-test====")
    val test = MyTest()
    test.test()
    test.setup()
    test.test()
}

fun propertyDelegateTest() {
    println("\n===== property-delegate-test====")
    val person = Person()
    println("${person.name}")
    person.name = "KotlinDev"
    println("${person.name}")
}

fun lazyPropertyDelegateTest() {
     println("\n===== lazy-property-delegate-test====")
     val lazyObj = LazyClass()
     println(lazyObj.lazyProperty)
     println(lazyObj.lazyProperty)
}

fun observablePropTest() {
    println("\n===== observable-property-delegate-test====")
    val observalObj = ObservableProp()
    observalObj.observableProp = "New Value"
    println("observalObj.observableProp is ${observalObj.observableProp}")
    observalObj.observableProp2 = "New Value2"
    println("observalObj.observableProp2 is ${observalObj.observableProp2}")
}

fun vetoablePropTest() {
    println("\n===== vetoable-property-delegate-test====")
    val vetoablePropObj = VetoableProp()
    vetoablePropObj.vetoableProp = 10;
    println("after vetoablePropObj.vetoableProp=10 is ${vetoablePropObj.vetoableProp}")
    vetoablePropObj.vetoableProp = 11;
    println("after vetoablePropObj.vetoableProp=11 is ${vetoablePropObj.vetoableProp}")
    vetoablePropObj.vetoableProp = 9;
    println("after vetoablePropObj.vetoableProp=9 is ${vetoablePropObj.vetoableProp}")
}

fun localDelegatedPropTest() {
    println("\n===== local-delegated-property-test====")
    var _backingFoo : Foo? = null
    val lateInitFoo = {
        if(_backingFoo == null) _backingFoo = Foo()
        _backingFoo as Foo
    }
    val alwasyNewFoo = {Foo()}

    localPropertyWithDelegate(lateInitFoo)
    localPropertyWithDelegate(lateInitFoo)
    localPropertyWithDelegate(lateInitFoo)
    
    localPropertyWithDelegate(alwasyNewFoo)
    localPropertyWithDelegate(alwasyNewFoo)
    localPropertyWithDelegate(alwasyNewFoo)
}