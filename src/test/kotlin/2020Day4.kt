import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is` as Is
import org.junit.jupiter.api.Test
import java.io.File


class `2020Day4` {

    private val testInput = listOf(
        "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
        "byr:1937 iyr:2017 cid:147 hgt:183cm",
        "",
        "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
        "hcl:#cfa07d byr:1929",
        "",
        "hcl:#ae17e1 iyr:2013",
        "eyr:2024",
        "ecl:brn pid:760753108 byr:1931",
        "hgt:179cm",
        "",
        "hcl:#cfa07d eyr:2025 pid:166559648",
        "iyr:2011 ecl:brn hgt:59in",
    )

    private val testinput2 = listOf(
        "eyr:1972 cid:100",
        "hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926",
        "",
        "iyr:2019",
        "hcl:#602927 eyr:1967 hgt:170cm",
        "ecl:grn pid:012533040 byr:1946",
        "",
        "hcl:dab227 iyr:2012",
        "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277",
        "",
        "hgt:59cm ecl:zzz",
        "eyr:2038 hcl:74454a iyr:2023",
        "pid:3556412378 byr:2007"
    )

    private val inputValid = listOf(
        "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980",
        "hcl:#623a2f",
        "",
        "eyr:2029 ecl:blu cid:129 byr:1989",
        "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm",
        "",
        "hcl:#888785",
        "hgt:164cm byr:2001 iyr:2015 cid:88",
        "pid:545766238 ecl:hzl",
        "eyr:2022",
        "",
        "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719",
    )

    private val elements = listOf(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid",
//        "cid",
    )


    private val day = "4"
    private val filename = "input2020_$day.txt"

    @Test
    fun test_part1() {

        val result = doIt(testInput)
        println(result)

    }

    private fun doIt(input: List<String>): Int {
        val passports = input.joinToString("|").split("||").map { it.replace('|', ' ') }
        val passportData = passports.map { convertPassPort(it) }.map { isValid(it) }.filter { it }.count()
        return passportData
    }

    fun convertPassPort(data: String): Map<String, String> {
        return data.split(' ').map { kvp -> kvp.split(':').let { it[0] to it[1] } }.toMap()
    }

    fun isValid(passport: Map<String, String>): Boolean {
        return elements.all { it in passport.keys }
    }

    @Test
    fun Part1() {
        val input = File(filename).readLines()
        val result = doIt(input)
        println(result)
    }


    @Test
    fun test_part2() {
        val result = doIt2(testinput2)
        println(result)

    }

    @Test
    fun test_part2valid() {
        val result = doIt2(inputValid)
        println(result)

    }



    private fun doIt2(input: List<String>): Any {
        val passports = input.joinToString("|").split("||").map { it.replace('|', ' ') }
        val passportData = passports.map { convertPassPort(it) }.map { isValid2(it) }.filter { it }.count()
        return passportData
    }

    fun isValid2(passport: Map<String, String>): Boolean {
        return elements.all { it in passport.keys } && checkPassport(passport)

    }

    @Test
    fun checkhcl() {
        val res = Regex("#[a-f|0-9]{6}").matches("#123abc")
        val res3 = Regex("#[a-f|0-9]{6}").matches("#123abz")
        val res2 = Regex("#[a-f|0-9]{6}").matches("123abc")
    }

    @Test
    fun checkpid() {
        assertThat(Regex("\\d{9}").matches("000000001"), Is(true))
        assertThat(Regex("\\d{9}").matches("0123456789"), Is(false))

    }

    @Test
    fun checkhgt() {
        assertThat(checkHeight("60in"), Is(true))
        assertThat(checkHeight("190cm"), Is(true))
        assertThat(checkHeight("190in"), Is(false))
        assertThat(checkHeight("190"), Is(false))


    }

    fun checkHeight(value: String): Boolean {

        return when {
            value.contains("in") -> value.removeSuffix("in").toInt() in 59..76
            value.contains("cm") -> value.removeSuffix("cm").toInt() in 150..193
            else -> false
        }
    }

    private fun checkPassport(elements: Map<String, String>): Boolean {
        return elements.all {
            when (it.key) {
                "byr" -> it.value.toInt() in 1920..2002
                "iyr" -> it.value.toInt() in 2010..2020
                "eyr" -> it.value.toInt() in 2020..2030
                "hcl" -> Regex("#[a-f|0-9]{6}").matches(it.value)
                "ecl" -> it.value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                "pid" -> Regex("\\d{9}").matches(it.value)
                "hgt" -> checkHeight(it.value)
                "cid"->true
                else -> {
                    println(it.key)
                    false
                }

            }
        }
    }

    @Test
    fun Part2() {
        val input = File(filename).readLines()
        val result = doIt2(input)
        println(result)
    }


}


