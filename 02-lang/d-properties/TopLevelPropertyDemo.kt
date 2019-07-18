/************TOP-LEVEL-PROPERTY****************/
var topLevelProperty : Int = 10 
get() {
    println("accessing top level propery's getter")
    return field
}
private set(value: Int) {
    println("mutating top level property")
    field = value
}

var isSomething : Boolean = false
get() {
    return field
}
internal set(value: Boolean) {
    field = value
};

fun topLevelPropertyDemo() {
    
    var someVar = topLevelProperty;
    println("someVar is $someVar")
    topLevelProperty = 20;
    println("someVar is $someVar")
    println("isSomething = $isSomething")
    isSomething = true
    println("isSomething = $isSomething")

    //Classes of var / property
    println("=== classes-of-local-var-and-prop ===")
    println("topLevelProperty class is ${topLevelProperty::class}")
    println("someVar class is = ${someVar::class}") //Notice that someVar doesn't have a getter
}
/*************************************/
fun main() {
    println("=======top-level-prop-demo===========")
    topLevelPropertyDemo()
    println("==========================")
}