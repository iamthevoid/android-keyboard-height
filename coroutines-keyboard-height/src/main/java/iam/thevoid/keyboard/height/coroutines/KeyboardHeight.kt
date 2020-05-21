package iam.thevoid.keyboard.height.coroutines

import android.app.Activity
import iam.thevoid.keyboard.height.KeyboardListener
import iam.thevoid.keyboard.height.Parameters
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
object KeyboardHeight : CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private val channel  = ConflatedBroadcastChannel<Parameters>()
    private var keyboardHeight : ApiKeyboardHeight? = null

    fun observeKeyboardHeight(activity: Activity) : Flow<Parameters> {
        keyboardHeight = null
        keyboardHeight = ApiKeyboardHeight(activity, object : KeyboardListener {
            override fun onKeyboardChanged(parameters: Parameters) {
                launch { channel.send(parameters) }
            }
        })
        return channel.asFlow().distinctUntilChanged()
    }
}