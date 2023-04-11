package com.example.ba2023

import android.app.Activity
import android.os.Bundle
import com.example.ba2023.databinding.ActivityMainBinding

class SettingsActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_settings)

    }
}