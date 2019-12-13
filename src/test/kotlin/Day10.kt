import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.atan2


class Day10 {

    @Test
    fun part1(){
        val asteroids = File("input10.txt").readLines()
            .mapIndexed { y, s -> s.mapIndexed {x, c -> if(c=='#') (x to y) else null  }}.flatten().filterNotNull()
        val distances =
            asteroids.map { getAllDistances(it, asteroids).
                groupBy { distance ->reduceVector(distance) }.count() }
        val asteroid = asteroids[distances.indexOf(distances.max())]
        println(asteroid)
//        asteroids.groupBy { it.second }.forEach()
        println(distances.max() )

    } @Test
    fun part2(){
        val asteroids = File("input10.txt").readLines()
            .mapIndexed { y, s -> s.mapIndexed {x, c -> if(c=='#') (x to y) else null  }}.flatten().filterNotNull()
        val distances =
            asteroids.map { getAllDistances(it, asteroids).
            groupBy { distance ->reduceVector(distance) }}.maxBy { it.count() }!!

        val sortedByAngle = distances.toList().
            sortedByDescending { (k,_)->sortbyAngle(k) }
        val asteroidslist = sortedByAngle.drop(198).take(5).map{it.second.first()}
        asteroidslist.forEach{asteroid->
            println(asteroid)
            val aster= asteroid.first+20 to asteroid.second +18
            val result = aster.first*100 + aster.second
            println(result)
        }

//        asteroids.groupBy { it.second }.forEach()

    }

    private fun sortbyAngle(k: Pair<Int, Int>):Int {
        var angle = vectorAngle(k.first, k.second)
        angle -= 90
        if (angle<=0) angle += 360
        return angle
    }

    fun vectorAngle(x: Int, y: Int): Int {
        if (x == 0) // special cases
            return if (y > 0) 90 else if (y == 0) 0 else 270 else if (y == 0) // special cases
            return if (x >= 0) 0 else 180
        var ret: Int = (Math.toDegrees(atan(y.toDouble() / x))).toInt()
        if (x < 0 && y < 0) // quadrant Ⅲ
            ret = 180 + ret else if (x < 0) // quadrant Ⅱ
            ret = 180 + ret // it actually substracts
        else if (y < 0) // quadrant Ⅳ
            ret = 270 + (90 + ret) // it actually substracts
        return ret
    }


    fun getAllDistances(asteroid : Pair<Int, Int>, asteroids: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        val distances = asteroids.map { otherasteroid -> asteroid.getVector(otherasteroid)}.sortedBy { (a,b)-> abs(a)+abs(b) }
        if(distances.first()!= 0 to 0) {
            return distances.drop(1)
        }
        return distances.drop(1)
    }

    fun Pair<Int, Int>.getVector(other: Pair<Int, Int>) = (other.first - first) to (other.second - second)

    fun reduceVector(vector :Pair<Int, Int>):Pair<Int,Int>{
        val (a,b) = vector
        val gcd = euclid(a,b)
        if(gcd == 0)
        {
            return a to b
        }
        return a/gcd to b/gcd
    }

    tailrec fun euclid(a:Int, b:Int):Int  = if( b == 0) abs(a) else euclid(b, a % b)

    @Test
    fun reduceVectorTest(){
        mapOf((2 to 4) to (1 to 2),
            (4 to 2) to (2 to 1),
            (2 to 0) to (1 to 0),
            (0 to 2) to (0 to 1)).forEach{
            val (a,b) = it.key
            assertThat(reduceVector(it.key), Is.`is`(it.value))
        }
    }
    @Test
    fun reduceTest(){
        mapOf((2 to 4) to 2,
            (4 to 2) to 2,
            (2 to 0) to 2,
            (0 to 2) to 2).forEach{
            val (a,b) = it.key
            assertThat(euclid(a,b), Is.`is`(it.value))
        }
    }
}