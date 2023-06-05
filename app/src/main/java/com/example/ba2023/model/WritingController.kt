package com.example.ba2023.model

import WritingStatusManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class WritingController(private val writingStatusManager: Context) {
    private val buffer = CircularBuffer(10)

    object WritingValues {
        // add 50% threshold
        private const val increase = 1.5
        private const val decrease = 0.5
        object X {
            const val Min = -0.9 * increase
            const val Max = 1.04 * increase
        }
        object Y {
            const val Min = -10.31 * increase
            const val Max = -7.62 * decrease
        }
        object Z {
            const val Min = 2.21 * decrease
            const val Max = 6.62 * increase
        }
    }

    fun isAndroidWatchWritingAverage(): Boolean {
        val pastX = buffer.getXValues().average()
        val pastY = buffer.getYValues().average()
        val pastZ = buffer.getZValues().average()

        return pastX in WritingValues.X.Min..WritingValues.X.Max &&
                pastY in WritingValues.Y.Min..WritingValues.Y.Max &&
                pastZ in WritingValues.Z.Min..WritingValues.Z.Max
    }

    fun isAndroidWatchWritingPast80Percent(): Boolean {
        val pastX = buffer.getXValues()
        val pastY = buffer.getYValues()
        val pastZ = buffer.getZValues()

        // 80% of the values should be in the range
        val threshold = pastX.size * 0.8

        val validCount = pastX.indices.count { i ->
            val xInRange = pastX[i] > WritingValues.X.Min && pastX[i] < WritingValues.X.Max
            val yInRange = pastY[i] > WritingValues.Y.Min && pastY[i] < WritingValues.Y.Max
            val zInRange = pastZ[i] > WritingValues.Z.Min && pastZ[i] < WritingValues.Z.Max

            xInRange && yInRange && zInRange
        }

        return validCount >= threshold
    }

    fun addDataToBuffer(x:Double, y:Double, z:Double){
        buffer.addData(x, y, z)
    }

    class CircularBuffer(val capacity: Int) {
        private val xValues = DoubleArray(capacity)
        private val yValues = DoubleArray(capacity)
        private val zValues = DoubleArray(capacity)
        private var index = 0

        fun addData(x: Double, y: Double, z: Double) {
            xValues[index] = x
            yValues[index] = y
            zValues[index] = z

            index = (index + 1) % capacity
        }

        fun getXValues(): DoubleArray = xValues.copyOf()
        fun getYValues(): DoubleArray = yValues.copyOf()
        fun getZValues(): DoubleArray = zValues.copyOf()
    }
}
