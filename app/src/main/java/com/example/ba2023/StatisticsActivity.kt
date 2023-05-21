package com.example.ba2023

import WritingStatusFileReader
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.ba2023.databinding.ActivityStatisticsBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class StatisticsActivity : ScreenActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var fileReader: WritingStatusFileReader
    private lateinit var entries: Array<WritingStatusFileReader.WritingSession>
    private var currentIndex = 0
    private lateinit var forwardButton: ImageView
    private lateinit var backButton: ImageView
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        forwardButton = binding.forwardButton
        backButton = binding.backButton
        text = binding.statisticsText

        forwardButton.setOnClickListener {
            showNextEntry()
        }
        backButton.setOnClickListener {
            showPreviousEntry()
        }
        fileReader = WritingStatusFileReader(this)
        entries = fileReader.readEntries()
        entries.forEach { entry ->
            println("timestamp: ${entry.timestamp} status: ${entry.status} ")
        }
        currentIndex = entries.size - 1
        showEntry()
    }
    private fun showPreviousEntry() {
        if (currentIndex > 0) {
            currentIndex -= 1
            showEntry()
        }
    }

    private fun showNextEntry() {
        if (currentIndex < entries.size - 1) {
            currentIndex += 1
            showEntry()
        }
    }

    private fun showEntry() {
        val entry = entries[currentIndex]
        val colorSegments = getColorSegments(entry)
        val timestamp = entry.timestamp
        text.text = parseDate(timestamp)
        hideButtons()
        binding.customProgressView.setProgress(100, colorSegments)

    }

    private fun hideButtons() {
        if (currentIndex == 0) {
            backButton.visibility = ImageView.INVISIBLE
        } else {
            backButton.visibility = ImageView.VISIBLE
        }
        if (currentIndex == entries.size - 1) {
            forwardButton.visibility = ImageView.INVISIBLE
        } else {
            forwardButton.visibility = ImageView.VISIBLE
        }
    }


    private fun getColorSegments(entry: WritingStatusFileReader.WritingSession): List<Pair<Int, Int>> {
        val green: Int = resources.getColor(R.color.progressBar_green, null)
        val red: Int = resources.getColor(R.color.progressBar_red, null)
        val black: Int = resources.getColor(R.color.progressBar_black, null)

        val status = entry.status ?: ""

        val regex = Regex("(W|T|D)(\\d+)")
        val matches = regex.findAll(status).toList()

        val total = matches.sumOf { matchResult ->
            matchResult.groupValues[2].toInt()
        }

        var currentPosition = 0
        return matches.map { matchResult ->
            val state = matchResult.groupValues[1]
            val count = matchResult.groupValues[2].toInt()
            val percentage = (count.toDouble() / total * 100).toInt()

            val color = when (state) {
                "W" -> green
                "T" -> black
                "D" -> red
                else -> Color.TRANSPARENT
            }

            val segment = color to percentage
            currentPosition += percentage
            segment
        }
    }
    private fun parseDate(date: Date): String {
        val pattern = "dd.MM.yyyy HH:mm"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(date)
    }
}