import hashTable.HashTable

fun main(args: Array<String>) {
    val testVal = HashTable<Any, String>(4, loadFactor = 0.75f)


    testVal.put(1, "Rome")
    testVal["hello"] = "похуй"

    for(listes in testVal.list){
        println(listes)
    }
    println()
    println(testVal.remove("gren"))

    for(listes in testVal.list){
        println(listes)
    }

    println()
    for(listes in testVal.list){
        println(listes)
    }

    println()
    testVal.replace(1, "Rome", "хуй")
    for(listes in testVal.list){
        println(listes)
    }


}