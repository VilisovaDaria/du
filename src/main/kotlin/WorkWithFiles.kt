import java.io.File
import kotlin.io.path.fileSize

fun getBytesSize(inputFile: File): MutableMap<String, Long> {
    if (!inputFile.exists()) throw IllegalArgumentException("File does not exist")

    val result = mutableMapOf<String, Long>()

    //находим размер каталога
    if (inputFile.isDirectory) {
        val listOfSummaryFileSize = mutableListOf<File>()
        val sizeOfOneFile = inputFile.walkTopDown().filter { it.isFile }.forEach { listOfSummaryFileSize.add(it) }
        var summary = 0L

        for (i in 0..listOfSummaryFileSize.lastIndex){
            val d = listOfSummaryFileSize[i].toPath().fileSize()
            summary += d
        }
        result[inputFile.toString()] = summary
    }
    else {
        result[inputFile.toString()] = inputFile.toPath().fileSize()
    }
    return result
}