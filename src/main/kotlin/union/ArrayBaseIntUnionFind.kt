package union

/**
 * Implementation of [UnionFind<Int>] using fixed size array.
 */
class ArrayBaseIntUnionFind(size: Int) : UnionFind<Int> {
    private var unifiedNodeCount = 0
    private val nodes = IntArray(size) { -1 }
    private val componentsSize = IntArray(size) { 1 }

    override val componentsCount: Int
        get() = nodes.size - unifiedNodeCount

    override fun union(first: Int, second: Int) {
        checkNode(first)
        checkNode(second)

        val firstRootNode = find(first)
        val secondRootNode = find(second)

        // If they are both in the same set no need to do anything.
        if (firstRootNode == secondRootNode) {
            return
        }

        // Merge the two sets according to which set is smaller, the smaller connect to the bigger
        if (componentsSize[firstRootNode] <= componentsSize[secondRootNode]) {
            nodes[firstRootNode] = secondRootNode
            componentsSize[secondRootNode] += componentsSize[firstRootNode]
        } else {
            nodes[secondRootNode] = firstRootNode
            componentsSize[firstRootNode] += componentsSize[secondRootNode]
        }

        // Add 1 to the number of unified nodes.
        unifiedNodeCount++
    }

    override fun find(node: Int): Int {
        checkNode(node)

        // find the root node of all nodes in the given node group.
        var rootNode = node
        while (nodes[rootNode] != -1) {
            rootNode = nodes[rootNode]
        }

        // Path compression by assigning the root node directly to all nodes we have scanned
        var curNode = node
        while (curNode != rootNode) {
            val nextNode = nodes[curNode]
            nodes[curNode] = rootNode
            curNode = nextNode
        }

        return rootNode
    }

    override fun connected(first: Int, second: Int): Boolean = find(first) == find(second)

    override fun componentSize(node: Int): Int = checkNode(node).let { componentsSize[find(node)] }

    private fun checkNode(node: Int) {
        require(node in nodes.indices) { "Invalid node ( $node ) index" }
    }
}
