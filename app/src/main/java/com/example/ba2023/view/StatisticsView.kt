package com.example.ba2023.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


class StatisticsView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    // Define variables to store the progress and color segments
    private var progress: Int = 0
    private val colorSegments: MutableList<Pair<Int, Int>> = mutableListOf()

    // Override onDraw to draw the colored segments based on progress
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        // Draw the white border
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint.color = Color.WHITE
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 1f

        val borderRect = RectF(0f, 0f, width, height)
        canvas.drawRect(borderRect, borderPaint)

        // Calculate the inner rectangle for the colored segments
        val innerRect = RectF(1f, 1f, width - 1f, height - 1f)

        // Draw the colored segments
        var left = 1f
        for (segment in colorSegments) {
            val segmentColor = segment.first
            val segmentProgress = segment.second

            val segmentRight = left + (width - 2f) * segmentProgress / 100
            val segmentRect = RectF(left, innerRect.top, segmentRight, innerRect.bottom)

            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = segmentColor
            canvas.drawRect(segmentRect, paint)

            left += (width - 2f) * segmentProgress / 100
        }
    }


    // Set the progress and color segments
    fun setProgress(progress: Int, segments: List<Pair<Int, Int>>) {
        this.progress = progress
        colorSegments.clear()
        colorSegments.addAll(segments)
        invalidate()
    }
}
