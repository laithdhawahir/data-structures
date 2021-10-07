package rangesum

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * Unit test for all [MutableRangeSum] implementations.
 */
class MutableRangeSumTest {
    @ParameterizedTest
    @MethodSource("mutableRangeSumFactory")
    fun `WHEN update called GIVEN valid index and value THEN return expected sum`(factory: MutableRangeSumFactory) {
        val mutableRangeSum = factory(doubleArrayOf(1.0, 2.0, 3.0, 4.0))
        mutableRangeSum.update(1, 3.0)

        assertEquals(actual = mutableRangeSum.size, expected = 4)
        assertEquals(actual = mutableRangeSum.query(0..3), expected = 11.0)
    }

    companion object {
        @JvmStatic
        fun mutableRangeSumFactory() = listOf(
            Arguments.of({ array: DoubleArray -> mutableRangeSumOf(array) }),
        )
    }
}

typealias MutableRangeSumFactory = (DoubleArray) -> MutableRangeSum