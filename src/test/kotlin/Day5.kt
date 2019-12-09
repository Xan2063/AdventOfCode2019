import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import java.io.File
import java.lang.Exception

class Day5 {


    @Test
    fun Part1() {
        val input = File("input5.txt").readLines()[0].split(',')
        val ar = input.map { it.toInt() }.toTypedArray()
        val data = processProgramm(ar, 0)
        println(data[0])

        //replace position 1 with the value 12 and replace position 2 with the value 2.
    }

    tailrec fun processProgramm(data: Array<Int>, commandPointer: Int): Array<Int> {
        return when (data[commandPointer]) {
            99 -> data
            else -> {
                val nextCommand =  processInput(data, commandPointer)
                processProgramm(data,nextCommand)
            }
        }
    }

    enum class OperandMode{
        Position,
        Immediate

    }

    fun getOperandMode(value: Char) = if(value == '0') OperandMode.Position else OperandMode.Immediate


    fun getOperand(commandPointer: Int, data: Array<Int>, offset: Int = 1):Int {
        val command = data[commandPointer].toString()

        return when(operandMode(command, offset)){
            OperandMode.Position -> data[data[commandPointer+offset]]
            OperandMode.Immediate -> data[commandPointer+offset]
        }
    }

    private fun operandMode(command: String, offset: Int) =
        getOperandMode(command.reversed().getOrElse(offset + 1, { '0' }))

    fun getCommand(command:Int)= command.toString().takeLast(2).toInt()

    fun getTwoOperands(commandPointer: Int, data: Array<Int>)=
        getOperand(commandPointer, data, 1) to getOperand(commandPointer, data, 2)

    fun processInput(data: Array<Int>, commandPointer: Int): Int {
        val command = getCommand(data[commandPointer])

        return when (command) {
            1 -> {data[data[commandPointer+3]] = getTwoOperands(commandPointer, data).let { (a,b)->a+b }; commandPointer+4}
            2 -> {data[data[commandPointer+3]] = getTwoOperands(commandPointer, data).let { (a,b)->a*b };  commandPointer+4}
            3-> {data[data[commandPointer+1]] = 5;commandPointer +2}
            4 -> {println("Out ${getOperand(commandPointer, data)}"); commandPointer+2}
            5-> {getTwoOperands(commandPointer, data).let { (a,b)-> if(a!=0) b else commandPointer+3}}
            6-> {getTwoOperands(commandPointer, data).let { (a,b)-> if(a==0) b else commandPointer+3}}
            7 -> {data[data[commandPointer+3]] = getTwoOperands(commandPointer, data).let { (a,b)->if(a<b) 1 else 0 }; commandPointer+4}
            8 -> {data[data[commandPointer+3]] = getTwoOperands(commandPointer, data).let { (a,b)->if(a==b) 1 else 0 };  commandPointer+4}
            else -> throw Exception("WTF")

        }
    }
    @Test
    fun test_outputprocessing() {
        val tests = listOf(
//            arrayOf(1, 0, 0, 0, 99) to arrayOf(2, 0, 0, 0, 99),
//            arrayOf(2, 3, 0, 3, 99) to arrayOf(2, 3, 0, 6, 99),
//            arrayOf(2, 4, 4, 5, 99, 0) to arrayOf(2, 4, 4, 5, 99, 9801),
//            arrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99) to arrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99),
//            arrayOf(1002,4,3,4,33) to  arrayOf(1002,4,3,4,99),
            arrayOf(3,9,8,9,10,9,4,9,99,-1,8),
            arrayOf(3,9,7,9,10,9,4,9,99,-1,8),
            arrayOf(3,3,1108,-1,8,3,4,3,99)
        )
        tests.forEach {
            val result = processProgramm(it, 0)

        }

    }
    @Test
    fun test_processing() {
        val tests = mapOf(
//            arrayOf(1, 0, 0, 0, 99) to arrayOf(2, 0, 0, 0, 99),
//            arrayOf(2, 3, 0, 3, 99) to arrayOf(2, 3, 0, 6, 99),
//            arrayOf(2, 4, 4, 5, 99, 0) to arrayOf(2, 4, 4, 5, 99, 9801),
//            arrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99) to arrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99),
//            arrayOf(1002,4,3,4,33) to  arrayOf(1002,4,3,4,99),
            arrayOf(3,0,4,0,99) to arrayOf(1,0,4,0,99)
        )
        val input = arrayOf(1, 0, 0, 0, 99)
        tests.forEach {
            val result = processProgramm(it.key, 0)

            MatcherAssert.assertThat(result, Matchers.`is`(it.value))
        }
    }

}