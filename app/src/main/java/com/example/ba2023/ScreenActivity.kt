package com.example.ba2023

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
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

    fun vibrate(durationMillis: Long) {
        val vibrator = ContextCompat.getSystemService(this, Vibrator::class.java) as Vibrator
        val vibrationEffect = VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)
    }
}