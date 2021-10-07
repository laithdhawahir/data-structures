package rangesum

/**
 * Implementation of [RangeSum] using Dynamic Programming.
 */
class DynamicProgrammingRangeSum(private val array: DoubleArray) : RangeSum {
    private val sumArray = DoubleArray(array.size + 1) { 0.0 }

    init {
        for (i in 1 until sumArray.size) {
            sumArray[i] = sumArray[i - 1] + array[i - 1]
        }
    }

    override val size: Int = array.size

    override fun query(range: IntRange): Double {
        checkBoundaries(range.first)
        checkBoundaries(range.last)

        // the end of the range is inclusive, while the sumArray do not include the end
        // that why we need to add one to the last index.
        return sumArray[range.last + 1] - sumArray[range.first]
    }

    override fun iterator(): Iterator<Double> = sumArray.iterator()

    private fun checkBoundaries(index: Int) {
        require(index in array.indices) { "Index $index out of boundaries" }
    }
}

/**
 * Factory method for RangeSum that is immutable.
 */
fun rangeSumOf(array: DoubleArray): RangeSum = DynamicProgrammingRangeSum(array)
