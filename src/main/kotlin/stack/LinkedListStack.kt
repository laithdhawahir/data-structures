package stack

import linkedlist.LinkedList
import linkedlist.SinglyLinkList

/**
 * Implementation of the [Stack] using [LinkedList]
 */
class LinkedListStack<T>(private val linkedList: LinkedList<T> = SinglyLinkList()) : Stack<T> {
    override val size: Int
        get() = linkedList.size

    override val peek: T
        get() = linkedList.first

    override fun push(value: T) = linkedList.addFirst(value)

    override fun pop(): T {
        val top = peek

        linkedList.removeFirst()

        return top
    }

    override fun isEmpty(): Boolean = linkedList.isEmpty()

    override operator fun contains(value: T): Boolean = linkedList.contains(value)

    override fun iterator(): Iterator<T> = linkedList.iterator()
}