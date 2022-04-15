import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.Option
import org.kohsuke.args4j.CmdLineParser
import java.io.File

fun main(args: Array<String>) {
    Parser().parsing(args)
}

class Parser{
    @Option (name = "-h", usage = "Human readable format")
    private var sizeOfFile = String

    @Option (name = "-c", usage = "Summary file size")
    private var totalSize = String

    @Option (name = "--si", usage = "The base - 1 000")
    private var foundation = String

    @Argument(metaVar = "InputName", usage = "Input file name", required = true)
    private lateinit var inputName: File

    fun parsing(args: Array<String>) {
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(args.toList())
            if (sizeOfFile.toString() == "") {
                unpacking(inputName) / 1000
            }
            getSize(inputName)
            }
        catch(e: Exception) {
            throw IllegalArgumentException("File does not exist")
        }
    }
}
