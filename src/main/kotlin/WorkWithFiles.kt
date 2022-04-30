import java.io.File
import kotlin.io.path.fileSize

fun unpacking(inputName: File): MutableMap<String, Long> {
    val result = mutableMapOf<String, Long>()

    //находим размер каталога
    if (inputName.isDirectory) {
        val listOfSummaryFileSize = mutableListOf<File>()
        val sizeOfOneFile = inputName.walkTopDown().filter { it.isFile }.forEach { listOfSummaryFileSize.add(it) }
        var summary = 0.toLong()

        for (i in 0..listOfSummaryFileSize.lastIndex){
            val d = listOfSummaryFileSize[i].toPath().fileSize()
            summary += d
        }
        result[inputName.toString()] = summary
    }
    else {
        result[inputName.toString()] = inputName.toPath().fileSize()
    }
    return result
}
