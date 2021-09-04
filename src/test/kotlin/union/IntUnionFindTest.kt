package union

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * A unit test for all implementations of UnionFind<Int>.
 */
class IntUnionFindTest {
    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN union called GIVEN first element not in the elements range and second element in range THEN throw IllegalArgumentException`(
        unionFindFactory: (Int) -> UnionFind<Int>
    ) {
        val unionFind = unionFindFactory(5)
        assertThrows<IllegalArgumentException> { unionFind.union(6, 3) }
        assertEquals( actual = unionFind.componentsCount, expected = 5)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN union called GIVEN second element not in the elements range and first element in range THEN throw IllegalArgumentException`(
        unionFindFactory: (Int) -> UnionFind<Int>
    ) {
        val unionFind = unionFindFactory(5)
        assertThrows<IllegalArgumentException> { unionFind.union(3, 6) }
        assertEquals( actual = unionFind.componentsCount, expected = 5)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN find called GIVEN invalid node THEN throw IllegalArgumentException`(
        unionFindFactory: (Int) -> UnionFind<Int>
    ) {
        val unionFind = unionFindFactory(5)
        assertThrows<IllegalArgumentException> { unionFind.find(6) }
        assertEquals( actual = unionFind.componentsCount, expected = 5)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN componentSize called GIVEN invalid node THEN throw IllegalArgumentException`(
        unionFindFactory: (Int) -> UnionFind<Int>
    ) {
        val unionFind = unionFindFactory(5)
        assertThrows<IllegalArgumentException> { unionFind.componentSize(6) }
        assertEquals( actual = unionFind.componentsCount, expected = 5)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN constructor called GIVEN zero size THEN return correct componentsCount`(
        unionFindFactory: (Int) -> UnionFind<Int>
    ) {
        val unionFind = unionFindFactory(0)
        assertEquals( actual = unionFind.componentsCount, expected = 0)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN union called GIVEN valid elements THEN unify elements and return expected componentSize and componentCount and connected`(
        unionFindFactory: (Int) -> UnionFind<Int>
    ) {
        val unionFind = unionFindFactory(5)
        unionFind.union(0, 3)

        assertEquals( actual = unionFind.connected(0, 3), expected = true)
        assertEquals( actual = unionFind.connected(3, 0), expected = true)
        assertEquals( actual = unionFind.connected(0, 1), expected = false)
        assertEquals( actual = unionFind.connected(3, 2), expected = false)
        assertEquals( actual = unionFind.connected(2, 1), expected = false)
        assertEquals( actual = unionFind.componentsCount, expected = 4)
        assertEquals( actual = unionFind.componentSize(0), expected = 2)
        assertEquals( actual = unionFind.componentSize(3), expected = 2)
        assertEquals( actual = unionFind.componentSize(1), expected = 1)
        assertEquals( actual = unionFind.componentSize(2), expected = 1)
        assertEquals( actual = unionFind.componentSize(4), expected = 1)

        unionFind.union(1, 2)

        assertEquals( actual = unionFind.connected(0, 3), expected = true)
        assertEquals( actual = unionFind.connected(1, 2), expected = true)
        assertEquals( actual = unionFind.connected(3, 2), expected = false)
        assertEquals( actual = unionFind.connected(2, 1), expected = true)
        assertEquals( actual = unionFind.componentsCount, expected = 3)
        assertEquals( actual = unionFind.componentSize(0), expected = 2)
        assertEquals( actual = unionFind.componentSize(3), expected = 2)
        assertEquals( actual = unionFind.componentSize(1), expected = 2)
        assertEquals( actual = unionFind.componentSize(2), expected = 2)
        assertEquals( actual = unionFind.componentSize(4), expected = 1)

        unionFind.union(0, 2)
        assertEquals( actual = unionFind.connected(0, 3), expected = true)
        assertEquals( actual = unionFind.connected(1, 2), expected = true)
        assertEquals( actual = unionFind.connected(3, 2), expected = true)
        assertEquals( actual = unionFind.connected(2, 1), expected = true)
        assertEquals( actual = unionFind.componentsCount, expected = 2)
        assertEquals( actual = unionFind.componentSize(0), expected = 4)
        assertEquals( actual = unionFind.componentSize(3), expected = 4)
        assertEquals( actual = unionFind.componentSize(1), expected = 4)
        assertEquals( actual = unionFind.componentSize(2), expected = 4)
        assertEquals( actual = unionFind.componentSize(4), expected = 1)
    }

    @ParameterizedTest
    @MethodSource("unionFindFactoryMethod")
    fun `WHEN find called GIVEN valid element THEN return expected root node`(
        unionFindFactory: (Int) -> UnionFind<Int>
    ) {
        val unionFind = unionFindFactory(5)

        unionFind.union(0, 3)
        assertEquals( actual = unionFind.find(0), expected = 3)
        assertEquals( actual = unionFind.find(3), expected = 3)

        unionFind.union(1, 3)
        assertEquals( actual = unionFind.find(1), expected = 3)
    }

    companion object {
        @JvmStatic
        fun unionFindFactoryMethod() = listOf(
            Arguments.of({ size: Int -> ArrayBaseIntUnionFind(size) as UnionFind<Int> })
        )
    }
}
