package linkedlist

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * A unit test for all implementations of LinkedList.
 */
class LinkedListTest {
    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN addFirst called GIVEN value THEN element added as first element in the list`(linkedList: LinkedList<String>) {
        linkedList.addFirst("1")
        linkedList.addFirst("2")

        assertEquals(actual = linkedList.first, expected = "2")
        assertEquals(actual = linkedList.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN addLast called GIVEN value THEN element added as last element in the list`(linkedList: LinkedList<String>) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertEquals(actual = linkedList.last, expected = "2")
        assertEquals(actual = linkedList.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN removeFirst called GIVEN linkList with elements THEN first element get removed`(linkedList: LinkedList<String>) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        linkedList.removeFirst()

        assertEquals(actual = linkedList.first, expected = "2")
        assertEquals(actual = linkedList.last, expected = "2")
        assertEquals(actual = linkedList.size, expected = 1)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN removeLast called GIVEN linkList with elements THEN last element get removed`(linkedList: LinkedList<String>) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        linkedList.removeLast()

        assertEquals(actual = linkedList.first, expected = "1")
        assertEquals(actual = linkedList.last, expected = "1")
        assertEquals(actual = linkedList.size, expected = 1)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN remove called GIVEN linkList with elements and existing element to remove THEN existing element get removed and return true`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertTrue { linkedList.remove("2") }

        assertEquals(actual = linkedList.first, expected = "1")
        assertEquals(actual = linkedList.last, expected = "1")
        assertEquals(actual = linkedList.size, expected = 1)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN remove called GIVEN linkList with elements and none existing element to remove THEN return false and no element get removed`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertFalse { linkedList.remove("3") }

        assertEquals(actual = linkedList.first, expected = "1")
        assertEquals(actual = linkedList.last, expected = "2")
        assertEquals(actual = linkedList.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN find called GIVEN linkList with elements and none existing element to find THEN return null`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertEquals(linkedList.find("3"), null)

        assertEquals(actual = linkedList.first, expected = "1")
        assertEquals(actual = linkedList.last, expected = "2")
        assertEquals(actual = linkedList.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN find called GIVEN linkList with elements and existing element to find THEN return element node`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertEquals(linkedList.find("2")?.content, "2")

        assertEquals(actual = linkedList.first, expected = "1")
        assertEquals(actual = linkedList.last, expected = "2")
        assertEquals(actual = linkedList.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN contains called GIVEN linkList with elements and existing element to find THEN return true`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        println("Laith = $linkedList")

        assertEquals(linkedList.contains("2"), true)

        assertEquals(actual = linkedList.first, expected = "1")
        assertEquals(actual = linkedList.last, expected = "2")
        assertEquals(actual = linkedList.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN contains called GIVEN linkList with elements and none existing element to find THEN return false`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertEquals(linkedList.contains("3"), false)

        assertEquals(actual = linkedList.first, expected = "1")
        assertEquals(actual = linkedList.last, expected = "2")
        assertEquals(actual = linkedList.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN clear called GIVEN linkList with elements THEN all elements get removed`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        linkedList.clear()

        assertEquals(actual = linkedList.size, expected = 0)
        assertEquals(actual = linkedList.isEmpty(), expected = true)
        assertThrows<IndexOutOfBoundsException> { linkedList.first }
        assertThrows<IndexOutOfBoundsException> { linkedList.last }
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN get called GIVEN linkList with elements and valid index THEN return specified element`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertEquals(actual = linkedList[0], expected = "1")
        assertEquals(actual = linkedList[1], expected = "2")
        assertEquals(actual = linkedList.isEmpty(), expected = false)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN get called GIVEN linkList with elements and not valid index THEN throws IndexOutOfBoundsException`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertThrows<IndexOutOfBoundsException> { linkedList[3] }
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN set called GIVEN linkList with elements and valid index THEN specified element changed`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        linkedList[0] = "3"
        linkedList[1] = "4"

        assertEquals(actual = linkedList[0], expected = "3")
        assertEquals(actual = linkedList[1], expected = "4")
        assertEquals(actual = linkedList.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `WHEN set called GIVEN linkList with elements and not valid index THEN throws IndexOutOfBoundsException`(
        linkedList: LinkedList<String>
    ) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertThrows<IndexOutOfBoundsException> { linkedList[3] = "5" }
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `GIVEN linkedList WITH elements THEN for each iterate over elements`(linkedList: LinkedList<String>) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        val expectedValues = arrayOf("1", "2")

        for ((index, value) in linkedList.withIndex()) {
            assertEquals(actual = value, expected = expectedValues[index])
        }
    }

    @ParameterizedTest
    @MethodSource("linkedLists")
    fun `GIVEN linkedList WITH elements THEN toString return expected String`(linkedList: LinkedList<String>) {
        linkedList.addLast("1")
        linkedList.addLast("2")

        assertEquals(actual = linkedList.toString(), expected = "[1, 2]")
    }

    companion object {
        @JvmStatic
        fun linkedLists() = listOf(
            Arguments.of(SinglyLinkList<String>()),
            Arguments.of(DoublyLinkList<String>())
        )
    }
}