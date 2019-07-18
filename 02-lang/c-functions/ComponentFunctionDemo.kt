/************* COMPONENT-FUNCTION-DEMO ***********/
class Name(firstNameVal:String, secondNameVal:String, ageVal: Int){
    val firstName: String = firstNameVal
    val secondName: String = secondNameVal
    val age : Int = ageVal

    operator fun component1(): String {
        return firstName;
    }
    operator fun component2(): String {
        return secondName;
    }
    operator fun component3(): Int {
        return age;
    }
}
/***************** COMPONENT-FUNCTION-AS EXTENDSION-DEMO *******/
class Animal(val breed: String, val age: Int)
operator fun Animal.component1(): String {
    return breed
}
operator fun Animal.component2(): Int {
    return age
}

fun main(){
    println("==== component-function-demo ====")
    val someNameObj = Name("Johnny","Walker",50)
    val (personFName , personSName , personAge) = someNameObj;
    println("personFName is $personFName, personSName is $personSName, personAge is $personAge")
    println("==== component-extension-function-demo ====")
    val someAnimalObj = Animal("Leopard", 30)
    val (animalName, animalAge) = someAnimalObj
    println("animalName is $animalName, animalAge is $animalAge")
}