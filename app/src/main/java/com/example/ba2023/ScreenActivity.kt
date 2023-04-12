package com.example.ba2023

import android.app.Activity
import android.content.Intent
import android.widget.ImageView

open class ScreenActivity : Activity() {
    fun setUpSettingsClickListener(button: ImageView, caller: String) {
        button.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("caller", caller)
            startActivity(intent)
        }
    }
}