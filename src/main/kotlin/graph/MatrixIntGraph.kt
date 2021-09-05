package graph

import java.util.*


/**
 * Implementation of [Graph<Int>] using a matrix.
 */
class MatrixIntGraph(verticesNumber: Int, override val directedGraph: Boolean = true) : Graph<Int> {
    companion object {
        private const val DISCONNECT_VALUE = Double.NEGATIVE_INFINITY
    }

    private val graph = DoubleArray(verticesNumber * verticesNumber) { DISCONNECT_VALUE }

    override val vertices: Set<Int> = (0 until verticesNumber).toSet()
    override val edges: Set<GraphEdge<Int>>
        get() = graph.withIndex().filter { it.value != DISCONNECT_VALUE }.map {
            val firstNode = it.index / vertices.size
            val secondNode = it.index % vertices.size

            GraphEdge(firstNode to secondNode, it.value)
        }.distinctBy {
            if (directedGraph) {
                it.vertexPair
            } else {
                // As a way to make sure a directed graph do not get duplicate A to B and B to A since it is a
                // directed graph, is by picking only one of A to B or B to A, in this case I picked
                // athe min of A and B to max of A and B
                val minVertex = minOf(it.vertexPair.first, it.vertexPair.second)
                val maxVertex = maxOf(it.vertexPair.first, it.vertexPair.second)

                minVertex to maxVertex
            }
        }.toSet()

    override fun connect(firstVertex: Int, secondVertex: Int, weight: Double) {
        checkVertex(firstVertex)
        checkVertex(secondVertex)

        graph.set(firstVertex, secondVertex, weight)

        if (!directedGraph) {
            graph.set(secondVertex, firstVertex, weight)
        }
    }

    override fun disconnect(firstVertex: Int, secondVertex: Int) {
        checkVertex(firstVertex)
        checkVertex(secondVertex)

        graph.set(firstVertex, secondVertex, DISCONNECT_VALUE)

        if (!directedGraph) {
            graph.set(secondVertex, firstVertex, DISCONNECT_VALUE)
        }
    }

    override fun connected(firstVertex: Int, secondVertex: Int): Boolean {
        checkVertex(firstVertex)
        checkVertex(secondVertex)

        return graph.get(firstVertex, secondVertex) != DISCONNECT_VALUE
    }

    override fun weightOf(firstVertex: Int, secondVertex: Int): Double {
        checkVertex(firstVertex)
        checkVertex(secondVertex)

        val weight = graph.get(firstVertex, secondVertex)

        if (weight == DISCONNECT_VALUE) {
            throw IllegalArgumentException("$firstVertex and $secondVertex not connected")
        }

        return weight
    }

    override fun edgesOf(firstVertex: Int): Set<GraphEdge<Int>> {
        checkVertex(firstVertex)

        return vertices.indices.filter { connected(firstVertex, it) }
            .map { GraphEdge(firstVertex to it, weightOf(firstVertex, it)) }
            .toSet()
    }

    private fun DoubleArray.set(row: Int, col: Int, value: Double) {
        graph[row * vertices.size + col] = value
    }

    private fun DoubleArray.get(row: Int, col: Int): Double = graph[row * vertices.size + col]

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other == null || other !is Graph<*>) {
            return false
        }

        return try {
            other as Graph<Int>
            vertices == other.vertices && edges == other.edges
        } catch (ex: TypeCastException) {
            return false
        }
    }

    override fun hashCode(): Int = Objects.hash(vertices, edges)

    private fun checkVertex(vertex: Int) {
        require(vertex in vertices.indices) { "Invalid vertex number ($vertex)" }
    }

    override fun toString(): String {
        return buildString {
            for (edge in edges) {
                appendLine("${edge.vertexPair.first} to ${edge.vertexPair.second} weight ${edge.weight}")
            }
        }
    }
}