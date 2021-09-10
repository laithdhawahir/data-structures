package tree

/**
 * Implementation of [Tree] using a Binary Search Tree Node Base.
 */
class BinarySearchTree<T : Comparable<T>> : Tree<T> {
    private var nodesCount = 0
    private var rootNode: TreeNode<T>? = null

    override val size: Int
        get() = nodesCount

    override fun add(value: T) {
        val newNode = TreeNode(value)

        if (rootNode == null) {
            rootNode = newNode
        } else {
            var parentNode: TreeNode<T>? = null
            var curNode = rootNode

            while (curNode != null) {
                parentNode = curNode

                curNode = if (value > curNode.value) {
                    curNode.right
                } else {
                    curNode.left
                }
            }

            if (value > parentNode!!.value) {
                parentNode.right = newNode
            } else {
                parentNode.left = newNode
            }
        }

        nodesCount++
    }

    override fun remove(value: T): Boolean {
        val nodePair = findNode(value) ?: return false
        nodesCount--
        remove(nodePair.first, nodePair.second)
        return true
    }

    private fun remove(parent: TreeNode<T>?, node: TreeNode<T>) {
        return when {
            // Case 1: node is a leaf node
            node.left == null && node.right == null -> {
                when {
                    parent == null -> {
                        rootNode = null
                    }
                    parent.left === node -> {
                        parent.left = null
                    }
                    else -> {
                        parent.right = null
                    }
                }
            }
            // Case 2: the left child is null only.
            node.left == null && node.right != null -> {
                val rightNode: TreeNode<T> = node.right!!
                node.left = rightNode.left
                node.right = rightNode.right
                node.value = rightNode.value
            }
            // Case 3: the right child is null only.
            node.left != null && node.right == null -> {
                val leftNode: TreeNode<T> = node.left!!
                node.left = leftNode.left
                node.right = leftNode.right
                node.value = leftNode.value
            }
            // Case 4: both child nodes are present. we pick the one in the right by finding the minimum of the right side,
            // and copy it to the target node, once that done we do recurisve call to remove the min node we found
            else -> {
                val minNodePair = findMinNodeOnRight(node)
                node.value = minNodePair.second.value
                remove(minNodePair.first, minNodePair.second)
            }
        }
    }

    private fun findMinNodeOnRight(node: TreeNode<T>): Pair<TreeNode<T>, TreeNode<T>> {
        var minNode = node.right!!
        var minParent = node

        var curNode = node.right
        var curParent = node

        while (curNode != null) {
            minNode = curNode
            minParent = curParent

            curParent = curNode
            curNode = curNode.left
        }

        return minParent to minNode
    }

    override fun find(value: T): Boolean = findNode(value) != null

    private fun findNode(value: T): Pair<TreeNode<T>?, TreeNode<T>>? {
        if (rootNode == null) {
            return null
        }

        var curNode = rootNode
        var parentNode: TreeNode<T>? = null

        while (curNode != null) {
            if (value == curNode.value) {
                return parentNode to curNode
            }

            parentNode = curNode
            curNode = if (value > curNode.value) {
                curNode.right
            } else {
                curNode.left
            }
        }

        return null
    }

    override fun preOrder(): List<T> {
        val result = mutableListOf<T>()
        preOrder(rootNode, result)

        return result
    }

    private fun preOrder(node: TreeNode<T>?, list: MutableList<T>) {
        if (node == null) {
            return
        }

        list.add(node.value)
        preOrder(node.left, list)
        preOrder(node.right, list)
    }

    override fun inOrder(): List<T> {
        val result = mutableListOf<T>()
        inOrder(rootNode, result)

        return result
    }

    private fun inOrder(node: TreeNode<T>?, list: MutableList<T>) {
        if (node == null) {
            return
        }

        inOrder(node.left, list)
        list.add(node.value)
        inOrder(node.right, list)
    }

    override fun postOrder(): List<T> {
        val result = mutableListOf<T>()
        postOrder(rootNode, result)

        return result
    }

    private fun postOrder(node: TreeNode<T>?, list: MutableList<T>) {
        if (node == null) {
            return
        }

        postOrder(node.left, list)
        postOrder(node.right, list)
        list.add(node.value)
    }

    override fun levelOrder(): List<T> {
        if (rootNode == null) {
            return emptyList()
        }

        val result = mutableListOf<T>()
        val queue = ArrayDeque<TreeNode<T>>()

        queue.add(rootNode!!)

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()

            result.add(node.value)

            if (node.left != null) {
                queue.add(node.left!!)
            }

            if (node.right != null) {
                queue.add(node.right!!)
            }
        }

        return result
    }
}