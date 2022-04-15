import java.io.*

fun unpacking(inputName: File): Int {
    if (!inputName.exists()) throw IllegalArgumentException("The file does not exist")

    var summarySize = 0

    return if (inputName.isDirectory) {
        val listOfSummaryFileSize = mutableListOf<File>()
        val sizeOfOneFile = inputName.walkTopDown().filter { it.isFile }.forEach { listOfSummaryFileSize.add(it) }

        for (i in 0..listOfSummaryFileSize.lastIndex){
            val d = FileInputStream(listOfSummaryFileSize[i]).readAllBytes().size
            summarySize += d
        }
        summarySize
    } else {
        FileInputStream(inputName).readAllBytes().size
    }
}

fun getSize(inputName: File): String {
    val summarySizeInBytes = unpacking(inputName)

    return when (digitNumber(unpacking(inputName))) {
        in 1..3 -> "$summarySizeInBytes B"
        in 4..6 -> "${summarySizeInBytes / 1000} KB"
        in 7..9 -> "${summarySizeInBytes / (1000 * 1000)} MB"
        else -> "${summarySizeInBytes / (1000 * 1000 * 1000)} GB"
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
