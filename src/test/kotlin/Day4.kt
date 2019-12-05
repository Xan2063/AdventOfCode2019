import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class Day4 {

    @Test
    fun part1() {
        var reslult = (165432..707912).map { it.toSequence() }.filter { isIncreasing(it)}.filter{hasDoublesExtended3(it)}.count()
        println(reslult)
    }

    @Test
    fun smalltests(){
//        assertTrue(hasDoubles(111111.toSequence()))
//        assertTrue(isIncreasing(111111.toSequence()))
//
//        assertTrue(hasDoubles(223450.toSequence()))
//        assertFalse(isIncreasing(223450.toSequence()))
//        assertFalse(hasDoubles(123789.toSequence()))
//        assertTrue(isIncreasing(123789.toSequence()))
        assertTrue(hasDoublesExtended3(112233.toSequence()))
//        assertTrue(isIncreasing(112233.toSequence()))
        assertTrue(hasDoublesExtended3(111122.toSequence()))
//        assertTrue(hasDoubles(111122.toSequence()))
        assertFalse(hasDoublesExtended3(112444.toSequence()))
        assertFalse(hasDoublesExtended3(123444.toSequence()))
//        assertTrue(isIncreasing(123444.toSequence()))
//        223450 does not meet these criteria (decreasing pair of digits 50).
//            123789 does not meet these criteria (no double).
    }

    fun Int.toSequence(): Sequence<Int> {
        var num = this
        val result = mutableListOf<Int>()
        var count = 0
        while (num > 0) {
            val digit: Int = num % 10
            result.add(digit)
            num /= 10
        }
        return result.asSequence()
    }

    @Test
    fun digitExtractor() {
        val test = 123456.toSequence()
    }

    fun isIncreasing(digits: Sequence<Int>)= digits.zipWithNext().all { (a,b)->a>=b }
    fun hasDoubles(digits: Sequence<Int>) = digits.zipWithNext().any { (a, b) -> a == b }
    fun hasDoublesExtended2(digits: Sequence<Int>)= digits.groupBy { it }.any{it.value.count()==2}
    fun hasDoublesExtended3(digits: Sequence<Int>) :Boolean{
        val triples = digits.windowed(3).toList()
        return triples.any { (a, b, c) ->
            return when{
                (a==b) &&(a!=c)->true
                (b==c)&&(a!=b)->true
                else -> false
            }
        }
    }fun hasDoublesExtended(digits: Sequence<Int>) :Boolean{
        val triples = digits.windowed(4).toList()
        return triples.any { (a, b, c,d) ->
            return when{
                ((a==b) &&(a!=c))->true
                (b==c)&&(a!=b) &&(c!=d)->true
                ((c==d)&&(c!=b))->true
                else -> false
            }
        }
    }


}
