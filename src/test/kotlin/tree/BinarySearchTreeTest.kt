package tree

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * A unit test for the [BinarySearchTree<T>]
 */
class BinarySearchTreeTest {
    @Test
    fun `WHEN add called GIVEN with empty tree THEN tree operations return expected results`() {
        val binarySearchTree = BinarySearchTree<Int>()
        binarySearchTree.add(5)

        assertEquals(actual = binarySearchTree.size, expected = 1)
        assertEquals(actual = binarySearchTree.isEmpty(), expected = false)
        assertEquals(actual = binarySearchTree.preOrder(), expected = listOf(5))
        assertEquals(actual = binarySearchTree.postOrder(), expected = listOf(5))
        assertEquals(actual = binarySearchTree.inOrder(), expected = listOf(5))
        assertEquals(actual = binarySearchTree.levelOrder(), expected = listOf(5))
        assertEquals(actual = binarySearchTree.find(5), expected = true)
        assertEquals(actual = binarySearchTree.find(4), expected = false)
        assertEquals(actual = binarySearchTree.find(6), expected = false)
    }

    @Test
    fun `WHEN add called GIVEN with none empty tree THEN tree operations return expected results`() {
        val binarySearchTree = BinarySearchTree<Int>()
        binarySearchTree.add(5)
        binarySearchTree.add(10)
        binarySearchTree.add(0)
        binarySearchTree.add(1)
        binarySearchTree.add(2)
        binarySearchTree.add(3)
        binarySearchTree.add(7)
        binarySearchTree.add(8)
        binarySearchTree.add(9)

        assertEquals(actual = binarySearchTree.size, expected = 9)
        assertEquals(actual = binarySearchTree.isEmpty(), expected = false)
        assertEquals(actual = binarySearchTree.preOrder(), expected = listOf(5, 0, 1, 2, 3, 10, 7, 8, 9))
        assertEquals(actual = binarySearchTree.postOrder(), expected = listOf(3, 2, 1, 0, 9, 8, 7, 10, 5))
        assertEquals(actual = binarySearchTree.inOrder(), expected = listOf(0, 1, 2, 3, 5, 7, 8, 9, 10))
        assertEquals(actual = binarySearchTree.levelOrder(), expected = listOf(5, 0, 10, 1, 7, 2, 8, 3, 9))
        assertEquals(actual = binarySearchTree.find(10), expected = true)
        assertEquals(actual = binarySearchTree.find(4), expected = false)
        assertEquals(actual = binarySearchTree.find(6), expected = false)
    }

    @Test
    fun `WHEN remove called GIVEN with none empty tree THEN tree operations return expected results`() {
        val binarySearchTree = BinarySearchTree<Int>()
        binarySearchTree.add(5)
        binarySearchTree.add(10)
        binarySearchTree.add(0)
        binarySearchTree.add(1)
        binarySearchTree.add(2)
        binarySearchTree.add(3)
        binarySearchTree.add(7)
        binarySearchTree.add(8)
        binarySearchTree.add(9)

        binarySearchTree.remove(7)
        assertEquals(actual = binarySearchTree.size, expected = 8)
        assertEquals(actual = binarySearchTree.isEmpty(), expected = false)
        assertEquals(actual = binarySearchTree.preOrder(), expected = listOf(5, 0, 1, 2, 3, 10, 8, 9))
        assertEquals(actual = binarySearchTree.postOrder(), expected = listOf(3, 2, 1, 0, 9, 8, 10, 5))
        assertEquals(actual = binarySearchTree.inOrder(), expected = listOf(0, 1, 2, 3, 5, 8, 9, 10))
        assertEquals(actual = binarySearchTree.levelOrder(), expected = listOf(5, 0, 10, 1, 8, 2, 9, 3))
        assertEquals(actual = binarySearchTree.find(7), expected = false)

        binarySearchTree.remove(9)
        assertEquals(actual = binarySearchTree.size, expected = 7)
        assertEquals(actual = binarySearchTree.isEmpty(), expected = false)
        assertEquals(actual = binarySearchTree.preOrder(), expected = listOf(5, 0, 1, 2, 3, 10, 8))
        assertEquals(actual = binarySearchTree.postOrder(), expected = listOf(3, 2, 1, 0, 8, 10, 5))
        assertEquals(actual = binarySearchTree.inOrder(), expected = listOf(0, 1, 2, 3, 5, 8, 10))
        assertEquals(actual = binarySearchTree.levelOrder(), expected = listOf(5, 0, 10, 1, 8, 2, 3))
        assertEquals(actual = binarySearchTree.find(9), expected = false)

        binarySearchTree.remove(2)
        assertEquals(actual = binarySearchTree.size, expected = 6)
        assertEquals(actual = binarySearchTree.isEmpty(), expected = false)
        assertEquals(actual = binarySearchTree.preOrder(), expected = listOf(5, 0, 1, 3, 10, 8))
        assertEquals(actual = binarySearchTree.postOrder(), expected = listOf(3, 1, 0, 8, 10, 5))
        assertEquals(actual = binarySearchTree.inOrder(), expected = listOf(0, 1, 3, 5, 8, 10))
        assertEquals(actual = binarySearchTree.levelOrder(), expected = listOf(5, 0, 10, 1, 8, 3))
        assertEquals(actual = binarySearchTree.find(2), expected = false)

        binarySearchTree.remove(5)
        assertEquals(actual = binarySearchTree.size, expected = 5)
        assertEquals(actual = binarySearchTree.isEmpty(), expected = false)
        assertEquals(actual = binarySearchTree.preOrder(), expected = listOf(8, 0, 1, 3, 10))
        assertEquals(actual = binarySearchTree.postOrder(), expected = listOf(3, 1, 0, 10, 8))
        assertEquals(actual = binarySearchTree.inOrder(), expected = listOf(0, 1, 3, 8, 10))
        assertEquals(actual = binarySearchTree.levelOrder(), expected = listOf(8, 0, 10, 1, 3))
        assertEquals(actual = binarySearchTree.find(5), expected = false)
    }

    @Test
    fun `WHEN GIVEN THEN`() {

    }
}