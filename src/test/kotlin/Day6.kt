import org.junit.jupiter.api.Test
import java.io.File

class Day6 {
    @Test
    fun part1(){
        val relations = File("input6.txt").readLines().map { it.substringAfter(')') to it.substringBefore(')') }.toMap()
        val objects = relations.flatMap { it.toPair().toList() }.distinct()
        val result = objects.sumBy { getOrbits(relations, it) }
        println(result)

    }
    @Test
    fun part2(){
        val relations = File("input6.txt").readLines().map { it.substringAfter(')') to it.substringBefore(')') }.toMap()
        val objects = relations.flatMap { it.toPair().toList() }.distinct()
        var path = path(relations, "YOU")
        var pathSanta = path(relations, "SAN")
        val elements = (path+pathSanta).groupBy { it }.filter { it.value.size ==1 }.flatMap { it.value }
        println(elements.count())

    }

    fun getOrbits(relations : Map<String, String>, star:String) :Int{
        return relations[star]?.let{getOrbits(relations, it) +1} ?: 0
    }

    fun path(relations: Map<String, String>, star: String):List<String>{
        return relations[star]?.let { listOf(it) + path(relations, it) }?: emptyList()
    }


}