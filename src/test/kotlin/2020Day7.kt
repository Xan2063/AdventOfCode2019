import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import org.hamcrest.Matchers.`is` as Is


class `2020Day7` {
    
    private val testInput = listOf(

    )
    private val day = "7"
    private val filename = "input2020_$day.txt"

    @Test
    fun test_part1() {

        val result = doIt(testInput)
        println(result)

    }

    private fun doIt(input: List<String>): Any {
        TODO("Not yet implemented")
    }

    @Test
    fun Part1() {
        val input = File(filename).readLines()
        val result = doIt(input)
        println(result)
    }

    @Test
    fun test_part2() {
        val result = doIt2(testInput)
        println(result)

    }

    private fun doIt2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
    @Test
    fun Part2() {
        val input = File(filename).readLines()
        val result = doIt2(input)
        println(result)
    }




}


