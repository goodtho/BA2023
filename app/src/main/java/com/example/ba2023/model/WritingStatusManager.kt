import android.content.Context
import com.example.ba2023.model.ScreenHandler
import com.example.ba2023.model.WritingController
import com.example.ba2023.model.WritingStatusFileWriter

object WritingStatusManager {
    private var lastScreenChangeTimeStmp = System.currentTimeMillis()
    private const val secondsTillThinkingState = 5 * 1000L
    private const val secondsTillWritingState = 5 * 1000L
    private const val secondsTillDisturbedState = 60 * 1000L

    private var initialState = true
    var DISTURBED_COUNTER = 0
    private var secondsInWriting = 0
    private var secondsInThinking = 0
    private var secondsInDisturbed = 0

    private lateinit var writingController: WritingController
    private lateinit var screenHandler: ScreenHandler
    private lateinit var fileWriter: WritingStatusFileWriter

    enum class ScreenState {
        WRITING,
        THINKING,
        DISTURBED
    }
    private var screenState = ScreenState.WRITING
    private var previousState = ScreenState.WRITING
    fun initStatusManager(context: Context) {
        writingController = WritingController(context)
        screenHandler = ScreenHandler(context)
        fileWriter = WritingStatusFileWriter(context)
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
                    DISTURBED_COUNTER++
                    lastScreenChangeTimeStmp = currentTime
                    screenHandler.showDistractedActivity()
                }
            }
        }
    }

    fun setScreenState(state: ScreenState) {
        screenState = state
    }

     fun updateTimesSpentInStates(currentTime: Long) {
        val timeSpent = ((currentTime - lastScreenChangeTimeStmp) / 1000).toInt()
        println("WritingStatusManager: $previousState -> $screenState")
        println("$screenState time spent: $timeSpent")
        fileWriter.writeStatus(screenState,timeSpent)

    }
}