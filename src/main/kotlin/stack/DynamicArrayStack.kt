package stack

import dynamicarray.DynamicArray
import linkedlist.LinkedList

/**
 * Implementation of the [Stack] using [LinkedList]
 */
class DynamicArrayStack<T>() : Stack<T> {
    private val array: DynamicArray<T> = DynamicArray()

    override val size: Int
        get() = array.size

    override val peek: T
        get() = array[size - 1]

    override fun push(value: T) {
        array.append(value)
    }

    override fun pop(): T {
        return peek.also { array.removeAt(size - 1) }
    }

    override fun isEmpty(): Boolean = array.size == 0

    override operator fun contains(value: T): Boolean = array.contains(value)

    override fun iterator(): Iterator<T> = array.iterator()
}