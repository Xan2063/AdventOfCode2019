import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import org.hamcrest.Matchers.`is` as Is


class `2020Day3` {

    private val testInput = listOf(
       "..##.......",
       "#...#...#..",
       ".#....#..#.",
       "..#.#...#.#",
       ".#...##..#.",
       "..#.##.....",
       ".#.#.#....#",
       ".#........#",
       "#.##...#...",
       "#...##....#",
       ".#..#...#.#",
    )
    private val day = "3"
    private val filename = "input2020_$day.txt"

    @Test
    fun test_part1() {

        val result = doIt(testInput)
        println(result)

    }

    private fun doIt(input: List<String>): Int {
        val width = input.first().length
        val points = createPoints(3 to 1,input.size).toList()
        return points.filter { input[it.second][it.first % width].isTree() }.count()
    }

    fun Char.isTree():Boolean = this=='#'

    fun createPoints(pattern:Pair<Int, Int>,height:Int):Sequence <Pair<Int, Int>>{
        return generateSequence((0 to 0), { Pair(it.first+pattern.first, it.second+pattern.second) }).takeWhile { it.second<height }
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

    private fun doIt2(input: List<String>): Long {
        val patterns = (1..7 step 2).map { it to 1 }.union(listOf(1 to 2))
        val results = patterns.map { getTrees(input, it).toLong() }.reduce { acc, i ->  acc * i }


        return results
    }

    fun getTrees(input: List<String>, pattern: Pair<Int, Int>):Int{
        val width = input.first().length
        val points = createPoints(pattern,input.size).toList()
        val res = points.filter { input[it.second][it.first % width].isTree() }.count()
        return res
    }

    @Test
    fun Part2() {
        val input = File(filename).readLines()
        val result = doIt2(input)
        println(result)
    }


}


