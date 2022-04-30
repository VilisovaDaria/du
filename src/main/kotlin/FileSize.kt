import java.io.*
import java.nio.file.Files
import java.nio.file.Paths


fun du(humanReadable: Boolean, totalSize: Boolean, foundation: Boolean, vararg inputName: File): String {
    var summarySize = 0.toLong()

    for (i in inputName.indices) {
        if (!inputName[i].exists()) throw IllegalArgumentException("The file does not exist")
    }

    if (humanReadable) return getSize(totalSize, foundation, *inputName).toString()

    //здесь вывод в КВ и без остатка, так как дробная часть не играет большую роль, как например в GB
    var baseFoundation = 1024
    if (foundation) baseFoundation = 1000
    for (i in inputName.indices) {
        val arrayBytesSize = unpacking(inputName[i]).values.toLongArray()
        for (k in arrayBytesSize.indices){
            if (totalSize) {
                summarySize += arrayBytesSize[k]
            } else return (arrayBytesSize[k] / baseFoundation).toString()
        }
    }

    return (summarySize / baseFoundation).toString()
}


//функция, если опция "-h" - true
fun getSize(totalSize: Boolean, foundation: Boolean, vararg inputName: File): List<String> {
    val result = mutableListOf<String>()
    val listFileSizeByte = mutableListOf<Long>()
    val listFileName = mutableListOf<String>()

    var summarySize = 0.toLong()
    var baseFoundation = 1024

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
    if (foundation) baseFoundation = 1000
    for (i in listFileSizeByte.indices){
        result.addAll(listOf("${listFileName[i]} - ${humanReadable(listFileSizeByte[i])}"))
    }
    return result
}


fun humanReadable(k: Long): String {
    var i = 0
    val type = arrayOf("B", "KB", "MB", "GB")
    var n = k
    if (n < 1024) return "$n B"

    while (n / 1024 > 0) {
        n /= 1024
        i += 1
    }

    val divisor = (k - n * 1024) % 1024
    return "$n.$divisor ${type[i]}"
}