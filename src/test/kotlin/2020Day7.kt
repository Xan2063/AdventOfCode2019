import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import org.hamcrest.Matchers.`is` as Is


class `2020Day7` {

    private val testInput = listOf(
        "light red bags contain 1 bright white bag, 2 muted yellow bags.",
        "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
        "bright white bags contain 1 shiny gold bag.",
        "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
        "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
        "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
        "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
        "faded blue bags contain no other bags.",
        "dotted black bags contain no other bags.",
    )
    private val day = "7"
    private val filename = "input2020_$day.txt"

    @Test
    fun test_part1() {

        val result = doIt(testInput)
        println(result)

    }


    private fun doIt(input: List<String>): Any {
        val bags = input.map { Regex("(.*)\\sbags contain (.*)\\.$").matchEntire(it) }
            .mapNotNull { res ->
                res!!.groupValues[1].trim() to (res.groupValues[2].split(',')
                    .map { Regex("(?:\\s?\\d\\s)?(.*)bag[s]?").matchEntire(it)!!.groupValues[1].trim() })
            }
        val lookup = bags.flatMap { it.second.map { sec -> sec to it.first } }


        tailrec fun getPredecessors(value: String): List<String> {
            val values = lookup.filter { it.first == value }.map { it.second }
            return values + values.flatMap { getPredecessors(it) }
        }
        val res = getPredecessors("shiny gold")

        return res.distinct().count()
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

    fun createBagsDesc(string: String): Pair<Int, String> {
        if(string.startsWith("no")) return 0 to string.trim()
        return string.take(1).toInt() to string.drop(1).trim()
    }

    private fun doIt2(input: List<String>): Any {
        val bags = input.map { Regex("(.*)\\sbags contain (.*)\\.$").matchEntire(it) }
            .mapNotNull { res ->
                res!!.groupValues[1].trim() to (res.groupValues[2].split(',')
                    .map { Regex("(.*)bag[s]?").matchEntire(it)!!.groupValues[1].trim() }
                    .map(this::createBagsDesc))
            }
        val lookup = bags.toMap()


        tailrec fun getSuccessors(value: String): Int {
            when(val x =lookup[value]){
                null->return 1
                else->return x.map { it.first*getSuccessors(it.second) }.sum()+1
            }
        }
        val res = getSuccessors("shiny gold")

        return res-1
    }

    @Test
    fun Part2() {
        val input = File(filename).readLines()
        val result = doIt2(input)
        println(result)
    }


}


