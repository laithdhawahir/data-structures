package stack

import linkedlist.DoublyLinkList
import linkedlist.SinglyLinkList
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * A unit test for all implementations of Stack.
 */
class StackTest {
    @ParameterizedTest
    @MethodSource("stacks")
    fun `WHEN push called GIVEN element THEN stack top equal pushed element`(stack: Stack<String>) {
        stack.push("1")
        stack.push("2")

        assertEquals(actual = stack.peek, expected = "2")
        assertEquals(actual = stack.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("stacks")
    fun `WHEN pop called THEN return top element and it get removed`(stack: Stack<String>) {
        stack.push("1")
        stack.push("2")

        assertEquals(actual = stack.pop(), expected = "2")
        assertEquals(actual = stack.peek, expected = "1")
        assertEquals(actual = stack.size, expected = 1)
    }

    @ParameterizedTest
    @MethodSource("stacks")
    fun `WHEN peek called THEN return top element returned`(stack: Stack<String>) {
        stack.push("1")
        stack.push("2")

        assertEquals(actual = stack.peek, expected = "2")
        assertEquals(actual = stack.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("stacks")
    fun `WHEN isEmpty called GIVEN stack with elements THEN return false`(stack: Stack<String>) {
        stack.push("1")
        stack.push("2")

        assertEquals(actual = stack.isEmpty(), expected = false)
    }

    @ParameterizedTest
    @MethodSource("stacks")
    fun `WHEN isEmpty called GIVEN empty stack THEN return true`(stack: Stack<String>) {
        assertEquals(actual = stack.isEmpty(), expected = true)
    }

    @ParameterizedTest
    @MethodSource("stacks")
    fun `WHEN contains called GIVEN stack with elements and existing element THEN return true`(stack: Stack<String>) {
        stack.push("1")
        stack.push("2")
        stack.push("3")

        assertEquals(actual = stack.contains("1"), expected = true)
    }

    @ParameterizedTest
    @MethodSource("stacks")
    fun `WHEN contains called GIVEN stack with elements and none existing element THEN return false`(stack: Stack<String>) {
        stack.push("1")
        stack.push("2")
        stack.push("3")

        assertEquals(actual = stack.contains("4"), expected = false)
    }

    companion object {
        @JvmStatic
        fun stacks() = listOf(
            Arguments.of(DynamicArrayStack<String>()),
            Arguments.of(LinkedListStack<String>(SinglyLinkList())),
            Arguments.of(LinkedListStack<String>(DoublyLinkList()))
        )
    }
}