package graph

import java.util.*


/**
 * Implementation of [Graph<T>] using a matrix.
 */
class MatrixGraph<T>(override val vertices: Set<T>, override val directedGraph: Boolean = true) : Graph<T> {
    private val vertexToNodeIdMap = vertices.mapIndexed { index, vertex -> vertex to index }.toMap()
    private val nodeIdToVertexMap = vertices.mapIndexed { index, vertex -> index to vertex }.toMap()

    private val graph = MatrixIntGraph(vertices.size, directedGraph)

    override val edges: Set<GraphEdge<T>>
        get() = graph.edges.map(::nodeIdEdgeToActualEdge).toSet()

    override fun connect(firstVertex: T, secondVertex: T, weight: Double) {
        checkVertex(firstVertex)
        checkVertex(secondVertex)

        graph.connect(vertexToNodeIdMap[firstVertex]!!, vertexToNodeIdMap[secondVertex]!!, weight)
    }

    override fun disconnect(firstVertex: T, secondVertex: T) {
        checkVertex(firstVertex)
        checkVertex(secondVertex)

        graph.disconnect(vertexToNodeIdMap[firstVertex]!!, vertexToNodeIdMap[secondVertex]!!)
    }

    override fun connected(firstVertex: T, secondVertex: T): Boolean {
        checkVertex(firstVertex)
        checkVertex(secondVertex)

        return graph.connected(vertexToNodeIdMap[firstVertex]!!, vertexToNodeIdMap[secondVertex]!!)
    }

    override fun weightOf(firstVertex: T, secondVertex: T): Double {
        checkVertex(firstVertex)
        checkVertex(secondVertex)

        return graph.weightOf(vertexToNodeIdMap[firstVertex]!!, vertexToNodeIdMap[secondVertex]!!)
    }

    override fun edgesOf(firstVertex: T): Set<GraphEdge<T>> {
        return graph.edges.map(::nodeIdEdgeToActualEdge).toSet()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other == null || other !is Graph<*>) {
            return false
        }

        return try {
            other as Graph<T>
            vertices == other.vertices && edges == other.edges
        } catch (ex: TypeCastException) {
            return false
        }
    }

    override fun hashCode(): Int = Objects.hash(vertices, edges)

    private fun nodeIdEdgeToActualEdge(edge: GraphEdge<Int>): GraphEdge<T> {
        val vertexPair = nodeIdToVertexMap[edge.vertexPair.first]!! to nodeIdToVertexMap[edge.vertexPair.second]!!
        return GraphEdge(vertexPair, edge.weight)
    }

    private fun checkVertex(vertex: T) {
        require(vertex in vertices) { "Invalid vertex $vertex" }
    }

    override fun toString(): String {
        return buildString {
            for (edge in edges) {
                appendLine("${edge.vertexPair.first} to ${edge.vertexPair.second} weight ${edge.weight}")
            }
        }
    }
}