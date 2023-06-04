package com.example.ba2023

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.ba2023.databinding.ActivityFinishedBinding
import com.example.ba2023.databinding.ActivityPauseBinding


class FinishedActivity : ScreenActivity() {

    private lateinit var binding: ActivityFinishedBinding
    private val delayTime = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vibrateFinish()

        binding = ActivityFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
            finish()
        }, delayTime)
    }
}