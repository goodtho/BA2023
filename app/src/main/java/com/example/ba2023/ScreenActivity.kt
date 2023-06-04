package com.example.ba2023

import android.app.Activity
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageView
import androidx.core.content.ContextCompat

open class ScreenActivity : Activity() {
    fun setUpSettingsClickListener(button: ImageView, caller: String) {
        button.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("caller", caller)
            startActivity(intent)
        }
    }
    fun vibratePauseBeginning() {
        val durationMillis: Long = 500
        val vibrator = ContextCompat.getSystemService(this, Vibrator::class.java) as Vibrator
        val vibrationEffect = VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)
    }

    fun vibrateFinish() {
        val durationMillis: Long = 500
        val pauseMillis: Long = 250

        val vibrator = ContextCompat.getSystemService(this, Vibrator::class.java) as Vibrator
        val timings = longArrayOf(0, durationMillis, pauseMillis, durationMillis)
        val amplitudes = intArrayOf(0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE)
        val vibrationEffect = VibrationEffect.createWaveform(timings, amplitudes, -1)
        vibrator.vibrate(vibrationEffect)
    }

    fun vibrateDistracted() {
        val durationMillis: Long = 500
        val pauseMillis: Long = 250

        val vibrator = ContextCompat.getSystemService(this, Vibrator::class.java) as Vibrator
        val timings = longArrayOf(0, durationMillis, pauseMillis, durationMillis, pauseMillis, durationMillis)
        val amplitudes = intArrayOf(0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE)
        val vibrationEffect = VibrationEffect.createWaveform(timings, amplitudes, -1)
        vibrator.vibrate(vibrationEffect)
    }
}