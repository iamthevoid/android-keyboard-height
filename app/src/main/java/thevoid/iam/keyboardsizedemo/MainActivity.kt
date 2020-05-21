package thevoid.iam.keyboardsizedemo

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import iam.thevoid.keyboard.height.Parameters
import iam.thevoid.keyboard.height.coroutines.KeyboardHeight
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.FlowCollector

@OptIn(InternalCoroutinesApi::class)
class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = launch {
            KeyboardHeight.observeKeyboardHeight(this@MainActivity)
                    .collect(object : FlowCollector<Parameters> {
                        override suspend fun emit(value: Parameters) = showHeight(value)
                    })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun showHeight(parameters: Parameters) {
        Log.i("MainActivity", "$parameters")
        tv.text = "Keyboard height: ${parameters.keyboardHeightPixels} px, ${parameters.keyboardHeightPixels / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT).toInt()} dp"
    }
}
