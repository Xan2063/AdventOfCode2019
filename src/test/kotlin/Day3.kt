import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class Day3 {

    @Test
    fun part1() {
        val commands = File("input3.txt").readLines().map { it.split(',') }
        val wires = commands.map { createWire(it.asSequence()) }

//    val pathes = wires.map{ createPath(it) }
//        val intersections = pathes.first().toSet().intersect(pathes.last().toSet())
//        val nearest = intersections.map { ((),z->abs(x)+abs(y) }.sorted().drop(1).min()
//        println(nearest)
    }
    @Test
    fun part2(){

        val commands = File("input3.txt").readLines().map { it.split(',') }
        val wires = commands.map { createWire(it.asSequence()) }

        val pathes = wires.map{ createPath(it) }
        val firstwire = pathes.first()
        val secondwire = pathes.last()

        val intersections = firstwire.map { (position,_)->position }.toSet().intersect(secondwire.map{(position,_)->position}.toSet())
        val firstpoints = firstwire.filter { (position, _)->intersections.contains(position) }.toList()
       val distances = firstpoints.map { (position, count)->secondwire.first{(secondPosition, secondCount)->position == secondPosition}.second+count }.sorted().drop(1).first()

        assertThat(distances, equalTo(20286))
    }



    fun createPath(steps: Sequence<Char>)= steps.scan(Pair(0 to 0, 0 ) , { (position, count), step ->
        val (x,y) = position
        when (step) {
           'R' ->  x+ 1 to y to count+1
           'L' ->  x - 1 to y to count+1
           'U' ->  x to y + 1 to count +1
           else ->  x to y - 1 to count +1
       }
    })

    fun <R, T> Sequence<T>.scan(seed: R, transform: (a: R, b: T) -> R): Sequence<R> = object : Sequence<R> {
        override fun iterator(): Iterator<R> = object : Iterator<R> {
            val it = this@scan.iterator()
            var last: R = seed
            var first = true

            override fun next(): R {
                if (first) first = false else last = transform(last, it.next())
                return last
            }

            override fun hasNext(): Boolean = it.hasNext()
        }
    }

    fun createWire(commands: Sequence<String>): Sequence<Char> {
        return commands.flatMap { expandCommand(it) }
    }

    fun expandCommand(command: String): Sequence<Char> {
        val direction = command.first()
        val number = command.drop(1).toInt()
        return direction.repeat(number)
    }

    fun Char.repeat(n: Int) = sequence {
        repeat(n) { yield(this@repeat) }
    }


    @Test
    fun expandCommandTest() {
        var expexted = sequenceOf('R', 'R', 'R')
        val expandCommands = expandCommand("R15")
        assertThat(expandCommands.joinToString(separator = ""), equalTo("RRRRRRRRRRRRRRR"))
    }


}