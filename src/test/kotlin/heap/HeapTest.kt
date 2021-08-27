package heap

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * Unit test for all heap implementation.
 */
class HeapTest {
    @ParameterizedTest
    @MethodSource("heaps")
    fun `WHEN add called GIVEN heap with elements THEN peek return min element`(heap: Heap<Int>) {
        heap.add(0)
        heap.add(-1)
        heap.add(3)

        assertEquals(actual = heap.peek(), expected = -1)
        assertEquals(actual = heap.size, expected = 3)
    }

    @ParameterizedTest
    @MethodSource("heaps")
    fun `WHEN poll called GIVEN heap with elements THEN return min element each time it is called`(heap: Heap<Int>) {
        heap.add(0)
        heap.add(-1)
        heap.add(3)
        heap.add(10)

        val expected = listOf(-1, 0, 3, 10)

        for (index in expected.indices) {
            val actualValue = heap.poll()
            assertEquals(actual = actualValue, expected = expected[index])
        }

        assertEquals(heap.isEmpty(), true)
        assertEquals(heap.size, 0)
    }

    @ParameterizedTest
    @MethodSource("heaps")
    fun `WHEN contains called GIVEN heap with elements THEN return true for existing element`(heap: Heap<Int>) {
        heap.add(0)
        heap.add(-1)
        heap.add(3)
        heap.add(10)

        assertEquals(actual = 3 in heap, expected = true)
    }

    @ParameterizedTest
    @MethodSource("heaps")
    fun `WHEN contains called GIVEN heap with elements THEN return false for none existing element`(heap: Heap<Int>) {
        heap.add(0)
        heap.add(-1)
        heap.add(3)
        heap.add(10)

        assertEquals(actual = 9 in heap, expected = false)
    }

    @ParameterizedTest
    @MethodSource("heaps")
    fun `WHEN toString called GIVEN heap with elements THEN return expected string`(heap: Heap<Int>) {
        heap.add(0)
        heap.add(-1)
        heap.add(3)
        heap.add(10)

        assertEquals(actual = heap.toString(), expected = listOf(-1, 0, 3, 10).toString())
    }

    @ParameterizedTest
    @MethodSource("heaps")
    fun `WHEN remove called GIVEN heap with elements and element that require bubble down THEN heap contain correct elements order`(
        heap: Heap<Int>
    ) {
        listOf(1, 3, 2, 5, 6, 2, 2, 13, 8, 11, 7, 10, 15).forEach { heap.add(it) }

        assertEquals(actual = heap.remove(3), expected = true)

        val expected = listOf(1, 5, 2, 8, 6, 2, 2, 13, 15, 11, 7, 10)

        for ((index, value) in heap.withIndex()) {
            assertEquals(actual = value, expected = expected[index])
        }
    }

    @ParameterizedTest
    @MethodSource("heaps")
    fun `WHEN remove called GIVEN heap with elements and element that require bubble up THEN heap contain correct elements order`(
        heap: Heap<Int>
    ) {
        listOf(1, 5, 2, 8, 6, 2, 2, 13, 12, 11, 7, 10, 15, 3).forEach { heap.add(it) }

        assertEquals(actual = heap.remove(12), expected = true)

        val expected = listOf(1, 3, 2, 5, 6, 2, 2, 13, 8, 11, 7, 10, 15)

        for ((index, value) in heap.withIndex()) {
            assertEquals(actual = value, expected = expected[index])
        }
    }

    companion object {
        @JvmStatic
        fun heaps() = listOf(
            Arguments.of(ArrayBinaryHeap<Int>()),
            Arguments.of(TreeNodeBinaryHeap<Int>())
        )
    }
}