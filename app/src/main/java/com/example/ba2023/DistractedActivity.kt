package com.example.ba2023

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.example.ba2023.databinding.ActivityDistractedBinding
import com.example.ba2023.databinding.ActivityMainBinding
import com.example.ba2023.databinding.ActivityThinkingBinding
import com.example.ba2023.model.CountDownModel

class DistractedActivity : ScreenActivity(), SensorEventListener {

    private lateinit var binding: ActivityDistractedBinding
    private lateinit var countDownModel:CountDownModel
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        binding = ActivityDistractedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val settingIcon: ImageView = findViewById(R.id.settingsIcon)
        val timer: TextView = findViewById(R.id.timer)

        setUpSettingsClickListener(settingIcon,this.javaClass.name)
        countDownModel = CountDownModel.getInstance()!!
        CountDownModel.currentTextView = timer
        timer.text = CountDownModel.timeMS

        vibrate(500)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val x = event.values[0].toDouble()
                val y = event.values[1].toDouble()
                val z = event.values[2].toDouble()
                WritingStatusManager.checkWritingStatus(x,y,z)            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}