package union

/**
 * Implementation of [UnionFind<T>] using [ArrayBaseIntUnionFind].
 */
class ArrayBaseUnionFind<T>(private val elements: Set<T>) : UnionFind<T> {
    private val elementToNodeIdMap = elements.mapIndexed { index, value -> value to index }.toMap()
    private val nodeIdToElementMap = elements.mapIndexed { index, value -> index to value }.toMap()
    private val unionFind = ArrayBaseIntUnionFind(elements.size)

    override val componentsCount: Int
        get() = unionFind.componentsCount

    override fun union(first: T, second: T) {
        checkNode(first)
        checkNode(second)

        unionFind.union(elementToNodeIdMap[first]!!, elementToNodeIdMap[second]!!)
    }

    override fun find(node: T): T = checkNode(node)
        .let { unionFind.find(elementToNodeIdMap[node]!!) }
        .let { nodeIdToElementMap[it]!! }

    override fun connected(first: T, second: T): Boolean {
        checkNode(first)
        checkNode(second)

        return unionFind.connected(elementToNodeIdMap[first]!!, elementToNodeIdMap[second]!!)
    }

    override fun componentSize(node: T): Int {
        checkNode(node)
        return unionFind.componentSize(elementToNodeIdMap[node]!!)
    }

    private fun checkNode(node: T) {
        require(node in elementToNodeIdMap) { "Unrecognized node ($node), valid nodes are $elements" }
    }
}
