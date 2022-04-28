import java.io.*
import kotlin.math.pow



fun unpacking(inputName: File): MutableMap<String, Int> {
    val result = mutableMapOf<String, Int>()

    return if (inputName.isDirectory) {
        val listOfSummaryFileSize = mutableListOf<File>()
        val sizeOfOneFile = inputName.walkTopDown().filter { it.isFile }.forEach { listOfSummaryFileSize.add(it) }

        for (i in 0..listOfSummaryFileSize.lastIndex){
            val d = FileInputStream(listOfSummaryFileSize[i]).readAllBytes().size
            result[listOfSummaryFileSize[i].toString()] = d
        }
        result
    }
    else {
        result[inputName.toString()] = FileInputStream(inputName).readAllBytes().size
        result
    }
}


private var baseFoundation = 1024


fun mainFunction(h: Boolean, c: Boolean, si: Boolean, vararg inputName: File): String {
    for (i in inputName.indices) {
        if (!inputName[i].exists()) throw IllegalArgumentException("The file does not exist")
    }

    fun summary(c: Boolean, h: Boolean, si: Boolean, vararg inputName: File): String {
        val listOfByte = mutableListOf<Int>()

        var result = 0

        for (i in inputName.indices) {
            listOfByte.addAll(unpacking(inputName[i]).values)
        }

        for (i in listOfByte.indices) {
            result += listOfByte[i]
        }

        println(result)
        return if (c && !h) {
            if (si) (result / 1000).toString()
            else (result / 1024).toString()
        } else {
            if (si) baseFondation = 1000
            when (digitNumber(result)) {
                in 1..3 -> "$result B"
                in 4..6 -> "${result / baseFondation} KB"
                in 7..9 -> "${result / baseFondation.toDouble().pow(2).toInt()} MB"
                else -> "${result / baseFondation.toDouble().pow(3).toInt()} GB"
            }
        }
    }

    if (!c && !h) throw IllegalArgumentException("Incorrect format")
    if (!c) {
        if (si) {
            baseFoundation = 1000
            for (i in inputName.indices) return getSize(inputName[i]).toString()
        } else for (i in inputName.indices) return getSize(inputName[i]).toString()
    }
    for (i in inputName.indices) return summary(c, h, si, inputName[i])

    throw IllegalArgumentException("Incorrect format")
}

private fun getSize(vararg inputName: File): String {
    val listFileSizeByte = mutableListOf<Int>()
    val listFileNames = mutableListOf<String>()

    for (i in inputName.indices) {
        listFileSizeByte.addAll(unpacking(inputName[i]).values)
        listFileNames.addAll(unpacking(inputName[i]).keys)
    }

    val result = mutableListOf<String>()

    for (i in listFileSizeByte.indices){
        when (digitNumber(listFileSizeByte[i])) {
            in 1..3 -> result.addAll(listOf("${listFileNames[i]}, ${listFileSizeByte[i]} B"))
            in 4..6 -> result.addAll(listOf("${listFileNames[i]} ${listFileSizeByte[i] / baseFondation} KB"))
            in 7..9 -> result.addAll(listOf("${listFileNames[i]} ${listFileSizeByte[i] / baseFondation.toDouble().pow(2).toInt()} MB"))
            else -> result.addAll(listOf("${listFileNames[i]} ${listFileSizeByte[i] / baseFondation.toDouble().pow(3).toInt()} GB"))
        }
    }
    return result.toString()
}

fun digitNumber(n: Int): Int {
    var countOfNumber = 0
    var l = n
    if (l == 0) return 1
    while (l > 0 || l < 0) {
        l /= 10
        countOfNumber += 1
    }
    return countOfNumber
}
