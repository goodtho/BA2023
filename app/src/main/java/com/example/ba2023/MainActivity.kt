package com.example.ba2023

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.ba2023.databinding.ActivityMainBinding

class MainActivity : ScreenActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startButton = binding.startButton
        val settingsButton = binding.settingsIcon

        startButton.setOnClickListener{
            val intent = Intent(this, WritingActivity::class.java)
            startActivity(intent)
        }

        setUpSettingsClickListener(settingsButton)

    }
}