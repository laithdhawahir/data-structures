package map

/**
 * Interface for map entry.
 */
interface MapEntry<T, R> {
    val key: T
    val value: R
}

/**
 * An interface for the Map data structure.
 */
interface Map<T, R> : Iterable<MapEntry<T, R>> {
    /**
     * Map size.
     */
    val size: Int

    /**
     * Map keys.
     */
    val keys: List<T>

    /**
     *  Map values.
     */
    val values: List<R>

    /**
     * Map key-value entries list.
     */
    val entries: List<MapEntry<T, R>>

    /**
     * @return True if map is empty, otherwise, false.
     */
    fun isEmpty() = size == 0

    /**
     * Put a key-value pair in the map.
     */
    fun put(key: T, value: R)

    /**
     * Remove value from the map.
     */
    fun remove(key: T): Boolean

    /**
     * @return True if given key in the map.
     */
    operator fun contains(key: T): Boolean

    /**
     * @return The value associated with the given key if found, otherwise, null
     */
    operator fun get(key: T): R?

    /**
     * Set a key-value pair to the map.
     */
    operator fun set(key: T, value: R)
}