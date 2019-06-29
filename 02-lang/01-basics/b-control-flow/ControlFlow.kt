import kotlin.random.Random;
import kotlin.collections.Iterable;

fun main(args: Array<String>) : kotlin.Unit {
    println("\n=== assignment-statement-test ===")
    assignmentStatementTest()
    println("\n=== control-strucutre-epression-test ===")
    controlStructureExpressionTest()
}

fun assignmentStatementTest(): kotlin.Unit {
    var a = 10;
    //println("value of assignments is ${a=20}"); //compilation error "assignments are not expressions".
}

fun controlStructureExpressionTest(): kotlin.Unit {
    println("\n=== if-expression-test===")
    ifExpressionTest()
    println("\n=== when-expression-test===")
    whenExpressionTest()
    println("\n=== loop-test===")
    loopTest()
}

fun ifExpressionTest(): kotlin.Unit {
    //if expression
    var rawString = """
    |if(10>20) "10 is greater" else "20 is greater"
    """.trimMargin();
    println("\nvalue of controlstructure($rawString) is ${if(10 > 20) "10 is greater" else "20 is greater"}")

   //if expression returning kotlin.Unit
   var unitRetVal = if (20 > 10) println("20 is greater") else println("10 is greater")
   println("unitRetVal is $unitRetVal")
   println("unitRetVal is kotlin.Unit? ${unitRetVal === kotlin.Unit}") //even identitiy (or) referential equality will be true

  //if expression returning the last expresson
  var intRetVal = if (20 > 10) {
      println("20 is greater") 
      20
    }else {
      println("10 is greater")
      10
    }
   println("intRetVal is ${intRetVal}")

   //if expression with else-if clause
   println("maxOf3Nos(2, 9, 7) is ${maxOf3Nos(2, 9, 7)}")
   println("maxOf3Nos(8, 7, 6) is ${maxOf3Nos(8, 7, 6)}")
   println("maxOf3Nos(4, 2, 19) is ${maxOf3Nos(4, 2, 19)}")
}

fun maxOf3Nos(num1: Int, num2: Int, num3: Int): Int {
    return if(num1 > num2 && num1 > num3) num1 else if(num2 > num3) num2 else num3
}

fun whenExpressionTest(): kotlin.Unit {
    //when expression  
    println("===========")
    println("maxOf3Nos2(2, 9, 7) is ${maxOf3Nos2(2, 9, 7)}")
    println("maxOf3Nos2(8, 7, 6) is ${maxOf3Nos2(8, 7, 6)}")
    println("maxOf3Nos2(4, 2, 19) is ${maxOf3Nos2(4, 2, 19)}")
    //when expression assigned to a variable
    println("===========")
    println("isOdd(1027) ? ${isOdd(1027)}")
    println("isOdd(96) ? ${isOdd(96)}")
    //when with multiple cases having same logic
    println("===========")
    println("isZeroOr1(1)? ${isZeroOr1(1)}")
    println("isZeroOr1(5)? ${isZeroOr1(5)}")

    //when case having "in"
    println("===========")
    println("isWithin10(9)? ${isWithin10(9)}")
    println("isWithin10(19)? ${isWithin10(19)}")

    //when case having "is"
    println("===========")
    println("stringify(\"something\") is: ${stringify("something")}")
    println("stringify(1) is: ${stringify(1)}")
    println("stringify(Success() is: ${stringify(Success())}")
    println("stringify(Busy() is: ${stringify(Busy())}")
    println("stringify(Failure() is: ${stringify(Failure())}")

    //capturing when subject in a variable
    println("randomNumberRange is: ${randomNumberRange()}")
    println("randomNumberRange is: ${randomNumberRange()}")
    println("randomNumberRange is: ${randomNumberRange()}") 
}

fun maxOf3Nos2(num1: Int, num2: Int, num3: Int): Int = when {
    num1 > num2 && num1 > num3 -> num1
    num2 > num3 -> num2
    else -> num3
}
 
fun isOdd(num1: Int): Boolean {
    val retVal = when {
        (num1 and 1) == 1 -> true
        else -> false
    }
    return retVal
}

fun isZeroOr1(num: Int): Boolean = when(num) {
    0,1 -> true
    else -> false
}

fun isWithin10(num: Int): Boolean = when(num) {
    in 1..10 ->  true
    else -> false 
}

class Success {}
class Busy{}
class Failure{}
fun stringify(arg: Any): String = when(arg) {
    is Success -> "Success"
    is Busy -> "Busy"
    is Failure -> "Failure"
    !is String -> arg.toString()
     else -> ""
}


fun randomNumberRange(): String = when (val num = nextRandomInt()) {
    in 0..9 -> "${num} is within 0..9"
    in 10..14 -> "${num} is within 10..14"
    else -> "${num} is within 15..20"
}

fun nextRandomInt(): Int {
    return Random.nextInt(until = 20);
}

/****************** LOOPS *****************/
fun loopTest(): kotlin.Unit {
  iteratorDemo()
  println("==============")
}

fun iteratorDemo() {
  var iterator1: IntRange? = 1..10 // belongs to IntRange which is an IntProgression which is an Iterable
  println("iterator1 is IntRange = ${iterator1 is IntRange}")
  var iterator2: IntProgression? = 6 downTo 0 step 2 //IntProgression which is an Iterable
  println("iterator2 is IntProgression = ${iterator2 is IntProgression}")
  var iterator3: Array<Int>? = Array(5){ it -> it}
  println("iterator3 is Array<*> = ${iterator3 is Array<*>}")
  println("iterator3.indices is IntRange = ${iterator3?.indices is IntRange}")
  var iterator4: Iterable<IndexedValue<Int>>? = iterator3?.withIndex()
  println("iterator4 is Iterable<*> = ${iterator4 is Iterable<*>}")
}
