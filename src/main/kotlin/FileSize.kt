import java.io.*
import java.lang.String.format
import java.math.MathContext
import java.nio.file.Files
import java.nio.file.Paths

fun du(humanReadable: Boolean, totalSize: Boolean, foundation: Boolean, vararg inputName: File): List<String> {
    val listFileSizeByte = mutableListOf<Long>()
    val listFileName = mutableListOf<String>()
    var summarySize = 0L
    val result = mutableListOf<String>()

    if (humanReadable && !totalSize || !humanReadable) {
        for (i in inputName.indices) {
            unpacking(inputName[i]).forEach { listFileName.add(it.key) && listFileSizeByte.add(it.value) }
        }
    }

    if (humanReadable) {
        if (totalSize) {
            for (i in inputName.indices) {
                val listBytesSize = unpacking(inputName[i]).values.toList()
                summarySize += listBytesSize[0]
            }
            listFileSizeByte.add(summarySize)
            listFileName.add("Summary size")
        }
        for (i in listFileSizeByte.indices){
            result.addAll(listOf("${listFileName[i]} - ${humanReadable(foundation, listFileSizeByte[i])}"))
        }
    } else {

        //здесь вывод в КВ
        val baseFoundation = if (foundation) 1000 else 1024
        for (i in inputName.indices) {
            if (totalSize) {
                summarySize += listFileSizeByte[i]
            } else {
                summarySize = listFileSizeByte[i]
            }
            val n = summarySize.toDouble() / baseFoundation.toDouble()
            result.addAll(listOf("${listFileName[i]} - ${"%.3f".format(n)}"))
        }
        if (totalSize) return listOf("Summary size - ${result[result.lastIndex].split(" ")[2]}")
    }
    return result
}

fun humanReadable(foundation: Boolean, k: Long): String {
    if (k < 0) throw IllegalArgumentException("Incorrect format")
    var i = 0
    val type = arrayOf("B", "KB", "MB", "GB")
    var n = k.toDouble()
    val defaultFoundation = if (foundation) 1000.toDouble() else 1024.toDouble()
    if (n < defaultFoundation) return "$k B"
    while (n.toLong() / defaultFoundation.toLong() > 0 && i < 4) {
        n /= defaultFoundation
        i += 1
    }
    return "${"%.3f".format(n)} ${type[i]}"
}