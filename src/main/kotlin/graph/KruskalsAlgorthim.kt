package graph

import union.ArrayBaseUnionFind
import union.UnionFind

/**
 * Implementation of Kruskal minimum spanning tree algorithm.
 */
fun <T> Graph<T>.kruskalMinimumSpanningTree(): Graph<T> {
    val minSpanningTreeGraph = MatrixGraph<T>(this.vertices, this.directedGraph)
    val unionFind: UnionFind<T> = ArrayBaseUnionFind(this.vertices)

    // 1. sort edges in ascending order
    val sortedEdges = this.edges.sortedBy { it.weight }
    var edgesCount = 0 // when edges count is vertices.size - 1 this mean all of them have been unified

    // walk through sorted edges and add edges to the new graph only if it have not been unified before.
    // stop looping when every edge has been processed or all vertices have been unified.
    for (edge in sortedEdges) {
        if (!unionFind.connected(edge.vertexPair.first, edge.vertexPair.second)) {
            unionFind.union(edge.vertexPair.first, edge.vertexPair.second)
            minSpanningTreeGraph.connect(edge.vertexPair.first, edge.vertexPair.second, edge.weight)
            edgesCount++
        }

        // This mean all vertices have been unified
        if (edgesCount >= vertices.size - 1) {
            break
        }
    }

    return minSpanningTreeGraph
}