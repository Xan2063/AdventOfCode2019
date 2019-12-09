import org.junit.jupiter.api.Test
import java.io.File
class TestDay1 {


    @Test
    fun DoIt() {
        val lines = File("input1.txt").readLines().map { test->test.toInt() }
        val result = lines.map { sequence1(it).takeWhile { it>0 }.sum() }.sum()
        println(result)
    }
    @Test
    fun DoIt2(){
        val lines = File("input1.txt").readLines().map { test->test.toInt() }
        val result = lines.map { calcNext(calcMass(it)).sum() }.sum()
        println(result)
    }

    fun calcNext(mass:Int):Sequence<Int>
    {
       return generateSequence (mass){
           calcMass(it).
               takeIf { mass->mass>0 } }
    }
    private fun sequence1(startMass:Int): Sequence<Int> = sequence {
        var fuelMass = startMass
        while (true) {
            val additionalFuel = calcMass(fuelMass)
            yield(additionalFuel)
            fuelMass = additionalFuel
        }
    }

    private fun calcMass(value: Int): Int = value / 3 - 2

    @Test
    fun TestIt() {
        val tests = mapOf<Int, Int>(12 to 2, 14 to 2, 1969 to 654, 100756 to 33583)
        tests.forEach {
            assert(calcMass(it.key) == it.value)
        }
    }
}
//12, divide by 3 and round down to get 4, then subtract 2 to get 2.
//For a mass of 14, dividing by 3 and rounding down still yields 4, so the fuel required is also 2.
//For a mass of 1969, the fuel required is 654.For a mass of 100756, the fuel required is 33583.