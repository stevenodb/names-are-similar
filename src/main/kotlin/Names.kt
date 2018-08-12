import java.io.BufferedReader
import java.util.stream.Collectors
import kotlin.system.exitProcess

fun distanceHalfForStringLargerThanThree(): (String) -> Int =
        {
            if (it.length >= 4) {
                it.length / 2
            } else {
                it.length / 3
            }
        }

fun main(args: Array<String>) {
    val one = inputName("First name?")
    val other = inputName("Second name?")

    val allNames = namesFromFile(resourceContentByName("voornamen_jongens_1995_2016.csv"))
    if (!allNames.contains(one) or !allNames.contains(other)) {
        println("At least one name is not in the list of all names between 1995 and 2016.")
        exitProcess(1)
    }

    if (namesAreSimilar(one, other, allNames, distanceHalfForStringLargerThanThree())) {
        println("Oh no! Given names are similar. (distance: ${levenshteinDistance(one, other)}).")
    } else {
        println("Given names should not be similar.")
    }
}

fun namesAreSimilar(one: String, other: String, allNames: Set<String>,
                    distance: (String) -> Int = distanceHalfForStringLargerThanThree()): Boolean {

    val similarOne = similarNames(allNames, one.toLowerCase(), distance)
    val similarTwo = similarNames(allNames, other.toLowerCase(), distance)

    val intersection = similarOne intersect similarTwo

    return intersection.isNotEmpty()
}

fun similarNames(allNames: Set<String>, name: String, distance: (String) -> Int): Set<Pair<String, Int>> {
    return allNames.stream()
            .map { Pair(it, levenshteinDistance(name, it)) }
            .filter { it.second <= distance(name) }
            .collect(Collectors.toSet())
}

fun inputNameTwice(message: String): String {
    var first = "first"
    var second = "second"
    while(first != second) {
        first = inputName(message)
        second = inputName("repeat")
    }
    return first
}

fun inputName(message: String): String {
    print("$message ")

    val name = (System.console()?.readPassword(": ")?.joinToString("")
            ?: readLine())

    if (name.isNullOrBlank()) {
        println("No name given.")
        exitProcess(1)
    } else {
        return name!!
    }
}

fun namesFromFile(bufferedReader: BufferedReader): Set<String> {
    val names = bufferedReader.lines()
            .filter { it.isNotEmpty() }
            .map { it.split(';')[1].toLowerCase() }
            .filter { it.isNotEmpty() }
            .distinct()
            .collect(Collectors.toSet())
    return names
}

fun resourceContentByName(fileName: String): BufferedReader {
    return object {}.javaClass.classLoader.getResource(fileName).openStream().bufferedReader()
}

fun levenshteinDistance(a: String, b: String): Int {
    val m = a.length
    val n = b.length
    val d = Array(m + 1, { IntArray(n + 1) })

    for (i in 1..m) {
        d[i][0] = i
    }

    for (j in 1..n) {
        d[0][j] = j
    }

    for (j in 1..n) {
        for (i in 1..m) {
            val subCost = if (a[i - 1] == b[j - 1]) 0 else 1
            d[i][j] = minOf(
                    d[i - 1][j] + 1,
                    d[i][j - 1] + 1,
                    d[i - 1][j - 1] + subCost
            )
        }
    }

    return d[m][n]
}