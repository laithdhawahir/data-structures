package rangesum

/**
 * Mutable interface for range sum.
 */
interface MutableRangeSum : RangeSum {
    /**
     * Update the range sum at the given index with the given value.
     * @param index to be updated.
     * @param value value to update on the given index.
     */
    fun update(index: Int, value: Double)
}