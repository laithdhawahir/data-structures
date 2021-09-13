package map

import java.util.*
import kotlin.math.abs

/**
 * Implementation of the [Map<T,R>] using a separate chaining hash table.
 */
class SeparateChainingHashMap<T, R>(initialCapacity: Int = 8) : Map<T, R> {
    private var elementsCount = 0
    private var hashTable = MutableList<LinkedList<MapEntry<T, R>>>(initialCapacity) { LinkedList() }

    override val size: Int
        get() = elementsCount

    override val keys: List<T>
        get() = hashTable.filter { it.isNotEmpty() }.flatten().map { it.key }

    override val values: List<R>
        get() = hashTable.filter { it.isNotEmpty() }.flatten().map { it.value }

    override val entries: List<MapEntry<T, R>>
        get() = hashTable.filter { it.isNotEmpty() }.flatten()

    override fun iterator(): Iterator<MapEntry<T, R>> = hashTable.filter { it.isNotEmpty() }.flatten().iterator()

    override fun put(key: T, value: R) {
        val foundEntry = find(key)

        if (foundEntry != null) {
            // if found, override the value
            foundEntry.value = value
        } else {
            // if not found add new entry
            elementsCount++
            hashTable[hashCodeOf(key)].add(SimpleMapEntry(key, value, key.hashCode()))
        }

        // If the hash table is full, resize it.
        if (elementsCount >= hashTable.size) {
            resize(hashTable.size * 2)
        }
    }

    override fun remove(key: T): Boolean {
        if (hashTable[hashCodeOf(key)].removeIf { it.key == key }) {
            elementsCount--
            return true
        }

        return false
    }

    override fun contains(key: T): Boolean = get(key) != null

    override fun get(key: T): R? {
        return find(key)?.value
    }

    override fun set(key: T, value: R) = put(key, value)

    private fun find(key: T): SimpleMapEntry<T, R>? {
        val keyHashCode = key.hashCode()
        return hashTable[hashCodeOf(key)]
            .map { it as SimpleMapEntry<T, R> }
            .find { it.hash == keyHashCode && it.key == key }
    }

    private fun resize(newCapacity: Int) {
        val entries: List<SimpleMapEntry<T, R>> = entries as List<SimpleMapEntry<T, R>>

        hashTable = MutableList(newCapacity) { LinkedList() }
        for (entry in entries) {
            hashTable[hashCodeToIndex(entry.hash)].add(entry)
        }
    }

    private fun hashCodeOf(key: T): Int {
        return hashCodeToIndex(key.hashCode())
    }

    private fun hashCodeToIndex(hashCode: Int): Int = abs(hashCode) % hashTable.size

    private class SimpleMapEntry<T, R>(override var key: T, override var value: R, val hash: Int) :
        MapEntry<T, R>
}