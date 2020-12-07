import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import org.hamcrest.Matchers.`is` as Is
import org.hamcrest.Matchers.`is` as Is

class `2020Day1` {

    //    1,0,0,0,99 becomes 2,0,0,0,99 (1 + 1 = 2).
//    2,3,0,3,99 becomes 2,3,0,6,99 (3 * 2 = 6).
//    2,4,4,5,99,0 becomes 2,4,4,5,99,9801 (99 * 99 = 9801).
//    1,1,1,4,99,5,6,0,99 becomes 30,1,1,4,2,5,6,0,99
    @Test
    fun test_processing() {
        val testinput = listOf(1721,
            979,
            366,
            299,
            675,
            1456)

        val result = findPairWithSum(testinput, 2020)
        assertThat(result, Is(1721 to 299))

    }
    @Test
    fun test_processing2() {
        val testinput = listOf(1721,
            979,
            366,
            299,
            675,
            1456)

        val result = findTriple(testinput)
        assertThat(result, Is(1721 to 299))

    }

    fun findPairWithSum(input:List<Int>, sum: Int):Pair<Int, Int>{
        val values = input.map{sum-it}.toSet()
        return input.first { values.contains(it) }.let { it to sum-it }
    }

    fun maybefindPairWithSum(input:List<Int>, sum: Int):Pair<Int, Int>?{
        val values = input.map{sum-it}.toSet()
        return input.firstOrNull { it in values }?.let { it to sum-it }
    }

    @Test
    fun Part1() {
        val input = File("input2020_1.txt").readLines().map { it.toInt() }
        val result =  findPairWithSum(input, 2020)
        println(result)
        println(result.first*result.second)
    }

    @Test
    fun Part2() {
        val input = File("input2020_1.txt").readLines().map { it.toInt() }
        val result = findTriple(input)

        println(result)
        println(result.first*result.second*(2020-result.first-result.second))
    }

    private fun findTriple(input: List<Int>): Pair<Int, Int> {
       val pair = input.mapIndexed { index, i -> maybefindPairWithSum(input.drop(index), 2020 -i)  }.filterNotNull().first()
        return pair
    }


}


