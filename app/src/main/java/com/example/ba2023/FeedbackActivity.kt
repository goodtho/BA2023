package com.example.ba2023

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import com.example.ba2023.databinding.ActivityFeedbackBinding
import java.lang.Math.abs


class FeedbackActivity : ScreenActivity() {

    private lateinit var binding: ActivityFeedbackBinding
    private var x1:Float = 0f
    private var x2:Float = 0f
    var y1:Float = 0f
    var y2:Float = 0f

    private val SWIPE_THRESHOLD = 150

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onBackPressed() {
        // Check if user performed a left-to-right swipe
        if (isLeftToRightSwipe()) {
            // Start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // finish current activity
        } else {
            // Call default onBackPressed() method
            super.onBackPressed()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
                y1 = event.y
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                y2 = event.y
                // Check if user performed a left-to-right swipe
                if (isLeftToRightSwipe()) {
                    // Start MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // finish current activity
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun isLeftToRightSwipe(): Boolean {
        if (x2 - x1 > SWIPE_THRESHOLD && kotlin.math.abs(y2 - y1) < SWIPE_THRESHOLD) {
            return true
        }
        return false
    }
}