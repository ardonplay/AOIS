import hashTable.HashTable
fun main(args: Array<String>) {
    println("Hello World!")

    val testVal = HashTable<Int, String>()

    testVal.put(10, "Hi")
    testVal.put(10, "Hг")
    println(testVal[10])
    println(testVal)
}