import java.io.*

fun unpacking(inputName: File): Int {
    var summarySize = 0

    return if (inputName.isDirectory) {
        val catalogOfFiles = inputName.listFiles()
        for (i in catalogOfFiles.indices) {
            val sizeOfOneFile = FileInputStream(catalogOfFiles[i]).readAllBytes().size
            summarySize += sizeOfOneFile
        }
        summarySize
    } else {
        FileInputStream(inputName).readAllBytes().size
    }
}

fun getSize(inputName: File): String {
    val digitOfSize = digitNumber(unpacking(inputName))

    return when {
        digitOfSize < 4 -> "${unpacking(inputName)} B"
        digitOfSize in 4..6 -> "${unpacking(inputName) / 1000} KB"
        digitOfSize in 7..9 -> "${unpacking(inputName) / (1000 * 1000)} KB"
        digitOfSize in 10..12 -> "${unpacking(inputName) / (1000 * 1000 * 1000)} GB"
        else -> String()
    }
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
