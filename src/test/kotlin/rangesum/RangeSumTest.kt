package rangesum

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * Unit test for all [RangeSum] implementations.
 */
class RangeSumTest {
    @ParameterizedTest
    @MethodSource("rangeSumFactory")
    fun `WHEN constructor called GIVEN an array THEN has the expected size`(factory: RangeSumFactory) {
        val rangeSum = factory(doubleArrayOf(1.0, 2.0, 3.0, 4.0))
        assertEquals(actual = rangeSum.size, expected = 4)
    }


    @ParameterizedTest
    @MethodSource("rangeSumFactory")
    fun `WHEN query called GIVEN an array THEN return expected result`(factory: RangeSumFactory) {
        val rangeSum = factory(doubleArrayOf(1.0, 2.0, 3.0, 4.0))

        assertEquals(actual = rangeSum.query(1..2), expected = 5.0)
        assertEquals(actual = rangeSum.query(0..3), expected = 10.0)
        assertEquals(actual = rangeSum.query(0..0), expected = 1.0)
        assertEquals(actual = rangeSum.query(2..2), expected = 3.0)
    }

    companion object {
        @JvmStatic
        fun rangeSumFactory() = listOf(
            Arguments.of({ array: DoubleArray -> rangeSumOf(array) }),
            Arguments.of({ array: DoubleArray -> mutableRangeSumOf(array) }),
        )
    }
}

typealias RangeSumFactory = (DoubleArray) -> RangeSum