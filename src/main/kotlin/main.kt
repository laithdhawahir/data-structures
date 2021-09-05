import graph.MatrixGraph
import graph.kruskalMinimumSpanningTree

fun main() {
    val graph = MatrixGraph(vertices = setOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'), directedGraph = false)

    with(graph) {
        connect('A', 'B', 5.0)
        connect('A', 'E', 1.0)
        connect('A', 'D', 9.0)

        connect('B', 'D', 2.0)
        connect('B', 'C', 4.0)

        connect('C', 'H', 4.0)
        connect('C', 'I', 1.0)
        connect('C', 'J', 8.0)

        connect('D', 'E', 2.0)
        connect('D', 'H', 2.0)
        connect('D', 'G', 11.0)
        connect('D', 'F', 5.0)

        connect('E', 'F', 1.0)

        connect('F', 'G', 7.0)

        connect('G', 'H', 1.0)
        connect('G', 'I', 4.0)

        connect('H', 'I', 6.0)

        connect('I', 'J', 0.0)
    }

    println("Before MST")
    println(graph)

    println()
    println("After MST")
    println(graph.kruskalMinimumSpanningTree())
}