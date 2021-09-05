package graph

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals


/**
 * A unit test for all [Graph<T>] implementations.
 */
class GraphTest {
    @ParameterizedTest
    @MethodSource("graphFactory")
    fun `WHEN vertices called GIVEN none empty graph THEN return graph expected vertices`(graphFactory: (Set<String>, Boolean) -> Graph<String>) {
        val graph = graphFactory(setOf("A", "B", "C", "D", "E"), true)

        assertEquals(actual = graph.vertices, expected = setOf("A", "B", "C", "D", "E"))
        assertEquals(actual = graph.edges, expected = setOf())
        assertEquals(actual = graph.directedGraph, expected = true)
    }

    @ParameterizedTest
    @MethodSource("graphFactory")
    fun `WHEN edges called GIVEN none empty graph with edges THEN return graph expected edges`(graphFactory: (Set<String>, Boolean) -> Graph<String>) {
        val graph = graphFactory(setOf("A", "B", "C", "D", "E"), true)
        graph.connect("A", "B", 1.0)
        graph.connect("B", "C", 5.0)
        graph.connect("E", "A", 8.0)

        assertEquals(actual = graph.vertices, expected = setOf("A", "B", "C", "D", "E"))
        assertEquals(
            actual = graph.edges,
            expected = setOf(
                GraphEdge("A" to "B", 1.0),
                GraphEdge("B" to "C", 5.0),
                GraphEdge("E" to "A", 8.0)
            )
        )

        assertEquals(graph.connected("A", "B"), true)
        assertEquals(graph.connected("B", "C"), true)
        assertEquals(graph.connected("C", "B"), false)
        assertEquals(graph.connected("E", "A"), true)
        assertEquals(graph.connected("A", "D"), false)

        assertEquals(actual = graph.directedGraph, expected = true)
    }

    @ParameterizedTest
    @MethodSource("graphFactory")
    fun `WHEN disconnect called GIVEN none empty graph with edges THEN disconnected edges get disconnected`(graphFactory: (Set<String>, Boolean) -> Graph<String>) {
        val graph = graphFactory(setOf("A", "B", "C", "D", "E"), true)
        graph.connect("A", "B", 1.0)
        graph.connect("B", "C", 5.0)
        graph.connect("E", "A", 8.0)

        graph.disconnect("E", "A")

        assertEquals(actual = graph.vertices, expected = setOf("A", "B", "C", "D", "E"))
        assertEquals(
            actual = graph.edges,
            expected = setOf(
                GraphEdge("A" to "B", 1.0),
                GraphEdge("B" to "C", 5.0)
            )
        )

        assertEquals(graph.connected("A", "B"), true)
        assertEquals(graph.connected("B", "C"), true)
        assertEquals(graph.connected("C", "B"), false)
        assertEquals(graph.connected("E", "A"), false)
        assertEquals(graph.connected("A", "D"), false)

        assertEquals(actual = graph.directedGraph, expected = true)
    }

    @ParameterizedTest
    @MethodSource("graphFactory")
    fun `WHEN directedGraph set to false GIVEN none empty graph with edges THEN all operations works as expected`(
        graphFactory: (Set<String>, Boolean) -> Graph<String>
    ) {
        val graph = graphFactory(setOf("A", "B", "C", "D", "E"), false)
        graph.connect("A", "B", 1.0)
        graph.connect("B", "C", 5.0)
        graph.connect("E", "A", 8.0)

        graph.disconnect("E", "A")

        assertEquals(actual = graph.vertices, expected = setOf("A", "B", "C", "D", "E"))
        assertEquals(
            actual = graph.edges,
            expected = setOf(
                GraphEdge("A" to "B", 1.0),
                GraphEdge("B" to "C", 5.0)
            )
        )

        assertEquals(graph.connected("A", "B"), true)
        assertEquals(graph.connected("B", "A"), true)

        assertEquals(graph.connected("B", "C"), true)
        assertEquals(graph.connected("C", "B"), true)

        assertEquals(graph.connected("E", "A"), false)
        assertEquals(graph.connected("A", "E"), false)

        assertEquals(actual = graph.directedGraph, expected = false)
    }

    companion object {
        @JvmStatic
        fun graphFactory() = listOf(
            Arguments.of({ vertices: Set<String>, directed: Boolean -> MatrixGraph(vertices, directed) })
        )
    }
}