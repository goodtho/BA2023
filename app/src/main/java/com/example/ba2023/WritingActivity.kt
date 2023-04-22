package com.example.ba2023

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.ba2023.databinding.ActivityMainBinding
import com.example.ba2023.model.ConfigModel
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

        setUpSettingsClickListener(settingIcon,this.javaClass.name)

        if (CountDownModel.isInstanceNull()
            || CountDownModel.getCaller() == PauseActivity::class.java.name
            || CountDownModel.getCaller() == MainActivity::class.java.name) {
            var configModel = ConfigModel(this)
            var writingTime = configModel.getProperty(ConfigModel.TIMER_WRITING).toLong()
            countDownModel = CountDownModel.initInstance(writingTime, 1000, this)
            countDownModel.start()

        }  else {
            countDownModel = CountDownModel.getInstance()
        }

        CountDownModel.setCaller(this.javaClass.name)
        CountDownModel.setCurrentTextView(timer)
        timer.text = CountDownModel.getTimeMS()

        val thumbsUpIcon: ImageView = findViewById(R.id.startButton)
        thumbsUpIcon.setOnClickListener{
            val intent = Intent(this, ThinkingActivity::class.java)
            startActivity(intent)
        }
    }
}