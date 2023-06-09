package com.example.ba2023

import WritingStatusManager
import android.content.Intent
import android.os.Bundle
import com.example.ba2023.databinding.ActivityMainBinding
import com.example.ba2023.model.CountDownModel
import com.example.ba2023.model.CycleUtil
import com.example.ba2023.model.WritingStatusFileWriter

class MainActivity : ScreenActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startButton = binding.startButton
        val settingsButton = binding.settingsIcon

        startButton.setOnClickListener{
            WritingStatusManager.initStatusManager(this)
            WritingStatusManager.setScreenState(WritingStatusManager.ScreenState.WRITING)
            WritingStatusManager.DISTURBED_COUNTER = 0

            //write timestamp to file
            var fileWriter:WritingStatusFileWriter = WritingStatusFileWriter(this)
            fileWriter.writeTimestamp(System.currentTimeMillis())

            //load Cycle for the exercises
            CycleUtil.loadCycleFromConfig(this)
            CountDownModel.caller = this.javaClass.name

            val intent = Intent(this, WritingActivity::class.java)
            startActivity(intent)
        }

        setUpSettingsClickListener(settingsButton,this.javaClass.name)
    }
}