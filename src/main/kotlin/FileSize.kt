import java.io.*
import java.nio.file.Files
import java.nio.file.Paths


fun du(h: Boolean, c: Boolean, si: Boolean, vararg inputName: File): String {
    var summarySize = 0.toLong()

    for (i in inputName.indices) {
        if (!inputName[i].exists()) throw IllegalArgumentException("The file does not exist")
    }


    if (h) return getSize(c, si, *inputName).toString()
    if (c) {
        var baseFoundation = 1024
        if (si) baseFoundation = 1000
        for (i in inputName.indices) {
            val arrayBytesSize = unpacking(inputName[i]).values.toLongArray()
            for (k in arrayBytesSize.indices){
                summarySize += arrayBytesSize[k]
            }
        }
        return (summarySize / baseFoundation).toString()
    } else {
        var baseFoundation = 1024
        if (si) baseFoundation = 1000
        for (i in inputName.indices) {
            val arrayBytesSize = unpacking(inputName[i]).values.toLongArray()
            for (k in arrayBytesSize.indices){
                return (arrayBytesSize[k] / baseFoundation).toString()
            }
        }
    }
    return ""
}


fun unpacking(inputName: File): MutableMap<String, Long> {
    val result = mutableMapOf<String, Long>()

    if (inputName.isDirectory) {
        val listOfSummaryFileSize = mutableListOf<File>()
        val sizeOfOneFile = inputName.walkTopDown().filter { it.isFile }.forEach { listOfSummaryFileSize.add(it) }
        var summary = 0.toLong()

        for (i in 0..listOfSummaryFileSize.lastIndex){
            val d = Files.size(Paths.get(listOfSummaryFileSize[i].toString()))
            summary += d
        }
        result[inputName.toString()] = summary
    }
    else {
        result[inputName.toString()] = Files.size(Paths.get(inputName.toString()))
    }
    return result
}


//функция, если "-h" - true
fun getSize(totalSize: Boolean, foundation: Boolean, vararg inputName: File): List<String> {
    val result = mutableListOf<String>()
    val listFileSizeByte = mutableListOf<Long>()
    val listFileName = mutableListOf<String>()

    var summarySize = 0.toLong()
    var baseFoundation = 1024

    if (!totalSize) {
        for (i in inputName.indices) {
            listFileSizeByte.addAll(unpacking(inputName[i]).values)
            listFileName.addAll(unpacking(inputName[i]).keys)
        }
    }
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


    if (foundation) baseFoundation = 1000
    for (i in listFileSizeByte.indices){
        var k = 0
        val type = arrayOf("B", "KB", "MB", "GB")

        if (listFileSizeByte[i] < baseFoundation) result.addAll(listOf("${listFileName[i]} - ${listFileSizeByte[i]} B}"))

        else {
            while (listFileSizeByte[i] / baseFoundation != 0.toLong() && i < type.size - 1 ) {
                listFileSizeByte[i] /= baseFoundation.toLong()
                k ++
            }

            val divisor = (listFileSizeByte[i] - k * baseFoundation) % baseFoundation
            result.addAll(listOf("${listFileName[i]} - ${listFileSizeByte[i]}.$divisor ${type[k]}"))
        }
    }
    return result
}

