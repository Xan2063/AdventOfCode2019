import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import org.hamcrest.Matchers.`is` as Is
import org.hamcrest.Matchers.`is` as Is

class `2020Day2` {

    //    1,0,0,0,99 becomes 2,0,0,0,99 (1 + 1 = 2).
//    2,3,0,3,99 becomes 2,3,0,6,99 (3 * 2 = 6).
//    2,4,4,5,99,0 becomes 2,4,4,5,99,9801 (99 * 99 = 9801).
//    1,1,1,4,99,5,6,0,99 becomes 30,1,1,4,2,5,6,0,99

    data class Policy(val cardinality:IntRange, val char: Char,val password: String){

    }


    @Test
    fun test_processing() {
        val input = listOf("1-3 a: abcde","1-3 b: cdefg","2-9 c: ccccccccc")
        val result = input.map { extractData(it) }.filter { isValidPassword(it.cardinality, it.char, it.password) }.count()
        println(result)

    }

    private fun extractData(input: String) :Policy{
        val parts = input.split(" ")
        val rangeParts= parts[0].split("-").map { it.toInt() }
        println(parts[1])
        val char =parts[1].first()


        return Policy(IntRange(rangeParts[0], rangeParts[1]),char, parts[2])
    }


    fun isValidPassword(range: IntRange, char: Char, password:String):Boolean{
        return password.filter { it==char }.count().let {range.contains(it)}
    }

    fun isValidPassword2(range: IntRange, char: Char, password:String):Boolean{
        return (password[range.first-1]==char).xor(password[range.last-1]==char)
    }




    @Test
    fun test_processing2() {
        val input = listOf("1-3 a: abcde","1-3 b: cdefg","2-9 c: ccccccccc")
        val result = input.map { extractData(it) }.filter { isValidPassword2(it.cardinality, it.char, it.password) }.count()
        println(result)

    }



    @Test
    fun Part1() {
        val input = File("input2020_2.txt").readLines()
        val result = input.map { extractData(it) }.filter { isValidPassword(it.cardinality, it.char, it.password) }.count()
        println(result)
    }

    @Test
    fun Part2() {
        val input = File("input2020_2.txt").readLines()
        val result = input.map { extractData(it) }.filter { isValidPassword2(it.cardinality, it.char, it.password) }.count()
        println(result)
    }




}


