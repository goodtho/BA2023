package com.example.ba2023

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.ba2023.databinding.ActivitySettingsBinding
import com.example.ba2023.model.CountDownModel

class SettingsActivity : Activity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_settings)

        val countDownModel:CountDownModel = CountDownModel.getInstance()
        val backButton:ImageView = findViewById(R.id.backButton)
        val finishLearningButton:LinearLayout = findViewById(R.id.finishLearningLinearLayout)
        val resetTimerButton:LinearLayout = findViewById(R.id.resetTimerLinearLayout)

        //get back to the previous activity
        backButton.setOnClickListener {
            val caller = intent.getStringExtra("caller")
            val callerClass = Class.forName(caller) as Class<*>
            val intent = Intent(this, callerClass)
            startActivity(intent)
        }

        finishLearningButton.setOnClickListener{
            countDownModel.onFinish()
        }

        resetTimerButton.setOnClickListener{
            countDownModel.cancel()
            countDownModel.start()
        }
    }
}