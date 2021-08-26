package queue

import dynamicarray.DynamicArray

/**
 * [Queue] implementation using [DynamicArray].
 */
class DynamicArrayQueue<T>() : Queue<T> {
    private val array = DynamicArray<T>()

    override val size: Int
        get() = array.size

    override val peek: T
        get() = array[0]

    override fun add(value: T) = array.append(value)

    override fun pop(): T = array[0].also { array.removeAt(0) }

    override fun isEmpty(): Boolean = array.size == 0

    override operator fun contains(value: T): Boolean = array.contains(value)

    override fun iterator(): Iterator<T> = array.iterator()
}