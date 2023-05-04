import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat
import com.example.ba2023.model.ScreenHandler
import com.example.ba2023.model.WritingController

object WritingStatusManager {
    private var lastScreenChangeTimeStmp = System.currentTimeMillis()
    private val secondsTillThinkingState = 5 * 1000L
    private val secondsTillDisturbedState = 5 * 1000L
    private val secondsTillWritingState = 60 * 1000L
    private var initialState = true
    private var disturbedCounter = 0
    private var secondsInWriting = 0
    private var secondsInThinking = 0
    private var secondsInDisturbed = 0

    private lateinit var vibrator: Vibrator
    private lateinit var writingController: WritingController
    private lateinit var screenHandler: ScreenHandler

    enum class ScreenState {
        WRITING,
        THINKING,
        DISTURBED
    }
    private var screenState = ScreenState.WRITING

    fun initStatusManager(context: Context) {
        writingController = WritingController(context)
        screenHandler = ScreenHandler(context)
        vibrator = ContextCompat.getSystemService(context, Vibrator::class.java) as Vibrator
    }
    fun checkWritingStatus(x: Double, y: Double, z: Double) {
        val currentTime = System.currentTimeMillis()
        writingController.addDataToBuffer(x, y, z)

        if (initialState) {
            if (currentTime - lastScreenChangeTimeStmp >= secondsTillThinkingState) {
                initialState = false
            }
            return
        }

        val isCurrentlyWriting = writingController.isAndroidWatchWritingAverage()

        if (isCurrentlyWriting && currentTime - lastScreenChangeTimeStmp >= secondsTillWritingState) {
            if (screenState != ScreenState.WRITING) {
                updateTimesSpentInStates(currentTime)
                screenState = ScreenState.WRITING
                screenHandler.showWritingActivity()
            }
            lastScreenChangeTimeStmp = currentTime
        } else {
            if (screenState == ScreenState.WRITING) {
                if (currentTime - lastScreenChangeTimeStmp >= secondsTillThinkingState) {
                    updateTimesSpentInStates(currentTime)
                    screenState = ScreenState.THINKING
                    lastScreenChangeTimeStmp = currentTime
                    screenHandler.showThinkingActivity()
                }
            } else {
                if (screenState == ScreenState.THINKING && currentTime - lastScreenChangeTimeStmp >= secondsTillDisturbedState) {
                    updateTimesSpentInStates(currentTime)
                    screenState = ScreenState.DISTURBED
                    disturbedCounter++
                    lastScreenChangeTimeStmp = currentTime
                    screenHandler.showDistractedActivity()
                    vibrateWatch()
                }
            }
        }
    }

    private fun updateTimesSpentInStates(currentTime: Long) {
        val timeSpent = ((currentTime - lastScreenChangeTimeStmp) / 1000).toInt()
        when (screenState) {
            ScreenState.WRITING -> secondsInWriting += timeSpent
            ScreenState.THINKING -> secondsInThinking += timeSpent
            ScreenState.DISTURBED -> secondsInDisturbed += timeSpent
        }
    }

    private fun vibrateWatch() {
        val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)
    }
}