import android.content.Context
import android.util.Log
import com.example.ba2023.model.ScreenHandler
import com.example.ba2023.model.WritingController

class WritingStatusManager(private val context: Context) {
    private var writingController = WritingController(this)
    private var isLastStateWriting = true
    private var lastWritingTimestamp = System.currentTimeMillis()
    private var lastNotWritingTimestamp = System.currentTimeMillis()
    private val writingDurationThreshold = 5 * 1000L
    private val notWritingDurationThreshold = 60 * 1000L
    private var initialState = true
    private val screenHandler = ScreenHandler(context)

    init {
        screenHandler.showWritingActivity()
    }

    fun checkWritingStatus() {

        val currentTime = System.currentTimeMillis()
        if (initialState) {
            if (currentTime - lastWritingTimestamp >= writingDurationThreshold) {
                initialState = false
            }
            return
        }

        Log.d("TEST", "Not in initial state")
        val currentWritingStatus = writingController.isAndroidWatchWritingAverage()
        if (currentWritingStatus) {
            if (!isLastStateWriting) {
//                if (currentTime - lastNotWritingTimestamp >= writingDurationThreshold) {
                    isLastStateWriting = true
                    screenHandler.showWritingActivity()
//                }
            }
            lastWritingTimestamp = currentTime
        } else {
            if (isLastStateWriting) {
//                if (currentTime - lastWritingTimestamp >= writingDurationThreshold) {
                    isLastStateWriting = false
                    screenHandler.showThinkingActivity()
//                }
            } else {
//                if (currentTime - lastNotWritingTimestamp >= notWritingDurationThreshold) {
                    screenHandler.showDistractedActivity()
//                }
            }
            lastNotWritingTimestamp = currentTime
        }
    }
}