package map

import java.util.Objects.hash
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Implementation of the [Map<T,R>] using a separate chaining hash table.
 */
class OpenAddressingHashMap<T, R> : Map<T, R> {
    private companion object {
        const val INIT_CAPACITY = 7 // I should be a prime number
        const val LOAD_FACTOR = 0.75
    }

    private var elementsCount = 0

    private var threshold: Int = (INIT_CAPACITY * LOAD_FACTOR).toInt()
    private var hashTable = Array<OpenAddressingMapEntry?>(INIT_CAPACITY) { null }

    override val size: Int
        get() = elementsCount

    override val keys: List<T>
        get() = hashTable.filterNotNull().filter { !isTombstone(it) }
            .map { it as OpenAddressingMapEntry.SimpleMapEntry<T, R> }.map { it.key }

    override val values: List<R>
        get() = hashTable.filterNotNull().filter { !isTombstone(it) }
            .map { it as OpenAddressingMapEntry.SimpleMapEntry<T, R> }.map { it.value }

    override val entries: List<MapEntry<T, R>>
        get() = hashTable.filterNotNull().filter { !isTombstone(it) }
            .map { it as OpenAddressingMapEntry.SimpleMapEntry<T, R> }

    override fun iterator(): Iterator<MapEntry<T, R>> = hashTable.filterNotNull().filter { !isTombstone(it) }
        .map { it as OpenAddressingMapEntry.SimpleMapEntry<T, R> }.iterator()

    override fun put(key: T, value: R) {
        if (elementsCount > threshold) {
            resize()
        }

        val indexPair = find(key)
        if (indexPair.first == IndexType.NEW_ENTRY) {
            elementsCount++
        }

        hashTable[indexPair.second] = OpenAddressingMapEntry.SimpleMapEntry(key, value, key.hashCode())
    }


    override fun remove(key: T): Boolean {
        val indexPair = find(key)

        return if (indexPair.first == IndexType.EXISTING_ENTRY) {
            hashTable[indexPair.second] = OpenAddressingMapEntry.TombstoneMapEntry
            elementsCount--
            true
        } else {
            false
        }
    }

    override fun contains(key: T): Boolean = get(key) != null

    override fun get(key: T): R? {
        val indexPair = find(key)

        if (indexPair.first == IndexType.NEW_ENTRY) {
            return null
        }

        val entry = hashTable[indexPair.second] as OpenAddressingMapEntry.SimpleMapEntry<T, R>
        return entry.value
    }

    override fun set(key: T, value: R) = put(key, value)

    private fun find(key: T): Pair<IndexType, Int> {
        val keyHashCode = key.hashCode()
        var index = hashCodeToIndex(keyHashCode)
        var iteration = 0
        var entry: OpenAddressingMapEntry? = hashTable[index]
        var firstTombstoneIndex = -1

        while (entry != null && !entryMatch(key, keyHashCode, index)) {
            if (firstTombstoneIndex == -1 && isTombstone(entry)) {
                firstTombstoneIndex = index
            }

            index = hashCodeToIndex(keyHashCode + doubleHashProbing(iteration, keyHashCode))
            entry = hashTable[index]
            iteration++
        }

        return when (entry) {
            null -> {
                // Optimization to reuse the tombstone if we encountered it first before the null entry,
                if (firstTombstoneIndex != -1) {
                    IndexType.NEW_ENTRY to firstTombstoneIndex
                } else {
                    IndexType.NEW_ENTRY to index
                }
            }
            else -> {
                // Optimization to replace the entry with the first tombstone to reduce the number of steps required in
                // retrieving the entry again.
                if (firstTombstoneIndex != -1) {
                    hashTable[firstTombstoneIndex] = entry
                    hashTable[index] = null

                    IndexType.EXISTING_ENTRY to index
                } else {
                    IndexType.EXISTING_ENTRY to index
                }
            }
        }
    }

    private fun resize() {
        val oldEntries = entries

        hashTable = Array(nextCapacity()) { null }
        threshold = (LOAD_FACTOR * hashTable.size).toInt()

        for (entry in oldEntries) {
            val indexPair = find(entry.key)
            hashTable[indexPair.second] = OpenAddressingMapEntry.SimpleMapEntry(entry.key, entry.value, entry.hashCode())
        }
    }

    private fun nextCapacity(): Int {
        var newCapacity = hashTable.size * 2

        while (!isPrime(newCapacity)) {
            newCapacity++
        }

        return newCapacity
    }

    private fun isPrime(value: Int): Boolean {
        for (i in 2..sqrt(value.toDouble()).toInt()) {
            if (value % i == 0) {
                return false
            }
        }

        return true
    }

    private fun hashCodeToIndex(hashCode: Int): Int = abs(hashCode % hashTable.size)

    private fun doubleHashProbing(iteration: Int, keyHashCode: Int): Int {
        if (iteration == 0) {
            return 0
        }

        return iteration * hash(keyHashCode)
    }

    private fun isTombstone(entry: OpenAddressingMapEntry): Boolean {
        return entry === OpenAddressingMapEntry.TombstoneMapEntry
    }

    private fun entryMatch(key: T, hashCode: Int, index: Int): Boolean {
        val entry: OpenAddressingMapEntry = hashTable[index] ?: return false

        if (isTombstone(entry)) {
            return false
        }
        entry as OpenAddressingMapEntry.SimpleMapEntry<T, R>

        return entry.hash == hashCode && entry.key == key
    }

    private sealed interface OpenAddressingMapEntry {
        class SimpleMapEntry<T, R>(override var key: T, override var value: R, val hash: Int) :
            MapEntry<T, R>, OpenAddressingMapEntry

        object TombstoneMapEntry : OpenAddressingMapEntry
    }

    private enum class IndexType {
        NEW_ENTRY,
        EXISTING_ENTRY
    }
}