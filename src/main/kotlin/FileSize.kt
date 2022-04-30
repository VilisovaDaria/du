import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

fun du(humanReadable: Boolean, totalSize: Boolean, foundation: Boolean, vararg inputName: File): List<String> {
    var summarySize = 0.toLong()
    val result = mutableListOf<String>()

    for (i in inputName.indices) {
        if (!inputName[i].exists()) throw IllegalArgumentException("The file does not exist")
    }

    if (humanReadable) return getSize(totalSize, foundation, *inputName)

    //здесь вывод в КВ
    var baseFoundation = 1024
    if (foundation) baseFoundation = 1000

    for (i in inputName.indices) {

        val bytesSize = unpacking(inputName[i]).values.toLongArray()
        val fileNames = unpacking(inputName[i]).keys.toList()
        for (k in bytesSize.indices){
            if (totalSize) {
                summarySize += bytesSize[k]
            } else {
                summarySize = bytesSize[k]

            }

            val full = summarySize / baseFoundation
            val divisor = (summarySize - full * baseFoundation) % baseFoundation

            result.addAll(listOf("${fileNames[k]} - $full.$divisor"))
        }
    }

    return if (totalSize) listOf("Summary size - ${result[result.lastIndex].split(" ")[2]}")
    else result
}



//функция, если опция "-h" - true
fun getSize(totalSize: Boolean, foundation: Boolean, vararg inputName: File): List<String> {
    val result = mutableListOf<String>()
    val listFileSizeByte = mutableListOf<Long>()
    val listFileName = mutableListOf<String>()

    var summarySize = 0.toLong()

    //если опция "-с" (суммарный размер) false
    if (!totalSize) {
        for (i in inputName.indices) {
            listFileSizeByte.addAll(unpacking(inputName[i]).values)
            listFileName.addAll(unpacking(inputName[i]).keys)
        }
    }
    // если true
    if (totalSize) {
        for (i in inputName.indices) {
            val arrayBytesSize = unpacking(inputName[i]).values.toLongArray()
            for (k in arrayBytesSize.indices){
                summarySize += arrayBytesSize[k]
            }
        }
        listFileSizeByte.add(summarySize)
        listFileName.add("Summary size")
    }

    // применяем опцию "-h"
    for (i in listFileSizeByte.indices){
        result.addAll(listOf("${listFileName[i]} - ${humanReadable(foundation, listFileSizeByte[i])}"))
    }
    return result
}


fun humanReadable(foundation: Boolean, k: Long): String {
    if (k < 0) throw IllegalArgumentException("Incorrect format")

    var defaultFoundation = 1024
    var i = 0
    val type = arrayOf("B", "KB", "MB", "GB")
    var n = k

    if (foundation) defaultFoundation = 1000

    if (n < defaultFoundation) return "$n B"

    while (n / defaultFoundation > 0) {
        n /= defaultFoundation
        i += 1
    }

    val divisor = (k - n * defaultFoundation) % defaultFoundation
    return "$n.$divisor ${type[i]}"
}