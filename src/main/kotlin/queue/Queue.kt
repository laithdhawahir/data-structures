package queue

/**
 * Interface for the queue data structure.
 */
interface Queue<T> : Iterable<T> {
    /**
     * Queue size.
     */
    val size: Int

    /**
     * Queue first element.
     */
    val peek: T

    /**
     * Add element to the end of the queue.
     */
    fun add(value: T)

    /**
     * @return first element and remove it.
     */
    fun pop(): T

    /**
     * @return true if queue is empty, otherwise, false.
     */
    fun isEmpty(): Boolean

    /**
     * @return true if the queue contains the given value.
     */
    operator fun contains(value: T): Boolean
}