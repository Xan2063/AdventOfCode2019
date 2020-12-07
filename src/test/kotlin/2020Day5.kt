import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import org.hamcrest.Matchers.`is` as Is


class `2020Day5` {
    
    private val testInput = listOf(
        "BFFFBBFRRR",
        "FFFBBBFRRR",
        "BBFFBBFRLL",
        "FBFBBFFRLR"
    )
    private val day = "5"
    private val filename = "input2020_$day.txt"

    @Test
    fun test_part1() {

        val result = doIt(testInput)
        println(result)

    }

    private fun doIt(input: List<String>): Int {
        return input.map{ getRow(it.take(7))*8+getColumn(it.drop(7))}.max()?:0
    }

    private fun getColumn(input: String): Int {
        val res =  input.map { if(it=='R') '1' else '0' }.joinToString("").toInt(2)
        return res
    }

    private fun getRow(input: String): Int {
        val res= input.map { if(it=='B') '1' else '0' }.joinToString("")
        return res.toInt(2)
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

    private fun hasEmptySeats(seats:List<Int>):Boolean{
        return seats.toTypedArray().contentEquals(arrayOf(0..7))
    }

    private fun doIt2(input: List<String>): Any {
        val usedSeats= input.map{ getRow(it.take(7)) to getColumn(it.drop(7))}.groupBy { it.first }.filter { hasEmptySeats(it.value.map { it.second }) }
        return 0
    }
    @Test
    fun Part2() {
        val input = File(filename).readLines()
        val result = doIt2(input)
        println(result)
    }




}


