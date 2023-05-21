import android.content.Context
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WritingStatusFileReader(private val context: Context) {

    private val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())

    data class WritingSession(val timestamp: Date, val status: String)

    fun readEntries(): Array<WritingSession> {
        val file = getFile()
        val entries = mutableListOf<WritingSession>()

        try {
            file.bufferedReader().useLines { lines ->
                var timestamp: Date? = null
                var status: String? = null
                lines.forEach { line ->
                    if (line.startsWith("timestamp:")) {
                        // If there is an existing timestamp and status, add them as an entry
                        if (timestamp != null && status != null) {
                            entries.add(WritingSession(timestamp!!, status!!))
                        }
                        timestamp = parseTimestamp(line.substringAfter("timestamp:"))
                        status = null // Reset the status for the new timestamp
                    } else {
                        // Concatenate the status lines
                        if (status == null) {
                            status = line
                        } else {
                            status += line
                        }
                    }
                }

                // Add the last entry after all lines have been processed
                if (timestamp != null && status != null) {
                    entries.add(WritingSession(timestamp!!, status!!))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return entries.toTypedArray()
    }



    private fun parseTimestamp(timestampString: String): Date {
        val sourcePattern = "yyyyMMddHHmmss"
        val sourceFormat = SimpleDateFormat(sourcePattern, Locale.getDefault())
        return try {
            sourceFormat.parse(timestampString) as Date
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }
    }

    private fun getFile(): File {
        val directory = context.filesDir
        return File(directory, "writing_status.txt")
    }
}
