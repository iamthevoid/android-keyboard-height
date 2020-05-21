package iam.thevoid.keyboard.height.rx3

import android.app.Activity
import iam.thevoid.keyboard.height.KeyboardListener
import iam.thevoid.keyboard.height.Parameters
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.schedulers.Schedulers

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