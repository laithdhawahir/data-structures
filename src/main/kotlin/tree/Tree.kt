package tree

/**
 * An interface for the binary search tree  data structure.
 */
interface Tree<T> {
    /**
     * The number of elements in the BST.
     */
    val size: Int

    /**
     * @return True if tree is empty, otherwise, false
     */
    fun isEmpty(): Boolean = size == 0

    /**
     * Add new element to the binary search tree.
     */
    fun add(value: T)

    /**
     * Remove an element from the binary search tree.
     *
     * @return True if element exists and removed, otherwise, false.
     */
    fun remove(value: T): Boolean

    /**
     * @return True if value exists in the tree, otherwise, false
     */
    fun find(value: T): Boolean

    /**
     * @return True if value exists in the tree, otherwise, false.
     */
    operator fun contains(value: T): Boolean = find(value)

    /**
     * @return a list of the tree elements in a preorder order.
     */
    fun preOrder(): List<T>

    /**
     * @return a list of the tree elements in a inorder order.
     */
    fun inOrder(): List<T>

    /**
     * @return a list of the tree elements in a post order order.
     */
    fun postOrder(): List<T>

    /**
     * @return a list of the tree elements in a level order. which is the elements in each level starting from the root node.
     */
    fun levelOrder(): List<T>
}