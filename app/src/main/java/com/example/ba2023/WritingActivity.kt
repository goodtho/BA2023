package com.example.ba2023

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.example.ba2023.databinding.ActivityMainBinding
import com.example.ba2023.model.ConfigModel
import com.example.ba2023.model.CountDownModel

class WritingActivity : ScreenActivity(), SensorEventListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownModel:CountDownModel
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.writing)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

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
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
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
        Log.d(this.javaClass.name, "entered onsensorevent");
        if (event != null) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                WritingStatusManager.checkWritingStatus(this)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}