class Person public constructor(val name: String, var occuptaion: String = "N/A") {
    //secondary constructor 1
    constructor() : this("Anonymous") {
    }
    //secondary constructor 2
    public constructor(name: String): this(name, "N/A")
    
    override fun toString(): String {
        return "name: ${name}, occupation:${occuptaion}"
    }
}

fun main() {
    var personObj: Person = Person()
    println("personObj is ${personObj}")
    println("personObj.name is ${personObj.name}")
    personObj = Person("Kotlin Dev")
     println("personObj is ${personObj}")
    println("personObj.name is ${personObj.name}")
}
