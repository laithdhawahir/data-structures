package dynamicarray

/**
 * A dynamic array that resize itself according to the elements count.
 */
class DynamicArray<T>(initCapacity: Int = 2) : Iterable<T> {
    /**
     * The array actual capacity.
     */
    var capacity: Int = initCapacity
        private set

    /**
     * The array elements count.
     */
    var size: Int = 0
        private set

    // In Java primitive arrays can not be initialized from generic,
    // the only way is to create an object array and cast it.
    @Suppress("UNCHECKED_CAST")
    private var array = arrayOfNulls<Any>(capacity) as Array<T?>

    /**
     * @return element at the given index.
     * @throws IndexOutOfBoundsException if index out of array boundaries.
     */
    operator fun get(index: Int): T {
        checkBounds(index)
        return array[index]!!
    }

    /**
     * Set value at element at the given index.
     * @throws IndexOutOfBoundsException if index out of array boundaries.
     */
    operator fun set(index: Int, value: T) {
        checkBounds(index)
        array[index] = value
    }

    /**
     * Append new element to the array tail.
     */
    fun append(value: T) {
        if (size >= capacity) {
            resizeArray(capacity * 2)
        }

        array[size] = value
        size++
    }

    /**
     * Remove element at the given index.
     * @throws IndexOutOfBoundsException if index out of array boundaries.
     */
    fun removeAt(index: Int) {
        checkBounds(index)

        val lastElementIndex = size - 1
        for (curIndex in index until lastElementIndex) {
            array[curIndex] = array[curIndex + 1]
        }

        // Required to make sure element at index size - 1 is garbage collected with no other references.
        array[lastElementIndex] = null

        size--
    }

    /**
     * Remove first element that is equal to the given value.
     */
    fun removeFirst(value: T): Boolean {
        val index = indexOf(value)

        if (index == -1) {
            return false
        }

        removeAt(index)
        return true
    }

    /**
     * @return index of element that is equal to the given value if exists, otherwise, returns -1.
     */
    fun indexOf(value: T): Int {
        for (index in 0 until size) {
            if (array[index] == value) {
                return index
            }
        }

        return -1
    }

    /**
     * @return true if array contains specific element value.
     */
    operator fun contains(value: T): Boolean = indexOf(value) != -1

    private fun checkBounds(index: Int) {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException("$index out of bounds [0 - $size)")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun resizeArray(newCapacity: Int) {
        val newArray = arrayOfNulls<Any>(newCapacity) as Array<T?>
        array.copyInto(
            destination = newArray,
            destinationOffset = 0,
            startIndex = 0,
            endIndex = size
        )

        array = newArray
        capacity = newCapacity
    }

    override fun toString(): String {
        return buildString {
            append("[ ")
            for (index in 0 until size) {
                append(array[index])

                if (index < size - 1) {
                    append(", ")
                }
            }
            append(" ]")
        }
    }

    override fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            private var index = 0

            override fun hasNext(): Boolean = index < size

            override fun next(): T {
                if (index >= size) {
                    throw IndexOutOfBoundsException("index = $index while size is $size")
                }

                return array[index++]!!
            }
        }
    }
}

/**
 * A [DynamicArray] factory method.
 */
fun <T> dynamicArrayOf(vararg values: T): DynamicArray<T> {
    return DynamicArray<T>().also {
        values.forEach { value -> it.append(value) }
    }
}