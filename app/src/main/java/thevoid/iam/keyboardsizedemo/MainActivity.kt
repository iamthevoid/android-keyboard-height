package thevoid.iam.keyboardsizedemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import thevoid.iam.keyboardsize.KeyboardListener
import thevoid.iam.keyboardsize.KeyboardSize


class MainActivity : AppCompatActivity(), KeyboardListener {
    @SuppressLint("SetTextI18n")
    override fun onKeyboardChanged(height: Int, orientation : Int) {
        tv.text = "Keyboard height: $height px, ${height / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT).toInt()} dp"
    }

    lateinit var text : TextView
    lateinit var field : EditText

    lateinit var keyboardSize : KeyboardSize

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.tv)
        field = findViewById(R.id.et)
        keyboardSize = KeyboardSize(field, this)
        keyboardSize.onCreate()
    }
}
