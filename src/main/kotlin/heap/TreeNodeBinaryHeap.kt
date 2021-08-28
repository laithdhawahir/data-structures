package heap

import java.util.Comparator.naturalOrder

/**
 * Implementation [Heap] using binary tree.
 */
class TreeNodeBinaryHeap<T : Comparable<T>>(private val comparator: Comparator<T> = naturalOrder()) : Heap<T> {
    private class TreeNode<T>(
        var content: T,
        var leftNode: TreeNode<T>? = null,
        var rightNode: TreeNode<T>? = null,
        var parentNode: TreeNode<T>? = null
    ) {
        override fun toString(): String = content.toString()
    }

    private var nodesCount = 0

    private var rootNode: TreeNode<T>? = null
    private val incompleteNodes = mutableListOf<TreeNode<T>>()

    override val size: Int
        get() = nodesCount

    override fun add(value: T) {
        val node = TreeNode(value)
        addToIncompleteNode(node)
        bubbleUp(node)
    }

    private fun addToIncompleteNode(node: TreeNode<T>) {
        nodesCount++

        // case for adding the first ever node
        if (rootNode == null) {
            rootNode = node
            incompleteNodes.add(node)
            return
        }

        // case for adding new node but it is not the first ever
        // first incomplete node is where we should add our next node
        val firstIncompleteNode = incompleteNodes.first()
        if (firstIncompleteNode.leftNode == null) {
            firstIncompleteNode.leftNode = node
        } else {
            firstIncompleteNode.rightNode = node
            incompleteNodes.removeFirst()
        }

        // first incomplete node is the parent node for the new node
        node.parentNode = firstIncompleteNode
        incompleteNodes.add(node)
    }

    override fun poll(): T {
        // get the value for the root node if it is not null.
        val element = requireNotNull(rootNode) { "heap is empty" }.content

        remove(rootNode!!)

        return element
    }

    override fun peek(): T = requireNotNull(rootNode) { "heap is empty" }.content


    override fun remove(value: T): Boolean {
        val node = findNode(value) ?: return false

        remove(node)
        return true
    }

    private fun remove(node: TreeNode<T>) {
        // Since we are removing a node by reference we are sure it will get removed so we -1 from count
        nodesCount--

        // if node count is zero this mean we had only one node before removing.
        if (nodesCount == 0) {
            rootNode = null
            incompleteNodes.clear()
            return
        }

        // get the last node to swap target node with it.
        val lastNode = incompleteNodes.last()
        swap(node, lastNode)

        // remove the last node should after swapping contains what target node has, this is same as removing the node itself.
        removeLastNode()

        // after swapping the node contains what last node supposed to contain
        // get the parent to figure how if we should bubble up or bubble down
        // bubble up if parent content is larger than node content, otherwise bubble down
        val parentNode = node.parentNode
        if (parentNode != null && comparator.compare(node.content, parentNode.content) < 0) {
            bubbleUp(node)
        } else {
            bubbleDown(node)
        }
    }

    private fun removeLastNode() {
        // get last node which what we are going to remove
        val lastNode = incompleteNodes.last()

        // remove the node from the incomplete list
        incompleteNodes.removeLast()

        // check which side the last node is in left or right of its parent,
        // and set that side to null to remove refernces to the last node
        val parent = lastNode.parentNode

        if (parent?.leftNode === lastNode) {
            parent.leftNode = null
        } else if (parent?.rightNode === lastNode) {
            parent.rightNode = null
        }

        // make sure that last node do not reference the parent anymore
        lastNode.parentNode = null
    }

    override operator fun contains(value: T): Boolean = findNode(value) != null

    override fun iterator(): Iterator<T> = traverseNodes().iterator()

    override fun toString(): String = traverseNodes().toString()

    private fun findNode(value: T): TreeNode<T>? {
        if (rootNode == null) {
            return null
        }

        // Apply Breadth first search to search tree elements according to
        // the indexed tree same as what we have in the Array Binary Heap
        val queue = ArrayDeque<TreeNode<T>>()
        queue.add(rootNode!!)

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()

            if (comparator.compare(node.content, value) == 0) {
                return node
            }

            if (node.leftNode != null) {
                queue.add(node.leftNode!!)
            }

            if (node.rightNode != null) {
                queue.add(node.rightNode!!)
            }
        }

        return null
    }

    private fun traverseNodes(): List<T> {
        if (rootNode == null) {
            return listOf()
        }

        // Traverse the tree using breadth first search algorithm
        val values = mutableListOf<T>()

        val queue = ArrayDeque<TreeNode<T>>()
        queue.add(rootNode!!)

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            values.add(node.content)

            if (node.leftNode != null) {
                queue.add(node.leftNode!!)
            }

            if (node.rightNode != null) {
                queue.add(node.rightNode!!)
            }
        }

        return values
    }

    private fun bubbleUp(node: TreeNode<T>) {
        // start with the node and keep swapping with parent as long as node content is less than parent
        // and stop at the rootNode this is when parentNode == null
        var curNode: TreeNode<T> = node
        var parentNode: TreeNode<T>? = node.parentNode

        while (parentNode != null && comparator.compare(curNode.content, parentNode.content) < 0) {
            swap(parentNode, curNode)

            curNode = parentNode
            parentNode = curNode.parentNode
        }
    }

    private fun bubbleDown(node: TreeNode<T>) {
        // start with node and keep swapping with the node minimum child as long as child is less than node,
        // stop when there is not children.
        var curNode: TreeNode<T> = node
        var minChild = minChildNode(curNode)

        while (minChild != null && comparator.compare(minChild.content, curNode.content) < 0) {
            swap(minChild, curNode)

            curNode = minChild
            minChild = minChildNode(curNode)
        }
    }

    private fun minChildNode(node: TreeNode<T>): TreeNode<T>? {
        // get the minimum child, by comparing content if both not null,
        // otherwise, return the not null one or return null of both are null
        val leftNode = node.leftNode
        val rightNode = node.rightNode

        return if (leftNode != null && rightNode != null) {
            if (comparator.compare(leftNode.content, rightNode.content) < 0) {
                node.leftNode
            } else {
                node.rightNode
            }
        } else if (leftNode == null && rightNode != null) {
            node.rightNode
        } else if (rightNode == null && leftNode != null) {
            node.leftNode
        } else {
            null
        }
    }

    private fun swap(firstNode: TreeNode<T>, secondNode: TreeNode<T>) {
        // swap the nodes content instead of pointers this is much easier.
        val tmp = firstNode.content
        firstNode.content = secondNode.content
        secondNode.content = tmp
    }
}