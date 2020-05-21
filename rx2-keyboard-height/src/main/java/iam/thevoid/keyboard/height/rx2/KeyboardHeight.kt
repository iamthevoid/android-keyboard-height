package iam.thevoid.keyboard.height.rx2

import android.app.Activity
import iam.thevoid.keyboard.height.KeyboardListener
import iam.thevoid.keyboard.height.Parameters
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers

object KeyboardHeight {
    private val subject  = BehaviorProcessor.create<Parameters>()
    private var keyboardHeight : ApiKeyboardHeight? = null

    fun observeKeyboardHeight(activity: Activity) : Flowable<Parameters> {
        keyboardHeight = null
        keyboardHeight = ApiKeyboardHeight(activity, object : KeyboardListener {
            override fun onKeyboardChanged(parameters: Parameters) = subject.onNext(parameters)
        })
        return subject.distinctUntilChanged().subscribeOn(Schedulers.computation())
    }
}