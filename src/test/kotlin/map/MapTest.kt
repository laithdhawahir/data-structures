package map

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * A unit test for all map implementations.
 */
class MapTest {
    @ParameterizedTest
    @MethodSource("map")
    fun `WHEN put called GIVEN a list of map entries THEN map operations return expected results`(map: Map<String, Int>) {
        map.put("Test", 1)

        assertEquals(actual = map.size, expected = 1)
        assertEquals(actual = map.keys, expected = listOf("Test"))
        assertEquals(actual = map.values, expected = listOf(1))
        assertEquals(actual = map.contains("Test"), expected = true)
        assertEquals(actual = map.contains("Another"), expected = false)
        assertEquals(actual = map.entries.map { it.key to it.value }, expected = listOf("Test" to 1))
        assertEquals(actual = map["Test"], expected = 1)

        map.put("Test 2", 2)

        assertEquals(actual = map.size, expected = 2)
        assertEquals(actual = map.keys, expected = listOf("Test", "Test 2"))
        assertEquals(actual = map.values, expected = listOf(1, 2))
        assertEquals(actual = map.contains("Test"), expected = true)
        assertEquals(actual = map.contains("Test 2"), expected = true)
        assertEquals(actual = map.contains("Another"), expected = false)
        assertEquals(actual = map.entries.map { it.key to it.value }, expected = listOf("Test" to 1, "Test 2" to 2))
        assertEquals(actual = map["Test 2"], expected = 2)

        map["Test"] = 3

        assertEquals(actual = map.size, expected = 2)
        assertEquals(actual = map.keys, expected = listOf("Test", "Test 2"))
        assertEquals(actual = map.values, expected = listOf(3, 2))
        assertEquals(actual = map.contains("Test"), expected = true)
        assertEquals(actual = map.contains("Test 2"), expected = true)
        assertEquals(actual = map.contains("Another"), expected = false)
        assertEquals(actual = map["Test"], expected = 3)
        assertEquals(actual = map.entries.map { it.key to it.value }, expected = listOf("Test" to 3, "Test 2" to 2))
    }

    @ParameterizedTest
    @MethodSource("map")
    fun `WHEN set called GIVEN a list of elements that require resizing the hash table capacity THEN map operations return expected results`(
        map: Map<String, Int>
    ) {
        (1..1000).forEach { item ->
            map["Test $item"] = item

            assertEquals(actual = map.size, expected = item)
            assertEquals(actual = map.keys.toSet(), expected = (1..item).map { "Test $it" }.toSet())
            assertEquals(actual = map.values.toSet(), expected = (1..item).toSet())
            assertEquals(actual = map.contains("Test $item"), expected = true)
            assertEquals(actual = map.contains("Another"), expected = false)
            assertEquals(
                actual = map.entries.map { it.key to it.value }.toSet(),
                expected = (1..item).map { "Test $it" to it }.toSet()
            )
            assertEquals(actual = map["Test $item"], expected = item)
        }
    }

    @ParameterizedTest
    @MethodSource("map")
    fun `WHEN remove called GIVEN given a none empty map THEN map operations return expected results`(map: Map<String, Int>) {
        map.put("Test", 1)
        map.put("Test 2", 2)
        map.put("Test 3", 3)

        assertEquals(actual = map.remove("Test"), expected = true)
        assertEquals(actual = map.size, expected = 2)
        assertEquals(actual = map.keys, expected = listOf("Test 3", "Test 2"))
        assertEquals(actual = map.values, expected = listOf(3, 2))
        assertEquals(actual = map.contains("Test"), expected = false)
        assertEquals(actual = map.entries.map { it.key to it.value }, expected = listOf("Test 3" to 3, "Test 2" to 2))
        assertEquals(actual = map["Test"], expected = null)

        assertEquals(actual = map.remove("Test 2"), expected = true)
        assertEquals(actual = map.size, expected = 1)
        assertEquals(actual = map.keys, expected = listOf("Test 3"))
        assertEquals(actual = map.values, expected = listOf(3))
        assertEquals(actual = map.contains("Test 2"), expected = false)
        assertEquals(actual = map.entries.map { it.key to it.value }, expected = listOf("Test 3" to 3))
        assertEquals(actual = map["Test 2"], expected = null)

        assertEquals(actual = map.remove("Test 3"), expected = true)
        assertEquals(actual = map.size, expected = 0)
        assertEquals(actual = map.keys, expected = listOf<String>())
        assertEquals(actual = map.values, expected = listOf<Int>())
        assertEquals(actual = map.contains("Test 3"), expected = false)
        assertEquals(actual = map.entries.map { it.key to it.value }, expected = listOf())
        assertEquals(actual = map["Test 3"], expected = null)

        assertEquals(actual = map.remove("Test 3"), expected = false)
    }

    companion object {
        @JvmStatic
        fun map() = listOf(
            Arguments.of(SeparateChainingHashMap<String, Int>()),
        )
    }
}