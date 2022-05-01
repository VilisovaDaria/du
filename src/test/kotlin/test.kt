import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.*


class Tests {

    @Test
    fun unpacking() {
        assertEquals(mapOf("File" to 30678.toLong()), unpacking(File("File")))
        assertEquals(mapOf("File\\NewDirectory\\bigFile" to 28961.toLong()), unpacking(File("File/NewDirectory/bigFile")))
        assertEquals(mapOf("File\\txt2" to 1024.toLong()), unpacking(File("File/txt2")))
        assertEquals(mapOf("File\\NewDirectory\\txt3" to 34.toLong()), unpacking(File("File/NewDirectory/txt3")))
        assertEquals(mapOf("File\\1" to 0.toLong()), unpacking(File("File/1")))
        assertThrows(IllegalArgumentException::class.java) { unpacking(File("Fijvd")) }
        assertThrows(IllegalArgumentException::class.java) { unpacking(File("")) }
    }

    @Test
    fun humanReadable() {
        assertEquals("29,959 KB", humanReadable(false, 30678))
        assertEquals("30,678 KB", humanReadable(true, 30678))
        assertEquals("34 B", humanReadable(false, 34))
        assertEquals("0 B", humanReadable(false, 0))
        assertThrows(IllegalArgumentException::class.java) {humanReadable(true, -56)}
    }

    @Test
    fun du() {
        assertEquals(listOf("File\\NewDirectory\\txt3 - 34 B"), du(true, false, true, File("File/NewDirectory/txt3")))
        assertEquals(listOf("File\\NewDirectory\\bigFile - 28,282 KB"), du(true, false, false, File("File/NewDirectory/bigFile")))
        assertEquals(listOf("Summary size - 2,048 KB"), du(true, true, true, File("File/txt2"), File("File/txt2")))
        assertEquals(listOf("Summary size - 659 B"), du(true, true, true, File("File/txt1")))
        assertEquals(listOf("File\\txt2 - 1,000", "File\\txt2 - 1,000"), du(false, false, false, File("File/txt2"), File("File/txt2")))
        assertEquals(listOf("Summary size - 30,678"), du(false, true, true, File("File")))
        assertThrows(IllegalArgumentException::class.java) { du(false, false, true, File("hjgt")) }
        assertEquals(listOf("Summary size - 2,000 KB"), du(true, true, false, File("File/txt2"), File("File/txt2")))
        assertEquals(
            listOf("File\\txt2 - 1,024 KB", "File\\txt1 - 659 B"),
            du(true, false, true, File("File/txt2"), File("File/txt1"))
        )
        assertThrows(IllegalArgumentException::class.java) { du(true, false, true, File("File/txt5")) }
        assertThrows(IllegalArgumentException::class.java) { du(false, false, true, File("")) }
    }

}