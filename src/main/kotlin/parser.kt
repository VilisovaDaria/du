import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File
import java.nio.file.Files

fun main(args: Array<String>) {
    val parser = Parser()
    parser.parsing(args)
}

class Parser{
    @Option (name = "-h", usage = "Human readable format")
    private var sizeOfFile: Boolean = false

    @Option (name = "-c", usage = "Summary file size")
    private var totalSize: Boolean = false

    @Option (name = "--si", usage = "The base - 1 000")
    private var foundation: Boolean = false

    @Argument(metaVar = "InputName", usage = "Input file name", required = true)
    private var inputName = arrayOf<File>()

    fun parsing(args: Array<String>) {
        val parser = CmdLineParser(this)
        parser.parseArgument(args.toList())
        println(du(sizeOfFile, totalSize, foundation, *inputName))
    }
}

