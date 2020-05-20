package thevoid.iam.keyboardsizedemo

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import iam.thevoid.keyboard.height.Parameters
import iam.thevoid.keyboard.height.rx1.RxKeyboardHeight
import kotlinx.android.synthetic.main.activity_main.*
import rx.Subscription


class MainActivity : AppCompatActivity() {

    private var subscription : Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscription = RxKeyboardHeight.observeKeyboardHeight(this)
                .subscribe(::showHeight) { Log.e("MainActivity", "Error", it)}
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription?.unsubscribe()
    }

    private fun showHeight(parameters: Parameters) {
        Log.i("MainActivity", "$parameters")
        tv.text = "Keyboard height: ${parameters.keyboardHeightPixels} px, ${parameters.keyboardHeightPixels / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT).toInt()} dp"
    }
}
