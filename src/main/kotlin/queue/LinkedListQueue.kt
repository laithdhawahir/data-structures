package queue

import linkedlist.DoublyLinkList
import linkedlist.LinkedList

/**
 * [Queue] implementation using [LinkedList].
 */
class LinkedListQueue<T>(private val linkedList: LinkedList<T> = DoublyLinkList()) : Queue<T> {
    override val size: Int
        get() = linkedList.size

    override val peek: T
        get() = linkedList.first

    override fun add(value: T) = linkedList.addLast(value)

    override fun pop(): T = linkedList.first.also { linkedList.removeFirst() }

    override fun isEmpty(): Boolean = linkedList.isEmpty()

    override operator fun contains(value: T): Boolean = linkedList.contains(value)

    override fun iterator(): Iterator<T> = linkedList.iterator()
}