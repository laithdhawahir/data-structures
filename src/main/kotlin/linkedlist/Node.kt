package linkedlist

interface Node<T> {
    var content: T
}

class SinglyNode<T>(override var content: T, var next: SinglyNode<T>? = null) : Node<T>
class DoublyNode<T>(override var content: T, var next: DoublyNode<T>? = null, var previous: DoublyNode<T>? = null) :
    Node<T>