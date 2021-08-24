import dynamicarray.DynamicArray
import dynamicarray.dynamicArrayOf

fun main(args: Array<String>) {
    val dynamicArray = DynamicArray<String>()
    dynamicArray.append("First")
    dynamicArray.append("Second")

    println(dynamicArray[0])
    println(dynamicArray[1])
    println(dynamicArray.size)
    println(dynamicArray.capacity)
}