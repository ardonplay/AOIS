package hashTable

import java.util.*
import java.util.Objects.hash
import kotlin.math.abs

class HashTable<K, V>(private var capacity: Int = 1 shl 4, private val loadFactor: Float = 0.75f) : Map<K, V> {

    private val defaultCapacity = capacity
    var list: Array<LinkedList<Entry<K, V>>?> = Array(size = capacity) { LinkedList() }

    private var indexCounter = 0

    class Entry<K, V>(override val key: K, override var value: V) : Map.Entry<K, V> {

        override fun toString(): String {
            return "$key=$value"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Entry<*, *>

            return key == other.key
        }

        override fun hashCode(): Int {
            return key?.hashCode() ?: 0
        }
    }


    override val entries: Set<Map.Entry<K, V>>
        get() {
            val result = HashSet<Map.Entry<K, V>>(capacity)
            for (nodes in list) {
                if (nodes != null) {
                    for (node in nodes) {
                        result.add(Entry(node.key, node.value))
                    }
                }
            }
            return result
        }


    override val keys: Set<K>
        get() {
            val result = HashSet<K>(capacity)
            for (nodes in list) {
                if (nodes != null) {
                    for (node in nodes) {
                        result.add(node.key)
                    }
                }
            }
            return result
        }
    override val size: Int
        get() = indexCounter

    override val values: Collection<V> = ArrayList(capacity)

    private fun resize() {
        capacity *= 2
        val newList: Array<LinkedList<Entry<K, V>>?> = Array(size = capacity) { LinkedList() }

        for (index in list.indices) {
            val nodes = list.getOrNull(index)
            if (nodes != null) {
                for (node in nodes) {
                    newList[getIndex(node.key)]?.add(node)
                }
            }
        }
        list = newList
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    private fun getHash(key: K): Int {
        return when (key) {
            is Int -> getHash(key as Int)
            is String -> getHash(key as String)
            else -> abs(hash(key))
        }
    }

    private fun getHash(key: Int): Int {
        return abs(key % capacity)
    }

    private fun getHash(key: String): Int {
        var hash = 0
        for (symbol in key) {
            hash = (hash shl 5) - hash + (symbol.code)
        }
        return abs(hash)
    }

    private fun getIndex(key: K): Int {
        return getHash(key) % capacity
    }

    fun put(key: K, value: V): V? {
        val index = getIndex(key)
        val element = list.getOrNull(index)
        val previosVal: V? = get(key)
        if (element != null) {
            if (!element.contains(Entry(key, value))) {
                element.add(Entry(key, value))
                indexCounter++
            } else {
                element.remove(Entry(key, value))
                element.add(Entry(key, value))
            }
        }
        if ((indexCounter / capacity.toFloat()) >= loadFactor) {
            resize()
        }
        return previosVal
    }

    fun clear() {
        capacity = defaultCapacity
        list = Array(size = capacity) { LinkedList() }
        indexCounter = 0
    }

    fun remove(key: K): V? {
        val value: V? =
            list[getIndex(key)]?.stream()?.filter { it.key == key }?.findFirst()?.orElse(null)?.value
        list[getIndex(key)]?.removeAll { it.key == key }
        indexCounter--
        return value
    }

    fun replace(key: K, oldValue: V, newValue: V): Boolean {
        val curValue: Any? = get(key)
        if (curValue != oldValue || curValue == null && !containsKey(key)) {
            return false
        }
        put(key, newValue)
        return true
    }
    fun replace(key: K, value: V): V? {
        var curValue: V?
        if (get(key).also { curValue = it } != null || containsKey(key)) {
            curValue = put(key, value)
        }
        return curValue
    }

    override fun get(key: K): V {
        val element = list.getOrNull(getIndex(key))
        if (element != null) {
            for (node in element) {
                if (node.key == key) {
                    return node.value
                }
            }
        }
        throw NullPointerException("map dont have this key!")
    }

    override fun containsValue(value: V): Boolean {
        return entries.any { it.value == value }
    }

    override fun containsKey(key: K): Boolean {
        return keys.contains(key)
    }

    override fun toString(): String {
        return entries.toString()
    }

    operator fun set(key: K, value: V) {
        put(key, value)
    }
}