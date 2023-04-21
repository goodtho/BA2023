package com.example.ba2023

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.ba2023.databinding.ActivityIntervallSettingBinding
import com.example.ba2023.model.ConfigModel
import java.util.concurrent.TimeUnit


class IntervalSettingActivity : ScreenActivity() {

    private lateinit var binding: ActivityIntervallSettingBinding
    private var MIN_TIME = 0
    private var MAX_TIME_MS = 59
    private var MAX_TIME_H = 23
    private lateinit var configModel:ConfigModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntervallSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val writingHours: TextView = findViewById(R.id.writing_numberPickerH)
        val writingMinutes: TextView = findViewById(R.id.writing_numberPickerM)
        val writingSeconds: TextView = findViewById(R.id.writing_numberPickerS)

        val pauseHours: TextView = findViewById(R.id.pause_numberPickerH)
        val pauseMinutes: TextView = findViewById(R.id.pause_numberPickerM)
        val pauseSeconds: TextView = findViewById(R.id.pause_numberPickerS)
        val cycle: TextView = findViewById(R.id.interval_Cycle)

        val writingLinearLayout: LinearLayout = findViewById(R.id.writing_linear_layout)
        val pauseLinearLayout: LinearLayout = findViewById(R.id.pause_linear_layout)
        val cycleLinearLayout: LinearLayout = findViewById(R.id.cycle_linear_layout)
        val backButton: ImageView = findViewById(R.id.backButton)

        //set Time defined in config.conf
        configModel = ConfigModel(this)
        getTimeFromConfigAndSetView(writingHours,writingMinutes,writingSeconds,ConfigModel.TIMER_WRITING)
        getTimeFromConfigAndSetView(pauseHours,pauseMinutes,pauseSeconds,ConfigModel.TIMER_PAUSE)
        getCycleAmountFromConfigAndSetView(cycle)

        writingLinearLayout.setOnClickListener{
            callNumberPickerPopup(writingHours, writingMinutes, writingSeconds, writingLinearLayout,ConfigModel.TIMER_WRITING)
        }

        pauseLinearLayout.setOnClickListener{
            callNumberPickerPopup(pauseHours,pauseMinutes,pauseSeconds,pauseLinearLayout,ConfigModel.TIMER_PAUSE)
        }

        cycleLinearLayout.setOnClickListener {
            callNumberPickerPopupCycle(cycle,cycleLinearLayout)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getTimeFromConfigAndSetView(hoursTextView: TextView,minutesTextView: TextView,secondsTextView: TextView,property:String){
        val timeMS = configModel.getProperty(property).toString().toLong()
        val hours: Long = TimeUnit.MILLISECONDS.toHours(timeMS)
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(timeMS) % 60
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(timeMS) % 60

        hoursTextView.text = hours.toString()
        minutesTextView.text = minutes.toString()
        secondsTextView.text = seconds.toString()
    }

    private fun getCycleAmountFromConfigAndSetView(cycleTextView: TextView){
        val cycleAmount = configModel.getProperty(ConfigModel.TIMER_CYCLE).toString()
        cycleTextView.text = cycleAmount
    }
    private fun callNumberPickerPopup(
        textViewHours: TextView,
        textViewMinutes: TextView,
        textViewSeconds: TextView,
        linearLayout: LinearLayout,
        property: String
    ) {
        val popupWindow = PopupWindow(this)

        // Set the content view of the pop-up window
        val popupView: View = LayoutInflater.from(this).inflate(R.layout.numberpicker_popup, null)
        popupWindow.contentView = popupView

        // Get the NumberPickers from the popup view
        val hoursPicker = popupView.findViewById<NumberPicker>(R.id.numberPickerH)
        val minutesPicker = popupView.findViewById<NumberPicker>(R.id.numberPickerM)
        val secondsPicker = popupView.findViewById<NumberPicker>(R.id.numberPickerS)
        val cancelButton: ImageView = popupView.findViewById<ImageView>(R.id.cancel_button)

        // Set the minimum and maximum values for the NumberPickers
        hoursPicker.minValue = MIN_TIME
        hoursPicker.maxValue = MAX_TIME_H
        minutesPicker.minValue = MIN_TIME
        minutesPicker.maxValue = MAX_TIME_MS
        secondsPicker.minValue = MIN_TIME
        secondsPicker.maxValue = MAX_TIME_MS

        // Set the current values of the NumberPickers to the values in the TextViews
        val hours: Int = textViewHours.text.toString().toInt()
        val minutes: Int = textViewMinutes.text.toString().toInt()
        val seconds: Int = textViewSeconds.text.toString().toInt()
        hoursPicker.value = hours
        minutesPicker.value = minutes
        secondsPicker.value = seconds

        // Set the width and height of the pop-up window
        popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT


        // Show the pop-up window at the center of the screen
        popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0)

        cancelButton.setOnClickListener {
            textViewHours.text = hoursPicker.value.toString()
            textViewMinutes.text = minutesPicker.value.toString()
            textViewSeconds.text = secondsPicker.value.toString()
            saveTimeToConfig(textViewHours,textViewMinutes,textViewSeconds,property)
            popupWindow.dismiss()
        }
    }

    private fun saveTimeToConfig(textViewHours: TextView,textViewMinutes: TextView,textViewSeconds: TextView,property: String){
        val hours:Long = textViewHours.text.toString().toLong()
        val minutes:Long = textViewMinutes.text.toString().toLong()
        val seconds:Long = textViewSeconds.text.toString().toLong()

        val ms:Long = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds)

        configModel.updateProperty(property,ms.toString())
    }

    private fun callNumberPickerPopupCycle(
        textViewCycle: TextView,
        linearLayout: LinearLayout
    ) {
        val popupWindow = PopupWindow(this)

        // Set the content view of the pop-up window
        val popupView: View = LayoutInflater.from(this).inflate(R.layout.numberpicker_popup, null)
        popupWindow.contentView = popupView

        // Get the NumberPickers from the popup view
        val hoursPicker = popupView.findViewById<NumberPicker>(R.id.numberPickerH)
        val cyclePicker = popupView.findViewById<NumberPicker>(R.id.numberPickerM)
        val secondsPicker = popupView.findViewById<NumberPicker>(R.id.numberPickerS)
        val cancelButton = popupView.findViewById<ImageView>(R.id.cancel_button)

        hoursPicker.isEnabled = false
        secondsPicker.isEnabled = false


        // Set the minimum and maximum values for the NumberPickers

        cyclePicker.minValue = MIN_TIME
        cyclePicker.maxValue = MAX_TIME_MS

        // Set the current values of the NumberPickers to the values in the TextViews

        val minutes: Int = textViewCycle.text.toString().toInt()

        cyclePicker.value = minutes

        // Set the width and height of the pop-up window
        popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT


        // Show the pop-up window at the center of the screen
        popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0)

        cancelButton.setOnClickListener {
            textViewCycle.text = cyclePicker.value.toString()
            configModel.updateProperty(ConfigModel.TIMER_CYCLE,cyclePicker.value.toString())
            popupWindow.dismiss()
        }
    }
}