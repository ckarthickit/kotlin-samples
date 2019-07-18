import kotlin.random.Random
fun main(args: Array<String>) {
    testRandom()
    if(args.size > 0) {
        val timeToken  = args[0].split(":")
        if(timeToken.size > 0){
            val hours = try {timeToken[0].toInt()}catch(e: NumberFormatException){-1};
            if(hours == -1) {
                println("Invalid format")

            }else if(hours < 12) {
                println("Good Morning, Kotlin")
            }else {
                println("Good Night, Kotlin")
            }
        }
    }else {
        println("Please pass a valid time")
    }
    
}

fun testRandom() {
    val random1 = Random.nextInt()
    val random2 = {Random.nextInt()}
    println("random1 is $random1")
    println("random2 is ${random2()}")
    println("random1 is $random1")
    println("random2 is ${random2()}")
}