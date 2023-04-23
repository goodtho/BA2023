package com.example.ba2023.model

import android.content.Context
import android.content.Intent
import com.example.ba2023.*

class ScreenHandler(private val context: Context) {

    fun showDistractedActivity(){
        val intent = Intent(context, DistractedActivity::class.java)
        context.startActivity(intent)
    }

    fun showFeedbackActivity(){
        val intent = Intent(context, FeedbackActivity::class.java)
        context.startActivity(intent)
    }

    fun showFinishedActivity(){
        val intent = Intent(context, FinishedActivity::class.java)
        context.startActivity(intent)
    }

    fun showPauseActivity(){
        val intent = Intent(context, PauseActivity::class.java)
        context.startActivity(intent)
    }

    fun showScreenActivity(){
        val intent = Intent(context, ScreenActivity::class.java)
        context.startActivity(intent)
    }

    fun showSettingsActivity(){
        val intent = Intent(context, SettingsActivity::class.java)
        context.startActivity(intent)
    }

    fun showThinkingActivity(){
        val intent = Intent(context, ThinkingActivity::class.java)
        context.startActivity(intent)
    }

    fun showWritingActivity(){
        val intent = Intent(context, WritingActivity::class.java)
        context.startActivity(intent)
    }

    fun showMainActivity(){
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}