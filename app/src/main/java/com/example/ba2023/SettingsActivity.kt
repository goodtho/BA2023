package com.example.ba2023

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import com.example.ba2023.databinding.ActivitySettingsBinding
import com.example.ba2023.model.CountDownModel

class SettingsActivity : Activity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var countDownModel:CountDownModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_settings)

        val isInstanceNull:Boolean= CountDownModel.isInstanceNull
        if(!isInstanceNull) {
             countDownModel = CountDownModel.getInstance()!!
        }
        val finishLearningButton:LinearLayout = findViewById(R.id.finishLearningLinearLayout)
        val resetTimerButton:LinearLayout = findViewById(R.id.resetTimerLinearLayout)
        val timerConfigButton:LinearLayout = findViewById(R.id.timerConfigLinearLayout)
        val statisticsButton:LinearLayout = findViewById(R.id.statisticLinearLayout)


        finishLearningButton.setOnClickListener{
            if (!isInstanceNull) {
                countDownModel.cancel()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        resetTimerButton.setOnClickListener{
            if(!isInstanceNull) {
                countDownModel.cancel()
                countDownModel.start()
            }
        }

        timerConfigButton.setOnClickListener {
            val intent = Intent(this, IntervalSettingActivity::class.java)
            startActivity(intent)
        }

        statisticsButton.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }
    }
}