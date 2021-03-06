package heap

import java.util.Comparator.naturalOrder

/**
 * Implementation [Heap] using binary tree array.
 */
class ArrayBinaryHeap<T : Comparable<T>>(private val comparator: Comparator<T> = naturalOrder()) : Heap<T> {
    private val heap = mutableListOf<T>()

    companion object {
        fun <T : Comparable<T>> fromArray(
            list: List<T>,
            comparator: Comparator<T> = naturalOrder()
        ): ArrayBinaryHeap<T> {
            val binaryHeap = ArrayBinaryHeap<T>(comparator)
            binaryHeap.heap.addAll(list)

            // start index is set to the second level from bottom of the binary tree,
            // since each level in the tree increase by 2^i, where i is the level, assume that the tree level is h
            // then last level is 2 ^ h elements, the level before it is 2 ^ ( h -1 ) which is same as (2 ^ h) / 2
            // the -1 because it is a zero indexed array which mean it starts from 0 to size - 1
            val startIndex = (list.size / 2) - 1

            // iterate from second level from bottom to the top.
            for (index in startIndex downTo 0) {
                binaryHeap.bubbleDown(index)
            }

            return binaryHeap
        }
    }

    override val size: Int
        get() = heap.size

    override fun poll(): T {
        val element = heap.first()

        // swap top with last element
        swap(0, size - 1)
        heap.removeLast()

        bubbleDown(0)
        return element
    }

    override fun peek(): T = heap.first()

    override fun add(value: T) {
        heap.add(value)
        bubbleUp(size - 1)
    }

    override fun remove(value: T): Boolean {
        // Find the element
        val index = heap.indexOf(value)

        if (index < 0) {
            return false
        }

        // Swap with last element and remove it.
        swap(index, size - 1)

        heap.removeLast()

        // Decide if we should bubble up or bubble down.
        val parentValue = heap[parentIndex(index)]
        val nodeValue = heap[index]

        if (comparator.compare(nodeValue, parentValue) < 0) {
            bubbleUp(index)
        } else {
            bubbleDown(index)
        }

        return true
    }

    override operator fun contains(value: T): Boolean {
        for (element in heap) {
            if (comparator.compare(element, value) == 0) {
                return true
            }
        }

        return false
    }

    override fun iterator(): Iterator<T> = heap.iterator()

    override fun toString(): String = heap.toString()

    private fun bubbleUp(index: Int) {
        var nodeIndex = index
        var parentIndex = parentIndex(nodeIndex)

        while (comparator.compare(heap[nodeIndex], heap[parentIndex]) < 0) {
            swap(nodeIndex, parentIndex)

            nodeIndex = parentIndex
            parentIndex = parentIndex(nodeIndex)
        }
    }

    private fun bubbleDown(index: Int) {
        var nodeIndex = index
        var minChild = minChild(index)

        while (minChild != null && comparator.compare(heap[nodeIndex], minChild!!.first) > 0) {
            swap(nodeIndex, minChild.second)

            nodeIndex = minChild.second
            minChild = minChild(nodeIndex)
        }
    }

    private fun parentIndex(index: Int) = when {
        index % 2 == 0 -> maxOf(0, (index - 1) / 2)
        else -> index / 2
    }

    private fun leftChildIndex(index: Int): Int = 2 * index + 1
    private fun rightChildIndex(index: Int): Int = 2 * index + 2

    private fun swap(firstIndex: Int, secondIndex: Int) {
        val tmp = heap[firstIndex]
        heap[firstIndex] = heap[secondIndex]
        heap[secondIndex] = tmp
    }

    private fun minChild(index: Int): Pair<T, Int>? {
        val leftChild = leftChild(index)
        val rightChild = rightChild(index)

        return if (leftChild != null && rightChild != null) {
            if (comparator.compare(leftChild, rightChild) < 0) {
                leftChild to leftChildIndex(index)
            } else {
                rightChild to rightChildIndex(index)
            }
        } else if (leftChild == null && rightChild != null) {
            rightChild to rightChildIndex(index)
        } else if (rightChild == null && leftChild != null) {
            leftChild to leftChildIndex(index)
        } else {
            null
        }
    }

    private fun leftChild(index: Int): T? {
        val leftChildIndex = leftChildIndex(index)

        if (leftChildIndex >= size) {
            return null
        }

        return heap[leftChildIndex]
    }

    private fun rightChild(index: Int): T? {
        val rightChildIndex = rightChildIndex(index)

        if (rightChildIndex >= size) {
            return null
        }

        return heap[rightChildIndex]
    }
}