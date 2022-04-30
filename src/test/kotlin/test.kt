import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.*
import java.nio.file.NoSuchFileException


class Tests {

    @Test
    fun unpacking() {
        assertEquals(mapOf("File" to 1717.toLong()), unpacking(File("File")))
        assertEquals(mapOf("File\\txt2" to 1024.toLong()), unpacking(File("File/txt2")))
        assertEquals(mapOf("File\\NewDirectory" to 34.toLong()), unpacking(File("File/NewDirectory")))
        assertEquals(mapOf("File\\1" to 0.toLong()), unpacking(File("File/1")))
        assertThrows(IllegalArgumentException::class.java) { unpacking(File("Fijvd")) }
        assertThrows(IllegalArgumentException::class.java) { unpacking(File("")) }
    }

    @Test
    fun getSize() {
        assertEquals(listOf("File\\NewDirectory - 34 B"), getSize(false, true, File("File/NewDirectory")))
        assertEquals(listOf("Summary size - 2.0 KB"), getSize(true, true, File("File/txt2"), File("File/txt2")))
        assertEquals(listOf("Summary size - 659 B"), getSize(true, true, File("File/txt1")))
        assertEquals(listOf("Summary size - 2.0 KB"), getSize(true, false, File("File/txt2"), File("File/txt2")))
        assertEquals(
            listOf("File\\txt2 - 1.0 KB", "File\\txt1 - 659 B"),
            getSize(false, true, File("File/txt2"), File("File/txt1"))
        )
        assertThrows(IllegalArgumentException::class.java) { getSize(false, true, File("File/txt5")) }
        assertThrows(IllegalArgumentException::class.java) { getSize(false, true, File("")) }
    }

    @Test
    fun du() {
        assertEquals("2.0", du(false, true, false, File("File/txt2"), File("File/txt2")))
        assertEquals("1.717", du(false, true, true, File("File")))
        assertThrows(IllegalArgumentException::class.java) { du(false, false, true, File("hjgt")) }
    }

}