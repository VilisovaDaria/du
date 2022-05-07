import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.*


class Tests {

    @Test
    fun getBytes() {
        assertEquals(mapOf("File" to 30678.toLong()), getBytesSize(File("File")))
        assertEquals(mapOf("File\\NewDirectory\\bigFile" to 28961.toLong()), getBytesSize(File("File/NewDirectory/bigFile")))
        assertEquals(mapOf("File\\txt2" to 1024.toLong()), getBytesSize(File("File/txt2")))
        assertEquals(mapOf("File\\NewDirectory\\txt3" to 34.toLong()), getBytesSize(File("File/NewDirectory/txt3")))
        assertEquals(mapOf("File\\1" to 0.toLong()), getBytesSize(File("File/1")))
    }

    @Test
    fun getHumanReadableFormat() {
        assertEquals("29,959 KB", getHumanReadableFormat(false, 30678))
        assertEquals("30,678 KB", getHumanReadableFormat(true, 30678))
        assertEquals("34 B", getHumanReadableFormat(false, 34))
        assertEquals("0 B", getHumanReadableFormat(false, 0))
    }

    @Test
    fun du() {
        assertEquals(mapOf("File\\NewDirectory\\txt3" to "34 B"), du(true, false, true, File("File/NewDirectory/txt3")))
        assertEquals(mapOf("File\\NewDirectory\\bigFile" to "28,282 KB"), du(true, false, false, File("File/NewDirectory/bigFile")))
        assertEquals(mapOf("Summary size" to "2,048 KB"), du(true, true, true, File("File/txt2"), File("File/txt2")))
        assertEquals(mapOf("Summary size" to "659 B"), du(true, true, true, File("File/txt1")))
        assertEquals(mapOf("File\\txt2" to "1,000", "File\\txt2" to "1,000"), du(false, false, false, File("File/txt2"), File("File/txt2")))
        assertEquals(mapOf("Summary size" to "30,678"), du(false, true, true, File("File")))
        assertEquals(mapOf("Summary size" to "2,000 KB"), du(true, true, false, File("File/txt2"), File("File/txt2")))
        assertEquals(
            mapOf("File\\txt2" to "1,024 KB", "File\\txt1" to "659 B"),
            du(true, false, true, File("File/txt2"), File("File/txt1"))
        )
    }

    @Test
    fun checking() {
        assertThrows(IllegalArgumentException::class.java) { getBytesSize(File("Fijvd")) }
        assertThrows(IllegalArgumentException::class.java) { getBytesSize(File("")) }
        assertThrows(IllegalArgumentException::class.java) {getHumanReadableFormat(true, -56)}
        assertThrows(IllegalArgumentException::class.java) { du(false, false, true, File("hjgt")) }
        assertThrows(IllegalArgumentException::class.java) { du(true, false, true, File("File/txt5")) }
        assertThrows(IllegalArgumentException::class.java) { du(false, false, true, File("")) }
    }
}