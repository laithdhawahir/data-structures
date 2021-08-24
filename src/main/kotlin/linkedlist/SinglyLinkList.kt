package linkedlist

/**
 * A singly list implementation for the [LinkedList] interface
 */
class SinglyLinkList<T>() : LinkedList<T> {
    override val size: Int
        get() = count

    private var count = 0
    private var head: SinglyNode<T>? = null
    private var tail: SinglyNode<T>? = null

    override val first: T
        get() = head?.content ?: throw IndexOutOfBoundsException("List is empty")

    override val last: T
        get() = tail?.content ?: throw IndexOutOfBoundsException("List is empty")

    override fun addFirst(value: T) {
        val newNode = SinglyNode(value)

        if (head == null) {
            head = newNode
            tail = head
        } else {
            newNode.next = head
            head = newNode
        }

        count++
    }

    override fun addLast(value: T) {
        val newNode = SinglyNode(value)

        if (tail == null) {
            addFirst(value)
        } else {
            tail?.next = newNode
            tail = newNode

            count++
        }
    }

    override fun removeFirst(): Boolean {
        if (head == null) {
            return false
        }

        if (head === tail) {
            tail = null
            head = null
        } else {
            val tmpNode = head
            head = head?.next

            tmpNode?.next = null
        }

        count--
        return true
    }

    override fun removeLast(): Boolean {
        if (tail == null) {
            return false
        }

        if (head === tail) {
            return removeFirst()
        }

        var curNode: SinglyNode<T>? = head

        while (curNode?.next !== tail) {
            curNode = curNode?.next
        }

        curNode?.next = null
        tail = curNode

        count--

        return true
    }

    override fun remove(value: T): Boolean {
        if (head?.content == value) {
            return removeFirst()
        }

        if (tail?.content == value) {
            return removeLast()
        }

        var prevNode: SinglyNode<T>? = null
        var curNode: SinglyNode<T>? = head

        while (curNode != null && curNode?.content != value) {
            prevNode = curNode
            curNode = curNode?.next
        }

        if (curNode?.content != value) {
            return false
        }

        val nextNode = curNode?.next
        prevNode?.next = nextNode

        curNode?.next = null

        count--

        return true
    }

    override fun find(value: T): Node<T>? {
        var curNode: SinglyNode<T>? = head

        while (curNode != null) {
            if (curNode?.content == value) {
                return curNode
            }

            curNode = curNode?.next
        }

        return null
    }

    override fun clear() {
        head = null
        tail = null
        count = 0
    }

    override fun isEmpty(): Boolean = count == 0

    override fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            var curNode: SinglyNode<T>? = head

            override fun hasNext(): Boolean = curNode != null

            override fun next(): T {
                val content = curNode?.content
                curNode = curNode?.next

                return content ?: throw IndexOutOfBoundsException("No next iteration")
            }
        }
    }

    override fun toString(): String {
        return buildString {
            append("[")
            for ((index, value) in this@SinglyLinkList.withIndex()) {
                append(value)

                if (index < count - 1) {
                    append(", ")
                }
            }
            append("]")
        }
    }

    override fun get(index: Int): T {
        checkBounds(index)

        var curNode = head
        for (curIndex in 0 until index) {
            curNode = head?.next
        }

        return curNode!!.content
    }

    override fun set(index: Int, value: T) {
        checkBounds(index)

        var curNode = head
        for (curIndex in 0 until index) {
            curNode = head?.next
        }

        curNode?.content = value
    }

    private fun checkBounds(index: Int) {
        if (index !in 0 until count) {
            throw IndexOutOfBoundsException("Index ($index) out of bounds")
        }
    }
}