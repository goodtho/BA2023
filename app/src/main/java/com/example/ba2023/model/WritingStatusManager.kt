import android.content.Context
import com.example.ba2023.model.ScreenHandler
import com.example.ba2023.model.WritingController

object WritingStatusManager {
    private var lastScreenChangeTimeStmp = System.currentTimeMillis()
    private val writingDurationThreshold = 5 * 1000L
    private val thinkingDuration = 60 * 1000L
    private var initialState = true
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
    }
    fun checkWritingStatus(x:Double,y:Double,z:Double) {
        val currentTime = System.currentTimeMillis()

        if (initialState) {
            if (currentTime - lastScreenChangeTimeStmp >= writingDurationThreshold) {
                initialState = false
            }
            return
        }

        writingController.addDataToBuffer(x,y,z)
        val isCurrentlyWriting = writingController.isAndroidWatchWritingAverage()

        if (isCurrentlyWriting) {
            if (screenState != ScreenState.WRITING) {
                screenState = ScreenState.WRITING
                screenHandler.showWritingActivity()
            }
            lastScreenChangeTimeStmp = currentTime
        } else {
            if (screenState == ScreenState.WRITING) {
                if (currentTime - lastScreenChangeTimeStmp >= writingDurationThreshold) {
                    screenState = ScreenState.THINKING
                    lastScreenChangeTimeStmp = currentTime
                    screenHandler.showThinkingActivity()
                }
            } else {
                if (screenState == ScreenState.THINKING && currentTime - lastScreenChangeTimeStmp >= thinkingDuration) {
                    screenState = ScreenState.DISTURBED
                    screenHandler.showDistractedActivity()
                }
            }
        }
    }
}