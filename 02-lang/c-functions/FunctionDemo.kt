package func;
import kotlin.collections.*

/*********LAMBDA ARGUEMENT****************/
fun printDelegate(value: String, printer: (String) -> Unit): Unit {
    //printer.invoke(value);
    printer(value);
}
/******VARARG TEST***************/
fun <T> printValues(vararg values: T) {
    for(v in values) {
        print("$v\t")
    }
    println()

    //T is an out parameter and hence "values" cannot be used as consumer
    //values.fill(element = 0, fromIndex=0 , toIndex = values.size) 

    val arrVals: Array<out T>  = values
    //T is an out parameter and hence "arrVals" cannot be used as consumer
    //arrVals.fill(element = 0, fromIndex=0 , toIndex = arrVals.size) //
}
/*******************************/

fun main() {
    printDelegateTest();
    varArgTest()
}

fun printDelegateTest() {
    println("==== print-delegate-test ===")
    printDelegate("Gonna Print Something", ::println)
    printDelegate("Gonna Print Something") { it -> println(it)}
    printDelegate("Gonna Print Something", printer = { it -> println(it)}) 
}

fun varArgTest() {
    println("==== vararg-test ===")
    printValues(1, 2, 3, 4) //pass varargs as normal comma seperted params
    
    val arrayValues = arrayOf(4, 5, 6, 7)
    printValues(1, 2, 3, *arrayValues, 8, 9 ) //pass normal values + pass array using spread operator
}