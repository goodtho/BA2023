package com.example.ba2023

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.ba2023.databinding.ActivityFeedbackBinding


class FeedbackActivity : ScreenActivity() {

    private lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonBack:ImageView = findViewById(R.id.backButton)

        buttonBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}