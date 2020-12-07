import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import java.io.File
import java.lang.Exception

import org.hamcrest.Matchers.`is` as Is
class Day7 {

    class Programm(val data: Array<Int>, val input: Array<Int>) {
        val output = mutableListOf<Int>()
        var commandPointer: Int = 0
        val inputSequence = sequence {
            for (value in input) {
                yield(value)
            }
        }.iterator()

        fun getNextInput(): Int = inputSequence.next()

    }

    fun  permute(list:List <Int>):List<List<Int>>{
        if(list.size==1) return listOf(list)
        val perms=mutableListOf<List <Int>>()
        val sub=list[0]
        for(perm in permute(list.drop(1)))
            for (i in 0..perm.size){
                val newPerm=perm.toMutableList()
                newPerm.add(i,sub)
                perms.add(newPerm)
            }
        return perms
    }
        @Test
        fun Part1() {
            val input = File("input7.txt").readLines()[0].split(',')
            val ar = input.map { it.toInt() }.toTypedArray()
            val permutations = permute(listOf(0,1,2,3,4))

            val result = permutations.map {
                (0..4).fold(0){
                    acc, i ->
                    val programmInput = arrayOf(it[i], acc)
                    val program = Programm(ar, programmInput)
                    processProgramm(program)
                    program.output[0]
                }


            }.max()


            println(result)

            //replace position 1 with the value 12 and replace position 2 with the value 2.
        }
    @Test
    fun Part2() {
        val input = File("input7.txt").readLines()[0].split(',')
        val ar = input.map { it.toInt() }.toTypedArray()
        val permutations = permute(listOf(5,6,7,8,9))

        val result = permutations.map {

            val accs = (0..4).map {index->

                val programmInput = arrayOf(it[index])
                Programm(ar, programmInput)
            }


            (0..4).fold(arrayOf(0)){
                    acc, i ->
                val programmInput = arrayOf(it[i]).plus(acc)
                val program = Programm(ar, programmInput)
                processProgramm(program)
                program.output.toTypedArray()
            }


        }.map { it.last() }.max()


        println(result)

        //replace position 1 with the value 12 and replace position 2 with the value 2.
    }
        tailrec fun processProgramm(program: Programm): Array<Int> {
            with(program) {
                return when (data[commandPointer]) {
                    99 -> data
                    else -> {
                        val nextCommand = processInput(program)
                        processProgramm(program)
                    }
                }
            }

        }

        enum class OperandMode {
            Position,
            Immediate

        }

        fun getOperandMode(value: Char) = if (value == '0') OperandMode.Position else OperandMode.Immediate


        fun getOperand(commandPointer: Int, data: Array<Int>, offset: Int = 1): Int {
            val command = data[commandPointer].toString()

            return when (operandMode(command, offset)) {
                OperandMode.Position -> data[data[commandPointer + offset]]
                OperandMode.Immediate -> data[commandPointer + offset]
            }
        }


        private fun operandMode(command: String, offset: Int) =
            getOperandMode(command.reversed().getOrElse(offset + 1, { '0' }))

        fun getCommand(command: Int) = command.toString().takeLast(2).toInt()

        fun getTwoOperands(commandPointer: Int, data: Array<Int>) =
            getOperand(commandPointer, data, 1) to getOperand(commandPointer, data, 2)

        fun processInput(program: Programm) {
            with(program) {
                val command = getCommand(data[commandPointer])

                val nextInstruction = when (command) {
                    1 -> {
                        data[data[commandPointer + 3]] =
                            getTwoOperands(commandPointer, data).let { (a, b) -> a + b }; commandPointer + 4
                    }
                    2 -> {
                        data[data[commandPointer + 3]] =
                            getTwoOperands(commandPointer, data).let { (a, b) -> a * b }; commandPointer + 4
                    }
                    3 -> {
                        val input = getNextInput()
                        data[data[commandPointer + 1]] = input;commandPointer + 2
                    }
                    4 -> {
                        val operand = getOperand(commandPointer, data)
                        println("Out $operand");
                        output.add(operand)
                        commandPointer + 2
                    }
                    5 -> {
                        getTwoOperands(commandPointer, data).let { (a, b) -> if (a != 0) b else commandPointer + 3 }
                    }
                    6 -> {
                        getTwoOperands(commandPointer, data).let { (a, b) -> if (a == 0) b else commandPointer + 3 }
                    }
                    7 -> {
                        data[data[commandPointer + 3]] =
                            getTwoOperands(
                                commandPointer,
                                data
                            ).let { (a, b) -> if (a < b) 1 else 0 }; commandPointer + 4
                    }
                    8 -> {
                        data[data[commandPointer + 3]] =
                            getTwoOperands(
                                commandPointer,
                                data
                            ).let { (a, b) -> if (a == b) 1 else 0 }; commandPointer + 4
                    }
                    else -> throw Exception("WTF")
                }
                commandPointer = nextInstruction

            }
        }

        @Test
        fun test_outputprocessing() {
            val tests = mapOf(
//            arrayOf(1, 0, 0, 0, 99) to arrayOf(2, 0, 0, 0, 99),
//            arrayOf(2, 3, 0, 3, 99) to arrayOf(2, 3, 0, 6, 99),
//            arrayOf(2, 4, 4, 5, 99, 0) to arrayOf(2, 4, 4, 5, 99, 9801),
//            arrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99) to arrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99),
//            arrayOf(1002,4,3,4,33) to  arrayOf(1002,4,3,4,99),
                arrayOf(3, 9,3,9, 8, 9, 10, 9, 4, 9, 99, -1, 8) to arrayOf(1),
                arrayOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)to arrayOf(0),
                arrayOf(3, 3, 1108, -1, 8, 3, 4, 3, 99) to arrayOf(1)
            )
            tests.forEach {

                val program = Programm(it.key, arrayOf(8,7))
                val result = processProgramm(program)
                assertThat(program.output[0], Is(it.value[0]))

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
                arrayOf(3, 0, 4, 0, 99) to arrayOf(1, 0, 4, 0, 99)
            )
            tests.forEach {
                val program = Programm(it.key, arrayOf(1))
                val result = processProgramm(program)

                MatcherAssert.assertThat(result, Matchers.`is`(it.value))
            }
        }
    }


