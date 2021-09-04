package union

/**
 * An interface for Union Find data structure
 */
interface UnionFind<T> {
    /**
     * Components count.
     */
    val componentsCount: Int

    /**
     * Union two nodes.
     */
    fun union(first: T, second: T)

    /**
     * Finds the root node of the given node.
     */
    fun find(node: T): T

    /**
     * @return true if both nodes are connected. otherwise, false
     */
    fun connected(first: T, second: T): Boolean

    /**
     * @return the size of the component in which given node belong to.
     */
    fun componentSize(node: T): Int
}
