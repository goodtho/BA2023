package com.example.ba2023

import android.os.Bundle
import android.widget.ImageView
import com.example.ba2023.databinding.ActivityMainBinding

class WritingActivity : ScreenActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.writing)
        val settingIcon: ImageView = findViewById(R.id.settingsIcon)

        setUpSettingsClickListener(settingIcon)
    }
}