package union

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class UnionFindTest {
    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN union called GIVEN first element not in the elements range and second element in range THEN throw IllegalArgumentException`(
        unionFindFactory: (Set<String>) -> UnionFind<String>
    ) {
        val unionFind = unionFindFactory(setOf("A", "B", "C", "D", "E"))
        assertThrows<IllegalArgumentException> { unionFind.union("F", "C") }
        assertEquals(actual = unionFind.componentsCount, expected = 5)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN union called GIVEN second element not in the elements range and first element in range THEN throw IllegalArgumentException`(
        unionFindFactory: (Set<String>) -> UnionFind<String>
    ) {
        val unionFind = unionFindFactory(setOf("A", "B", "C", "D", "E"))
        assertThrows<IllegalArgumentException> { unionFind.union("C", "F") }
        assertEquals(actual = unionFind.componentsCount, expected = 5)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN find called GIVEN invalid node THEN throw IllegalArgumentException`(
        unionFindFactory: (Set<String>) -> UnionFind<String>
    ) {
        val unionFind = unionFindFactory(setOf("A", "B", "C", "D", "E"))
        assertThrows<IllegalArgumentException> { unionFind.find("F") }
        assertEquals(actual = unionFind.componentsCount, expected = 5)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN componentSize called GIVEN invalid node THEN throw IllegalArgumentException`(
        unionFindFactory: (Set<String>) -> UnionFind<String>
    ) {
        val unionFind = unionFindFactory(setOf("A", "B", "C", "D", "E"))
        assertThrows<IllegalArgumentException> { unionFind.componentSize("F") }
        assertEquals(actual = unionFind.componentsCount, expected = 5)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN constructor called GIVEN zero size THEN return correct componentsCount`(
        unionFindFactory: (Set<String>) -> UnionFind<String>
    ) {
        val unionFind = unionFindFactory(setOf())
        assertEquals(actual = unionFind.componentsCount, expected = 0)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN union called GIVEN valid elements THEN unify elements and return expected componentSize and componentCount and connected`(
        unionFindFactory: (Set<String>) -> UnionFind<String>
    ) {
        val unionFind = unionFindFactory(setOf("A", "B", "C", "D", "E"))
        unionFind.union("A", "D")

        assertEquals(actual = unionFind.connected("A", "D"), expected = true)
        assertEquals(actual = unionFind.connected("D", "A"), expected = true)
        assertEquals(actual = unionFind.connected("A", "B"), expected = false)
        assertEquals(actual = unionFind.connected("D", "C"), expected = false)
        assertEquals(actual = unionFind.connected("D", "B"), expected = false)
        assertEquals(actual = unionFind.componentsCount, expected = 4)
        assertEquals(actual = unionFind.componentSize("A"), expected = 2)
        assertEquals(actual = unionFind.componentSize("D"), expected = 2)
        assertEquals(actual = unionFind.componentSize("B"), expected = 1)
        assertEquals(actual = unionFind.componentSize("C"), expected = 1)
        assertEquals(actual = unionFind.componentSize("E"), expected = 1)

        unionFind.union("B", "C")

        assertEquals(actual = unionFind.connected("A", "D"), expected = true)
        assertEquals(actual = unionFind.connected("B", "C"), expected = true)
        assertEquals(actual = unionFind.connected("D", "C"), expected = false)
        assertEquals(actual = unionFind.connected("C", "B"), expected = true)
        assertEquals(actual = unionFind.componentsCount, expected = 3)
        assertEquals(actual = unionFind.componentSize("A"), expected = 2)
        assertEquals(actual = unionFind.componentSize("D"), expected = 2)
        assertEquals(actual = unionFind.componentSize("B"), expected = 2)
        assertEquals(actual = unionFind.componentSize("C"), expected = 2)
        assertEquals(actual = unionFind.componentSize("E"), expected = 1)

        unionFind.union("A", "C")
        assertEquals(actual = unionFind.connected("A", "D"), expected = true)
        assertEquals(actual = unionFind.connected("B", "C"), expected = true)
        assertEquals(actual = unionFind.connected("D", "C"), expected = true)
        assertEquals(actual = unionFind.connected("C", "B"), expected = true)
        assertEquals(actual = unionFind.componentsCount, expected = 2)
        assertEquals(actual = unionFind.componentSize("A"), expected = 4)
        assertEquals(actual = unionFind.componentSize("D"), expected = 4)
        assertEquals(actual = unionFind.componentSize("B"), expected = 4)
        assertEquals(actual = unionFind.componentSize("C"), expected = 4)
        assertEquals(actual = unionFind.componentSize("E"), expected = 1)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN find called GIVEN valid element THEN return expected root node`(
        unionFindFactory: (Set<String>) -> UnionFind<String>
    ) {
        val unionFind = unionFindFactory(setOf("A", "B", "C", "D", "E"))

        unionFind.union("A", "D")
        assertEquals(actual = unionFind.find("A"), expected = "D")
        assertEquals(actual = unionFind.find("D"), expected = "D")

        unionFind.union("B", "D")
        assertEquals(actual = unionFind.find("B"), expected = "D")
    }

    companion object {
        @JvmStatic
        fun unionFindFactoryMethod() = listOf(
            Arguments.of({ elements: Set<String> -> ArrayBaseUnionFind(elements) as UnionFind<String> })
        )
    }
}