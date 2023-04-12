package com.example.ba2023

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.ba2023.databinding.ActivityDistractedBinding
import com.example.ba2023.databinding.ActivityMainBinding
import com.example.ba2023.databinding.ActivityThinkingBinding
import com.example.ba2023.model.CountDownModel

class DistractedActivity : ScreenActivity() {

    private lateinit var binding: ActivityDistractedBinding
    private lateinit var countDownModel:CountDownModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDistractedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val settingIcon: ImageView = findViewById(R.id.settingsIcon)
        val timer: TextView = findViewById(R.id.timer)

        setUpSettingsClickListener(settingIcon,this.javaClass.name)
        countDownModel = CountDownModel.getInstance()
        CountDownModel.setCurrentTextView(timer)
        timer.text = CountDownModel.getTimeMS()

        val thumbsUpIcon: ImageView = findViewById(R.id.startButton)
        thumbsUpIcon.setOnClickListener{
            val intent = Intent(this, WritingActivity::class.java)
            startActivity(intent)
        }
    }
}