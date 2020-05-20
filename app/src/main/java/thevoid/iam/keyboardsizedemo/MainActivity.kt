package thevoid.iam.keyboardsizedemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_main.*
import iam.thevoid.keyboard.height.KeyboardListener
import iam.thevoid.keyboard.height.KeyboardHeight
import iam.thevoid.keyboard.height.Parameters


class MainActivity : AppCompatActivity(), KeyboardListener {
    @SuppressLint("SetTextI18n")
    override fun onKeyboardChanged(parameters : Parameters) {
        tv.text = "Keyboard height: ${parameters.keyboardHeightPixels} px, ${parameters.keyboardHeightPixels / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT).toInt()} dp"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KeyboardHeight(this, this)
    }
}
