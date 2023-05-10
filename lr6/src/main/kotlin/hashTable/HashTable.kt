package hashTable

import java.util.Objects.hash

class HashTable<K, V>(private val capacity: Int = 16): Map<K, V>{
    private val nodes = ArrayList<V?>(capacity).apply {
        for (i in 0 until capacity) {
            add(null)
        }
    }
    override val entries: Set<Map.Entry<K, V>>
        get() = TODO("Not yet implemented")
    override val keys: Set<K>
        get() = TODO("Not yet implemented")
    override val size: Int
        get() = nodes.size
    override val values: Collection<V>
        get() = TODO("Not yet implemented")

    override fun isEmpty(): Boolean {
        return nodes.size == 0
    }
    private fun getHash(key: K): Int{
       return hash(key) % capacity
    }

    fun put(key: K, value: V){
        nodes.add(getHash(key), value)
    }
    override fun get(key: K): V? {
        return nodes.getOrNull(getHash(key))
    }

    override fun containsValue(value: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsKey(key: K): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return nodes.toString()
    }
}