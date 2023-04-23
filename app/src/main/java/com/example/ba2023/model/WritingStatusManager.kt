import android.content.Context
import android.util.Log
import com.example.ba2023.model.ScreenHandler
import com.example.ba2023.model.WritingController

object WritingStatusManager {
    private var isLastStateWriting = true
    private var lastWritingTimestamp = System.currentTimeMillis()
    private var lastNotWritingTimestamp = System.currentTimeMillis()
    private val writingDurationThreshold = 5 * 1000L
    private val notWritingDurationThreshold = 60 * 1000L
    private var initialState = true
    private lateinit var writingController: WritingController
    private lateinit var screenHandler: ScreenHandler

    fun initStatusManager(context: Context) {
        writingController = WritingController(context)
        screenHandler = ScreenHandler(context)
    }
    fun checkWritingStatus(x:Double,y:Double,z:Double) {
        val currentTime = System.currentTimeMillis()

        if (initialState) {
            if (currentTime - lastWritingTimestamp >= writingDurationThreshold) {
                initialState = false
            }
            return
        }

        writingController.addDataToBuffer(x,y,z)
        val currentWritingStatus = writingController.isAndroidWatchWritingAverage()

        if (currentWritingStatus) {
            if (!isLastStateWriting) {
                if (currentTime - lastNotWritingTimestamp >= writingDurationThreshold) {
                    isLastStateWriting = true
                    screenHandler.showWritingActivity()
                }
            }
            lastWritingTimestamp = currentTime
        } else {
            if (isLastStateWriting) {
                if (currentTime - lastWritingTimestamp >= writingDurationThreshold) {
                    isLastStateWriting = false
                    screenHandler.showThinkingActivity()
                }
                lastNotWritingTimestamp = currentTime
            } else {
                Log.d(this.javaClass.name, "writingTime  $currentTime - $lastNotWritingTimestamp >= $notWritingDurationThreshold")

                if (currentTime - lastNotWritingTimestamp >= notWritingDurationThreshold) {
                    screenHandler.showDistractedActivity()
                }
            }
        }
    }
}