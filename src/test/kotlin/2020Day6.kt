import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import org.hamcrest.Matchers.`is` as Is


class `2020Day6` {

    private val testInput = listOf(
        "abc",
        "",
        "a",
        "b",
        "c",
        "",
        "ab",
        "ac",
        "",
        "a",
        "a",
        "a",
        "a",
        "",
        "b",
    )
    private val day = "6"
    private val filename = "input2020_$day.txt"

    @Test
    fun test_part1() {

        val result = doIt(testInput)
        println(result)

    }

    private fun doIt(input: List<String>): Any {
        val forms = input.joinToString("|").split("||").map { it.replace("|", "") }
        val distforms = forms.map { it.asSequence().distinct() }
        return distforms.map { it.count() }.sum()
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
        val forms = input.joinToString("|").split("||").map { it.split('|') }
        val distforms = forms.map{it.reduce{acc, list ->acc.toList().intersect(list.asIterable()).joinToString("")  }}
        return distforms.map { it.count() }.sum()
    }


    @Test
    fun Part2() {
        val input = File(filename).readLines()
        val result = doIt2(input)
        println(result)
    }


}


