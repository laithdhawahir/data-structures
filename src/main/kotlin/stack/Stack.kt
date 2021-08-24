package stack

/**
 * Interface for the stack data structure.
 */
interface Stack<T> : Iterable<T> {
    /**
     * Stack size.
     */
    val size: Int

    /**
     * stack top element.
     */
    val peek: T

    /**
     * push element to the top of the stack.
     */
    fun push(value: T)

    /**
     * @return top element and remove it.
     */
    fun pop(): T

    /**
     * @return true if stack is empty, otherwise, false.
     */
    fun isEmpty(): Boolean

    /**
     * @return true if the stack contains the given value.
     */
    operator fun contains(value: T): Boolean

}