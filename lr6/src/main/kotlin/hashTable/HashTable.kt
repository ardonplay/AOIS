package hashTable

import java.util.*
import java.util.Objects.hash
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.math.abs

class HashTable<K, V>(private val capacity: Int = 16) : Map<K, V> {

    private var list: Array<LinkedList<Node<K, V>>?> = Array(size = capacity) { LinkedList() }

    private class Node<K, V>(val key: K, val value: V) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node<*, *>

            return key == other.key
        }

        override fun hashCode(): Int {
            return key?.hashCode() ?: 0
        }
    }

    class Entry<K, V>(override val key: K, override var value: V) : Map.Entry<K, V> {

        override fun toString(): String {
            return "$key=$value"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Entry<*, *>

            return value == other.value
        }

        override fun hashCode(): Int {
            return value?.hashCode() ?: 0
        }
    }

    override var entries: Set<Map.Entry<K, V>> = HashSet(capacity)

    override var keys: Set<K> = HashSet(capacity)
    override val size: Int
        get() = list.size

    override val values: Collection<V> = ArrayList(capacity)

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    private fun getHash(key: K): Int {
        return when (key) {
            Int -> getHash(key as Int)
            String -> getHash(key as String)
            else -> hash(key)
        }
    }

    private fun getHash(key: Int): Int {
        return key % size
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

    fun put(key: K, value: V) {
        val index = getIndex(key)
        val element = list.getOrNull(index)
        if (element != null) {
            keys += key
            entries += Entry(key, value)

            if (!element.contains(Node(key, value))) {
                list[getIndex(key)]?.add(Node(key, value))
            } else {
                list[getIndex(key)]?.remove(Node(key, value))
                list[getIndex(key)]?.add(Node(key, value))
            }
        }
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
        return entries.any{it.value == value}
    }

    override fun containsKey(key: K): Boolean {
        return keys.contains(key)
    }

    override fun toString(): String {
        return entries.toString()
    }
}