package tree

/**
 * A Tree node data structure to store tree nodes.
 */
class TreeNode<T>(
    var value: T,
    var left: TreeNode<T>? = null,
    var right: TreeNode<T>? = null
)