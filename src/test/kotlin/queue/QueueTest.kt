package queue

import linkedlist.DoublyLinkList
import linkedlist.SinglyLinkList
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * A unit test for all implementations of Queue.
 */
class QueueTest {
    @ParameterizedTest
    @MethodSource("queues")
    fun `WHEN add called GIVEN elements THEN queue top equal first element`(queue: Queue<String>) {
        queue.add("1")
        queue.add("2")

        assertEquals(actual = queue.peek, expected = "1")
        assertEquals(actual = queue.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("queues")
    fun `WHEN pop called THEN return first element and it get removed`(queue: Queue<String>) {
        queue.add("1")
        queue.add("2")

        assertEquals(actual = queue.pop(), expected = "1")
        assertEquals(actual = queue.peek, expected = "2")
        assertEquals(actual = queue.size, expected = 1)
    }

    @ParameterizedTest
    @MethodSource("queues")
    fun `WHEN peek called THEN return first element returned`(queue: Queue<String>) {
        queue.add("1")
        queue.add("2")

        assertEquals(actual = queue.peek, expected = "1")
        assertEquals(actual = queue.size, expected = 2)
    }

    @ParameterizedTest
    @MethodSource("queues")
    fun `WHEN isEmpty called GIVEN queue with elements THEN return false`(queue: Queue<String>) {
        queue.add("1")
        queue.add("2")

        assertEquals(actual = queue.isEmpty(), expected = false)
    }

    @ParameterizedTest
    @MethodSource("queues")
    fun `WHEN isEmpty called GIVEN empty queue THEN return true`(queue: Queue<String>) {
        assertEquals(actual = queue.isEmpty(), expected = true)
    }

    @ParameterizedTest
    @MethodSource("queues")
    fun `WHEN contains called GIVEN queue with elements and existing element THEN return true`(queue: Queue<String>) {
        queue.add("1")
        queue.add("2")
        queue.add("3")

        assertEquals(actual = queue.contains("1"), expected = true)
    }

    @ParameterizedTest
    @MethodSource("queues")
    fun `WHEN contains called GIVEN queue with elements and none existing element THEN return false`(queue: Queue<String>) {
        queue.add("1")
        queue.add("2")
        queue.add("3")

        assertEquals(actual = queue.contains("4"), expected = false)
    }

    companion object {
        @JvmStatic
        fun queues() = listOf(
            Arguments.of(DynamicArrayQueue<String>()),
            Arguments.of(LinkedListQueue<String>(SinglyLinkList())),
            Arguments.of(LinkedListQueue<String>(DoublyLinkList()))
        )
    }
}