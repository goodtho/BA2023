package com.example.ba2023.model

import android.content.Context
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class WritingStatusFileWriter(private val context: Context) {

    private val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
    private val fileWriter: BufferedWriter

    init {
        val file = getFile()
        fileWriter = BufferedWriter(FileWriter(file, true))
    }

    fun writeTimestamp(timestamp: Long) {
        val formattedTimestamp = dateFormat.format(Date(timestamp))
        val entry = "timestamp:$formattedTimestamp\n"

        fileWriter.write(entry)
        fileWriter.flush()
    }

    fun writeStatus(state: WritingStatusManager.ScreenState, durationInSeconds: Int) {
        val entry = "${state.name[0]}$durationInSeconds"

        fileWriter.append(entry)
        fileWriter.newLine()
        fileWriter.flush()
    }

    private fun getBufferedWriter(): BufferedWriter {
        val file = getFile()
        return BufferedWriter(FileWriter(file, true))
    }

    private fun getFile(): File {
        val directory = context.filesDir
        return File(directory, "writing_status.txt")
    }
}