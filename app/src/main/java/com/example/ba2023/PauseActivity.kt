package com.example.ba2023

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import com.example.ba2023.databinding.ActivityPauseBinding
import com.example.ba2023.model.ConfigModel
import com.example.ba2023.model.CountDownModel


class PauseActivity : ScreenActivity() {

    private lateinit var binding: ActivityPauseBinding
    private lateinit var countDownModel:CountDownModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPauseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settingIcon: ImageView = findViewById(R.id.settingsIcon)
        val timer: TextView = findViewById(R.id.timer)

        setUpSettingsClickListener(settingIcon,this.javaClass.name)


        var configModel = ConfigModel(this)
        var pauseTime = configModel.getProperty(ConfigModel.TIMER_PAUSE).toLong()
        countDownModel = CountDownModel.initInstance(pauseTime, 1000, this)
        CountDownModel.setCaller(this.javaClass.name)

        CountDownModel.setCurrentTextView(timer)
        countDownModel.start()
        timer.text = CountDownModel.getTimeMS()

    }
}