package iam.thevoid.keyboard.height

import android.content.res.Configuration

data class Parameters(val keyboardHeightPixels : Int, val screenOrientation : Int) {

    @get:JvmName("isPortrait")
    val isPortrait
        get() = screenOrientation == Configuration.ORIENTATION_PORTRAIT

    @get:JvmName("isKeyboardOnScreen")
    val isKeyboardOnScreen
        get() = keyboardHeightPixels > 0
}