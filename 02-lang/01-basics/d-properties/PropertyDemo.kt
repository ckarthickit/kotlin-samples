package proptest;
import kotlin.reflect.KProperty;
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
/**************************************/
class LazyClass {
    val lazyValue: String by lazy{
        println("computed")
        "LazyValue"
    }
}
/**************************************/

fun main() {
    lateInitTest()
    propertyDelegateTest()
    lazyPropertyDelegateTest()
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
     println(lazyObj.lazyValue)
     println(lazyObj.lazyValue)
}