import java.io.*

fun du(humanReadable: Boolean, totalSize: Boolean, foundation: Boolean, vararg inputFile: File): Map<String, String> {
    val result = mutableMapOf<String, String>()
    var summarySize = 0L
    val baseFoundation = if (foundation) 1000 else 1024

    if (totalSize) {
        inputFile.forEach { getBytesSize(it).forEach { summarySize += it.value } }
        if (humanReadable) result["Summary size"] = getHumanReadableFormat(foundation, summarySize)
        else {
            val n = summarySize.toDouble() / baseFoundation.toDouble()
            result["Summary size"] = "%.3f".format(n)
        }
    } else {
        for (i in inputFile.indices) {
            val oneFileByteSize = getBytesSize(inputFile[i]).values.first()
            if (humanReadable) result["${inputFile[i]}"] = getHumanReadableFormat(foundation, oneFileByteSize)
            else {
                val n = oneFileByteSize.toDouble() / baseFoundation.toDouble()
                result["${inputFile[i]}"] = "%.3f".format(n)
            }
        }
    }
    return result
}

fun getHumanReadableFormat(foundation: Boolean, k: Long): String {
    if (k < 0) throw IllegalArgumentException("Incorrect format")
    var i = 0
    val type = arrayOf("B", "KB", "MB", "GB")
    var n = k.toDouble()
    val defaultFoundation = if (foundation) 1000.toDouble() else 1024.toDouble()
    if (n < defaultFoundation) return "$k B"
    while (n.toLong() / defaultFoundation.toLong() > 0 && i < type.size) {
        n /= defaultFoundation
        i += 1
    }
    return "${"%.3f".format(n)} ${type[i]}"
}