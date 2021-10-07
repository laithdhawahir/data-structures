package rangesum

/**
 * Implementation of [MutableRangeSum] using fenwick tree.
 */
class FenwickMutableRangeSum(array: DoubleArray) : MutableRangeSum {
    // To make the Fenwick works correctly, it should be one based, so that why we add the +1
    // but also we need to hide that from users of this class, that why we do +1 to the given index to keep it zero base.
    private val tree = DoubleArray(array.size + 1) {
        if (it == 0) {
            0.0
        } else {
            array[it - 1]
        }
    }

    private val actualArray = array.copyOf()

    init {
        for (i in 1 until tree.size) {
            val parentIndex = i + Integer.lowestOneBit(i)
            if (parentIndex < tree.size) {
                tree[parentIndex] += tree[i]
            }
        }
    }

    override val size: Int = array.size

    override fun update(index: Int, value: Double) {
        checkBoundaries(index)

        // This update function is by setting a new value and not adding to the value for that reason,
        // we compute the difference between the new value and the actual value and add it to the sum.
        var addValue = value - actualArray[index]
        var i = index + 1

        while (i < tree.size) {
            tree[i] += addValue
            i += Integer.lowestOneBit(i)
        }
    }

    override fun query(range: IntRange): Double {
        checkBoundaries(range.first)
        checkBoundaries(range.last)

        val lastSum = prefixSum(range.last)
        val firstSum = prefixSum(range.first - 1)

        return lastSum - firstSum
    }

    override fun iterator(): Iterator<Double> = tree.iterator()

    private fun prefixSum(index: Int): Double {
        var sum = 0.0
        // to m
        var i = index + 1
        while (i > 0) {
            sum += tree[i]
            i -= Integer.lowestOneBit(i)
        }

        return sum
    }

    private fun checkBoundaries(index: Int) {
        require(index in 0 until tree.size - 1) { "index $index out of boundaries" }
    }
}

/**
 * Factory method for MutableRangeSum that is mutable.
 */
fun mutableRangeSumOf(array: DoubleArray): MutableRangeSum = FenwickMutableRangeSum(array)
