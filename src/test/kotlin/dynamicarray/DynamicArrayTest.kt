package dynamicarray

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

/**
 * A unit test for [DynamicArray]
 */
class DynamicArrayTest {
    @Test
    fun `GIVEN DynamicArray WITH new elements THEN it contains the elements with correct size`() {
        val dynamicArray = DynamicArray<String>()
        dynamicArray.append("First")
        dynamicArray.append("Second")

        assertEquals(dynamicArray[0], "First")
        assertEquals(dynamicArray[1], "Second")
        assertEquals(dynamicArray.size, 2)
        assertEquals(dynamicArray.capacity, 2)
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN toString return expected string`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        assertEquals(dynamicArray.toString(), "[ First, Second ]")
    }

    @Test
    fun `GIVEN DynamicArray WITH no elements THEN it contains no elements and size is zero`() {
        val dynamicArray = DynamicArray<String>()
        assertEquals(dynamicArray.size, 0)

        assertThrows<IndexOutOfBoundsException> { dynamicArray[0] }
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN indexOf for existing element return correct index`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        assertEquals(dynamicArray.indexOf("Second"), 1)
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN indexOf for not existing element return -1`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        assertEquals(dynamicArray.indexOf("Third"), -1)
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN contains for existing element return true`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        assertEquals(dynamicArray.contains("Second"), true)
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN contains for not existing element return false`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        assertEquals(dynamicArray.contains("Third"), false)
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN removeAt(0) removes the first element`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        dynamicArray.removeAt(0)
        assertEquals(dynamicArray[0], "Second")
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN removeAt with out of bound index throws IndexOutOfBoundsException`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        assertThrows<java.lang.IndexOutOfBoundsException> { dynamicArray.removeAt(3) }
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN remove first existing element and return true`() {
        val dynamicArray = dynamicArrayOf("First", "Second", "Second")
        assertEquals(dynamicArray.removeFirst("Second"), true)
        assertEquals(dynamicArray[0], "First")
        assertEquals(dynamicArray[1], "Second")
        assertEquals(dynamicArray.size, 2)
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN remove not existing element return false`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        assertEquals(dynamicArray.removeFirst("Third"), false)
        assertEquals(dynamicArray.size, 2)
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN for each iterate over elements`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        val expectedValues = arrayOf("First", "Second")

        for ((index, value) in dynamicArray.withIndex()) {
            assertEquals(value, expectedValues[index])
        }
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN accessing element out of bound throws IndexOutOfBoundsException`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        assertThrows<IndexOutOfBoundsException> { dynamicArray[3] }
    }

    @Test
    fun `GIVEN DynamicArray WITH elements THEN set element at valid index change the value`() {
        val dynamicArray = dynamicArrayOf("First", "Second")
        dynamicArray[0] = "Changed Value"
        assertEquals(dynamicArray[0], "Changed Value")
    }

    @Test
    fun `GIVEN DynamicArray WITH elements more than initial capacity THEN capacity get resized`() {
        val dynamicArray = dynamicArrayOf("First", "Second", "Third")
        assertEquals(dynamicArray.capacity, 4)
        assertEquals(dynamicArray.size, 3)
    }
}

