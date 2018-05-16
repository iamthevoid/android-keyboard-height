package thevoid.iam.keyboardsize

import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.view.View


class KeyboardSize(private val popupView : View, private val keyboardListener : KeyboardListener) {

    /**
     * The cached landscape height of the keyboard
     */
    private var keyboardLandscapeHeight: Int = 0

    /**
     * The cached portrait height of the keyboard
     */
    private var keyboardPortraitHeight: Int = 0

    fun onCreate() {
        popupView.viewTreeObserver.addOnGlobalLayoutListener({
            handleOnGlobalLayout()
        })
    }

    private fun handleOnGlobalLayout() {
        val activity = getActivity(popupView.context)

        val screenSize = Point()

        activity.windowManager.defaultDisplay.getSize(screenSize)

        val rect = Rect()
        popupView.getWindowVisibleDisplayFrame(rect)

        // REMIND, you may like to change this using the fullscreen size of the phone
        // and also using the status bar and navigation bar heights of the phone to calculate
        // the keyboard height. But this worked fine on a Nexus.
        val orientation = activity.resources.configuration.orientation
        val keyboardHeight = screenSize.y - rect.bottom

        when {
            keyboardHeight == 0 -> notifyKeyboardHeightChanged(0, orientation)
            orientation == Configuration.ORIENTATION_PORTRAIT -> {
                this.keyboardPortraitHeight = keyboardHeight
                notifyKeyboardHeightChanged(keyboardPortraitHeight, orientation)
            }
            else -> {
                this.keyboardLandscapeHeight = keyboardHeight
                notifyKeyboardHeightChanged(keyboardLandscapeHeight, orientation)
            }
        }
    }

    private fun notifyKeyboardHeightChanged(height: Int, orientation: Int) {
        keyboardListener.onKeyboardChanged(height, orientation)
    }
}