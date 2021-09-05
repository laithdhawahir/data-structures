package graph

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * A unit test for all [Graph<Int>] implementations.
 */
class IntGraphTest {
    @ParameterizedTest
    @MethodSource("graphFactory")
    fun `WHEN vertices called GIVEN none empty graph THEN return graph expected vertices`(graphFactory: (Int, Boolean) -> Graph<Int>) {
        val graph = graphFactory(5, true)

        assertEquals(actual = graph.vertices, expected = setOf(0, 1, 2, 3, 4))
        assertEquals(actual = graph.edges, expected = setOf())
        assertEquals(actual = graph.directedGraph, expected = true)
    }

    @ParameterizedTest
    @MethodSource("graphFactory")
    fun `WHEN edges called GIVEN none empty graph with edges THEN return graph expected edges`(graphFactory: (Int, Boolean) -> Graph<Int>) {
        val graph = graphFactory(5, true)
        graph.connect(0, 1, 1.0)
        graph.connect(1, 2, 5.0)
        graph.connect(4, 0, 8.0)

        assertEquals(actual = graph.vertices, expected = setOf(0, 1, 2, 3, 4))
        assertEquals(
            actual = graph.edges,
            expected = setOf(
                GraphEdge(0 to 1, 1.0),
                GraphEdge(1 to 2, 5.0),
                GraphEdge(4 to 0, 8.0)
            )
        )

        assertEquals(graph.connected(0, 1), true)
        assertEquals(graph.connected(1, 2), true)
        assertEquals(graph.connected(2, 1), false)
        assertEquals(graph.connected(4, 0), true)
        assertEquals(graph.connected(0, 3), false)

        assertEquals(actual = graph.directedGraph, expected = true)
    }

    @ParameterizedTest
    @MethodSource("graphFactory")
    fun `WHEN disconnect called GIVEN none empty graph with edges THEN disconnected edges get disconnected`(graphFactory: (Int, Boolean) -> Graph<Int>) {
        val graph = graphFactory(5, true)
        graph.connect(0, 1, 1.0)
        graph.connect(1, 2, 5.0)
        graph.connect(4, 0, 8.0)

        graph.disconnect(4, 0)

        assertEquals(actual = graph.vertices, expected = setOf(0, 1, 2, 3, 4))
        assertEquals(
            actual = graph.edges,
            expected = setOf(
                GraphEdge(0 to 1, 1.0),
                GraphEdge(1 to 2, 5.0)
            )
        )

        assertEquals(graph.connected(0, 1), true)
        assertEquals(graph.connected(1, 2), true)
        assertEquals(graph.connected(2, 1), false)
        assertEquals(graph.connected(4, 0), false)
        assertEquals(graph.connected(0, 3), false)

        assertEquals(actual = graph.directedGraph, expected = true)
    }

    @ParameterizedTest
    @MethodSource("graphFactory")
    fun `WHEN directedGraph set to false GIVEN none empty graph with edges THEN all operations works as expected`(graphFactory: (Int, Boolean) -> Graph<Int>) {
        val graph = graphFactory(5, false)
        graph.connect(0, 1, 1.0)
        graph.connect(1, 2, 5.0)
        graph.connect(4, 0, 8.0)

        graph.disconnect(4, 0)

        assertEquals(actual = graph.vertices, expected = setOf(0, 1, 2, 3, 4))
        assertEquals(
            actual = graph.edges,
            expected = setOf(
                GraphEdge(0 to 1, 1.0),
                GraphEdge(1 to 2, 5.0)
            )
        )

        assertEquals(graph.connected(0, 1), true)
        assertEquals(graph.connected(1, 0), true)

        assertEquals(graph.connected(1, 2), true)
        assertEquals(graph.connected(2, 1), true)

        assertEquals(graph.connected(4, 0), false)
        assertEquals(graph.connected(0, 4), false)

        assertEquals(actual = graph.directedGraph, expected = false)
    }

    companion object {
        @JvmStatic
        fun graphFactory() = listOf(
            Arguments.of({ size: Int, directed: Boolean -> MatrixIntGraph(size, directed) })
        )
    }
}