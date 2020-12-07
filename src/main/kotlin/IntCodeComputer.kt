
import java.lang.Exception

class IntCodeComputer {

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


        tailrec fun processProgramm(program: Programm): Array<Int> {
            with(program) {
                return when (data[commandPointer]) {
                    99 -> data
                    else -> {
                        executeStep(program)
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

        fun executeStep(program: Programm) {
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
    }


