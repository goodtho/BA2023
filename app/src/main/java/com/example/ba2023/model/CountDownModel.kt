package com.example.ba2023.model

import WritingStatusManager
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ba2023.FinishedActivity
import com.example.ba2023.PauseActivity
import com.example.ba2023.WritingActivity
import com.example.ba2023.model.CycleUtil.getCycle
import com.example.ba2023.model.CycleUtil.setCycle
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class CountDownModel private constructor(
    millisInFuture: Long,
    countDownInterval: Long,
    context: Context
) : CountDownTimer(millisInFuture, countDownInterval) {
    private val contextRef: WeakReference<Context>

    init {
        formatedTime = formatTime(millisInFuture)
        contextRef = WeakReference(context)
    }

    private fun formatTime(millis: Long): String {
        return String.format(
            "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    millis
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    millis
                )
            )
        )
    }

    override fun onTick(l: Long) {
        formatedTime = formatTime(l)
        if (currentTextView != null) {
            currentTextView!!.text = timeMS
        }
    }

    override fun onFinish() {
        var cycle = getCycle()
        var switchActivity: Class<*> = PauseActivity::class.java
        //write last screen state to file
        WritingStatusManager.updateTimesSpentInStates(System.currentTimeMillis())
        //stop the exercise
        if (cycle == 0) {
            switchActivity = FinishedActivity::class.java
        }
        val context = contextRef.get()
        if (caller == PauseActivity::class.java.name) {
            WritingStatusManager.setScreenState(WritingStatusManager.ScreenState.WRITING)
            switchActivity = WritingActivity::class.java
            setCycle(--cycle)
            vibrate(context,500)

        }
        if (context != null) {
            val intent = Intent(context, switchActivity)
            context.startActivity(intent)
        }
    }


    companion object {
        lateinit var formatedTime: String
        private var instance: CountDownModel? = null
        var currentTextView: TextView? = null
        var caller: String? = null
        fun initInstance(
            millisInFuture: Long,
            countDownInterval: Long,
            activity: Context
        ): CountDownModel? {
            instance = CountDownModel(millisInFuture, countDownInterval, activity)
            return instance
        }

        @Throws(Exception::class)
        fun getInstance(): CountDownModel? {
            return if (instance == null) {
                throw Exception("Parameters not initialized. Initiate with initInstance")
            } else {
                instance
            }
        }

        val isInstanceNull: Boolean
            get() = instance == null
        val timeMS: String
            get() = formatedTime.substring(3)
    }
    private fun vibrate(context: Context?,durationMillis: Long) {
        if (context != null) {
            val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java) as Vibrator
            val vibrationEffect = VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        }

    }

}