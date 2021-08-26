package heap

/**
 * Interface for the heap data structure.
 */
interface Heap<T> : Iterable<T> {
    /**
     * Heap size.
     */
    val size: Int

    /**
     * Remove top element and return it value.
     */
    fun poll(): T

    /**
     * Return the top element.
     */
    fun peek(): T

    /**
     * Add new element to the heap.
     */
    fun add(value: T)

    /**
     * Remove element from the heap.
     */
    fun remove(value: T): Boolean

    /**
     * @return true if heap is empty, otherwise, false.
     */
    fun isEmpty(): Boolean = size == 0

    /**
     * @return true if element exists in the heap, otherwise, false.
     */
    operator fun contains(value: T): Boolean
}