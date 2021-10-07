package rangesum

/**
 * Immutable interface for RangeSum.
 */
interface RangeSum : Iterable<Double> {
    /**
     * Range size.
     */
    val size: Int

    /**
     * Return the range sum of given range.
     * @param range the range to get its sum.
     */
    fun query(range: IntRange): Double
}