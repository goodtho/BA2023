package com.example.ba2023.model

import android.content.Context

object CycleUtil  {
    private var cycle: Int = 0
    private var cycleLoaded: Boolean = false

    @JvmStatic
    fun  setCycle(newCycle: Int){
        cycle = newCycle
    }

    @JvmStatic
    fun getCycle():Int {
        return cycle
    }

    fun loadCycleFromConfig(context: Context) {
        var configModel = ConfigModel(context)
        cycle = configModel.getProperty(ConfigModel.TIMER_CYCLE).toInt()
        cycleLoaded = false
    }
}