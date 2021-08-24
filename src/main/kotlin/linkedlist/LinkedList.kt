package linkedlist

interface LinkedList<T> : Iterable<T> {
    /**
     * Number of elements in the list.
     */
    val size: Int

    /**
     * Adds value as the first element in the list.
     */
    fun addFirst(value: T)

    /**
     * Adds value as last element in the list.
     */
    fun addLast(value: T)

    /**
     * @return true if it successfully removed the first element from the list, otherwise, false.
     */
    fun removeFirst(): Boolean

    /**
     * @return true if it successfully removed the last element from the list, otherwise, false.
     */
    fun removeLast(): Boolean

    /**
     * @return true if it find and remove the given element value, otherwise, false.
     */
    fun remove(value: T): Boolean

    /**
     * @return reference to the element with a value equal to the given value.
     */
    fun find(value: T): Node<T>?

    /**
     * Remove all elements in the list.
     */
    fun clear()

    /**
     * @return true if list is empty, otherwise, false.
     */
    fun isEmpty(): Boolean

    /**
     * The first element in the list.
     * @throws IndexOutOfBoundsException if list is empty.
     */
    val first: T

    /**
     * The last element in the list.
     * @throws IndexOutOfBoundsException if list is empty.
     */
    val last: T

    /**
     * @return true if the list contains the given value.
     */
    operator fun contains(value: T): Boolean = find(value) != null

    /**
     * @return element at the given index.
     * @throws IndexOutOfBoundsException if index is out of bounds.
     */
    operator fun get(index: Int): T

    /**
     * @return sets given value to the element at the given index.
     * @throws IndexOutOfBoundsException if index is out of bounds.
     */
    operator fun set(index: Int, value: T)
}