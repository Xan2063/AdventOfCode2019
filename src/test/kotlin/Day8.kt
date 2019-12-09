import org.junit.jupiter.api.Test
import java.io.File

class Day8 {

    @Test
    fun part1(){
        val input = File("input8.txt").readLines()[0]
        val size =25*6
        val minlayer = input.chunked(size).minBy { layer->layer.filter { c->c=='0' } }
        val result = minlayer!!.groupBy { it }
        val value = result['1']!!.size * result['2']!!.size
        println(value)
    }
    @Test
    fun part2(){
        val input = File("input8.txt").readLines()[0]
 //       val input = "0222112222120000"
        val size =25*6
        val layers = input.chunked(size)
        val result = layers.reversed().fold(Array(size){'2'}){acc, s ->  acc.mapIndexed {index, i -> when(s[index]){
            '0' ->'0'
            '1'->'1'
            else ->acc[index]
        }  }.toTypedArray()}

        val lines = result.toList().chunked(25).map { it.map { if(it == '1')'#' else ' ' } }.forEach{
            println(it)
        }

    }

}