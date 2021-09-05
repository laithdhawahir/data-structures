package graph

/**
 * GraphEdge<T> represent a weighted connection between two graph nodes.
 */
data class GraphEdge<T>(
    val vertexPair: Pair<T, T>,
    val weight: Double = 0.0
) {

    // Overriding equals and hashCode to make GraphEdge comparison only based on the vertex pair.
    @Suppress("UNCHECKED_CAST")
    override fun equals(other: Any?): Boolean {
        return when {
            other === this -> true
            other !is GraphEdge<*> -> false
            else -> try {
                other as GraphEdge<T>
                other.vertexPair == this.vertexPair
            } catch (ex: TypeCastException) {
                false
            }
        }
    }

    override fun hashCode(): Int = vertexPair.hashCode()
}

/**
 * Interface for the graph data structure.
 */
interface Graph<T> {
    /**
     * Graph edges.
     */
    val edges: Set<GraphEdge<T>>

    /**
     * Graph vertices.
     */
    val vertices: Set<T>

    /**
     * True if graph is a directed graph, otherwise, false for undirected graph.
     */
    val directedGraph: Boolean

    /**
     * Connect two graph vertices with weight.
     */
    fun connect(firstVertex: T, secondVertex: T, weight: Double)

    /**
     * Disconnect two graph vertices.
     */
    fun disconnect(firstVertex: T, secondVertex: T)

    /**
     * True if the given two graph vertices are connected. otherwise, false.a
     */
    fun connected(firstVertex: T, secondVertex: T): Boolean

    /**
     * @return the weight of edge between the given two vertices. if vertices are not connect,
     * it throw IllegalArgumentException
     */
    fun weightOf(firstVertex: T, secondVertex: T): Double

    /**
     * @return the edges of all vertices connected with the given vertex.
     */
    fun edgesOf(firstVertex: T): Set<GraphEdge<T>>
}