package iam.thevoid.keyboard.height

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import iam.thevoid.ae.asActivity
import java.lang.ref.WeakReference


class KeyboardHeight(activity : Activity, private val keyboardListener : KeyboardListener) {

    private val point = Point()
    private val rect = Rect()
    private var last : Parameters? = null

    private var view = activity.window.decorView.rootView
                    .findViewById<View>(android.R.id.content)
                    .let(::WeakReference)

    private val onGlobalLayoutListener =
            ViewTreeObserver.OnGlobalLayoutListener(::handleOnGlobalLayout)

    private val onAttachStateChangeListener =
            object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(p0: View?) {
                    p0?.viewTreeObserver?.removeListener(onGlobalLayoutListener)
                }

                override fun onViewAttachedToWindow(p0: View?)  {
                    view = WeakReference(p0)
                    p0?.viewTreeObserver?.addListener(onGlobalLayoutListener)
                }
            }


    /**
     * The cached landscape height of the keyboard
     */
    private var keyboardLandscapeHeight: Int = 0

    /**
     * The cached portrait height of the keyboard
     */
    private var keyboardPortraitHeight: Int = 0

    init {
        view.get()?.apply {
            viewTreeObserver.addListener(onGlobalLayoutListener)
            removeOnAttachStateChangeListener(onAttachStateChangeListener)
            addOnAttachStateChangeListener(onAttachStateChangeListener)
        }
    }

    private fun handleOnGlobalLayout() {
        val view = this.view.get() ?: return

        val activity = view.context.asActivity()

        activity.windowManager.defaultDisplay.getSize(point)

        view.getWindowVisibleDisplayFrame(rect)

        // REMIND, you may like to change this using the fullscreen size of the phone
        // and also using the status bar and navigation bar heights of the phone to calculate
        // the keyboard height. But this worked fine on a Nexus.
        val keyboardHeight = point.y - rect.bottom
        val orientation = activity.resources.configuration.orientation

        if (keyboardHeight != 0)
            this.keyboardLandscapeHeight = keyboardHeight

        if (last.let { it == null || !it.hasValues(keyboardHeight, orientation) }) {
            val parameters = Parameters(keyboardHeight, orientation)
            keyboardListener.onKeyboardChanged(parameters)
            last = parameters
        }
    }

    @Suppress("DEPRECATION")
    private fun ViewTreeObserver.addListener(listener : ViewTreeObserver.OnGlobalLayoutListener) {
        removeListener(listener)
        addOnGlobalLayoutListener(listener)
    }

    @Suppress("DEPRECATION")
    private fun ViewTreeObserver.removeListener(listener : ViewTreeObserver.OnGlobalLayoutListener) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ->
                removeOnGlobalLayoutListener(listener)
            else ->
                removeGlobalOnLayoutListener(listener)
        }
    }

}