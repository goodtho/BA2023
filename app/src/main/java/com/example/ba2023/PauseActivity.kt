package com.example.ba2023

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
        val pauseLayout: ConstraintLayout = findViewById(R.id.pauseLayout)
        setUpSettingsClickListener(settingIcon,this.javaClass.name)


        val configModel = ConfigModel(this)
        val pauseTime = configModel.getProperty(ConfigModel.TIMER_PAUSE).toLong()
        countDownModel = CountDownModel.initInstance(pauseTime, 1000, this)!!
        CountDownModel.caller = this.javaClass.name

        CountDownModel.currentTextView = timer
        pauseLayout.setOnClickListener{
            countDownModel.start()
            pauseLayout.setOnClickListener(null)
        }
        timer.text = CountDownModel.timeMS

        vibratePauseBeginning()
    }

    override fun onPause() {
        super.onPause()
        vibrateFinish()
    }
}