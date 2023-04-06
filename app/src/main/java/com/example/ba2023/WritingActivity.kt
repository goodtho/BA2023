package com.example.ba2023

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.ba2023.databinding.ActivityMainBinding
import com.example.ba2023.model.CountDownModel

class WritingActivity : ScreenActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownModel:CountDownModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.writing)
        val settingIcon: ImageView = findViewById(R.id.settingsIcon)
        val timer: TextView = findViewById(R.id.timer)

        setUpSettingsClickListener(settingIcon)
        countDownModel = CountDownModel.initInstance(180000, 1000)
        CountDownModel.setCurrentTextView(timer)
        countDownModel.start()
        timer.text = CountDownModel.getTimeMS()
    }
}