import java.util.*

class Node(val content: Int, val connections: MutableList<Node> = mutableListOf())

fun breadthFirstSearch(node: Node) {
    val queue = ArrayDeque<Node>()
    val visitedNodes = mutableSetOf<Node>()

    queue.add(node)
    visitedNodes.add(node)

    while (queue.isNotEmpty()) {
        val curNode = queue.poll()!!

        println(curNode.content)

        for (neighborNode in curNode.connections) {
            if (neighborNode !in visitedNodes) {
                visitedNodes.add(neighborNode)
                queue.add(neighborNode)
            }
        }
    }
}

fun main() {
    val nodes = (0..12).map { Node(it) }
    val connectionsMap = mapOf(
        0 to listOf(1, 9),
        1 to listOf(8),
        9 to listOf(8),
        8 to listOf(7),
        7 to listOf(3, 6, 10, 11),
        3 to listOf(2, 4),
        6 to listOf(5)
    )

    connectionsMap.forEach { (nodeIndex, neighborNodeIndex) ->
        println("$nodeIndex to $neighborNodeIndex")
        nodes[nodeIndex].connections.addAll(neighborNodeIndex.map { nodes[it] })
    }

    breadthFirstSearch(nodes[0])
}