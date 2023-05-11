import hashTable.HashTable
fun main(args: Array<String>) {
    println("Hello World!")

    val testVal = HashTable<String, String>()

    testVal.put("hi", "hello")
    println(testVal["hi"])
    println(testVal.containsValue("hello"))
}