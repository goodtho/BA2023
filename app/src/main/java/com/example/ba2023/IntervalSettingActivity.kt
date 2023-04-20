package com.example.ba2023

import android.os.Bundle
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.PopupWindow
import android.widget.TextView
import com.example.ba2023.databinding.ActivityIntervallSettingBinding


class IntervalSettingActivity : ScreenActivity() {

    private lateinit var binding: ActivityIntervallSettingBinding
    private var MIN_TIME = 0
    private var MAX_TIME = 60
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntervallSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val writingHours: TextView = findViewById(R.id.writing_numberPickerH)
        val writingMinutes: TextView = findViewById(R.id.writing_numberPickerM)
        val writingSeconds: TextView = findViewById(R.id.writing_numberPickerS)

        val pauseHours: NumberPicker = findViewById(R.id.pause_numberPickerH)
        val pauseMinutes: NumberPicker = findViewById(R.id.pause_numberPickerM)
        val pauseSeconds: NumberPicker = findViewById(R.id.pause_numberPickerS)
        val cycle: NumberPicker = findViewById(R.id.interval_Cycle)

        val writingLinearLayout: LinearLayout = findViewById(R.id.writing_linear_layout)

        writingLinearLayout.setOnClickListener{
            val popupWindow = PopupWindow(this)

            // Set the content view of the pop-up window
            val popupView: View = LayoutInflater.from(this).inflate(R.layout.numberpicker_popup, null)
            popupWindow.contentView = popupView

            // Get the NumberPickers from the popup view
            val hoursPicker = popupView.findViewById<NumberPicker>(R.id.numberPickerH)
            val minutesPicker = popupView.findViewById<NumberPicker>(R.id.numberPickerM)
            val secondsPicker = popupView.findViewById<NumberPicker>(R.id.numberPickerS)

            // Set the minimum and maximum values for the NumberPickers
            hoursPicker.minValue = 0
            hoursPicker.maxValue = 23
            minutesPicker.minValue = 0
            minutesPicker.maxValue = 59
            secondsPicker.minValue = 0
            secondsPicker.maxValue = 59

            // Set the current values of the NumberPickers to the values in the TextViews
            val hours: Int = writingHours.getText().toString().toInt()
            val minutes: Int = writingMinutes.getText().toString().toInt()
            val seconds: Int = writingSeconds.getText().toString().toInt()
            hoursPicker.value = hours
            minutesPicker.value = minutes
            secondsPicker.value = seconds

            // Set the width and height of the pop-up window
            popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
            popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT


            // Show the pop-up window at the center of the screen
            popupWindow.showAtLocation(writingLinearLayout, Gravity.CENTER, 0, 0)
        }

        //setNumberpickerMinMax(writingHours)
        //setNumberpickerMinMax(writingMinutes)
        //setNumberpickerMinMax(writingSeconds)
        setNumberpickerMinMax(pauseHours)
        setNumberpickerMinMax(pauseMinutes)
        setNumberpickerMinMax(pauseSeconds)
        setNumberpickerMinMax(cycle)

    }

    private fun setNumberpickerMinMax(numberPicker: NumberPicker) {
        numberPicker.minValue = MIN_TIME
        numberPicker.maxValue = MAX_TIME
    }
}