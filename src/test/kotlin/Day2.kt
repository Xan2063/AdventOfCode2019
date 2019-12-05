import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import org.hamcrest.Matchers.`is` as Is

class Day2 {

    //    1,0,0,0,99 becomes 2,0,0,0,99 (1 + 1 = 2).
//    2,3,0,3,99 becomes 2,3,0,6,99 (3 * 2 = 6).
//    2,4,4,5,99,0 becomes 2,4,4,5,99,9801 (99 * 99 = 9801).
//    1,1,1,4,99,5,6,0,99 becomes 30,1,1,4,2,5,6,0,99
    @Test
    fun test_processing() {
        val tests = mapOf(
            arrayOf(1, 0, 0, 0, 99) to arrayOf(2, 0, 0, 0, 99),
            arrayOf(2, 3, 0, 3, 99) to arrayOf(2, 3, 0, 6, 99),
            arrayOf(2, 4, 4, 5, 99, 0) to arrayOf(2, 4, 4, 5, 99, 9801),
            arrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99) to arrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99)
        )
        val input = arrayOf(1, 0, 0, 0, 99)
        tests.forEach {
            val result = processProgramm(it.key, 0)

            assertThat(result, Is(it.value))
        }
    }

    @Test
    fun Part2() {
        val input = File("input2.txt").readLines()[0].split(',').map { it.toInt() }.toTypedArray()
        val result = generateSequence(0 to 0) {
            if (it.second >= 99)
                it.first + 1 to 0
            else
                it.first to it.second + 1
        }
            .first { p ->
                val data = input.copyOf()
                data[1] = p.first
                data[2] = p.second
                val output = processProgramm(data, 0)
                output[0] == 19690720
            }

        val res = result.first * 100 + result.second
        println(res)
    }

    @Test
    fun Part1() {
        val input = File("input2.txt").readLines()[0].split(',')
        val ar = input.map { it.toInt() }.toTypedArray()
        ar[1] = 12
        ar[2] = 2
        val data = processProgramm(ar, 0)
        println(data[0])

        //replace position 1 with the value 12 and replace position 2 with the value 2.
    }

    tailrec fun processProgramm(data: Array<Int>, commandPointer: Int): Array<Int> {
        val command = data[commandPointer]
        return when (command) {
            99 -> data
            else -> {
                processInput(data, commandPointer)
                processProgramm(data, commandPointer + 4)
            }
        }
    }


    fun processInput(data: Array<Int>, commandPointer: Int): Array<Int> {
        val command = data[commandPointer]
        val operand1 = data[data[commandPointer + 1]]
        val operand2 = data[data[commandPointer + 2]]
        val resultPointer = data[commandPointer + 3]

        when (command) {
            1 -> data[resultPointer] = operand1 + operand2
            2 -> data[resultPointer] = operand1 * operand2
        }
        return data
    }
}


