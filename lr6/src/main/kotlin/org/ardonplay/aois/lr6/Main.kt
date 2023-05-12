package org.ardonplay.aois.lr6

fun main() {
    val hashTable = HashTable<Any, Any>()

    hashTable.put("hi", "hello")
    hashTable.put(1, 5)
    hashTable.put(5, "ogo")
    hashTable["macbook"] = "apple"

    println("Нормальный вывод:\n $hashTable")
    println("Вывод как хранится внутри:\n ${hashTable.toStringAsList()}")

    hashTable.remove(5)
    println("Удален ключ 5:\n ${hashTable.toStringAsList()}")
    hashTable.replace(5, "mde")
    println("Заменили 5 ключ, которого нет, и так как внутри нету данного ключа," +
            " мы не можем заменить:\n ${hashTable.toStringAsList()}")
    hashTable.put(5,"AOIS")
    println("Добавили ключ 5:\n ${hashTable.toStringAsList()}")
    hashTable.replace(5, "AOIS TOP")
    println("Заменили 5 ключ:\n ${hashTable.toStringAsList()}")
    println("Проверка есть ли в таблице ключ \"hi\": ${hashTable.containsKey("hi")}")
    println("Проверка есть ли в таблице значение \"AOIS TOP\": ${hashTable.containsValue("AOIS TOP")}")

    println("Проверка увеличения таблицы:")
    hashTable["Rome"] = "Italy"
    hashTable["Minsk"] = "Belarus"
    hashTable["Washington"] = "USA"
    hashTable["Moscow"] = "Russia"
    hashTable[3] = "E"
    hashTable[2] = "S"
    hashTable[15] = "IS"
    println("Сейчас в таблице 11 элементов, при добавлении 12го таблица увеличится:\n" +
            "До добавления:\n" +
            hashTable.toStringAsList())
    hashTable[7] = "T"
    println("После добавления:\n" +
            hashTable.toStringAsList())

    println("Очищаем таблицу:")
    hashTable.clear()
    println(hashTable.toStringAsList())
}