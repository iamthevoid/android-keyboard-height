package iam.thevoid.keyboard.height.rx1

import android.app.Activity
import iam.thevoid.keyboard.height.KeyboardListener
import iam.thevoid.keyboard.height.Parameters
import rx.Observable
import rx.schedulers.Schedulers
import rx.subjects.BehaviorSubject

object KeyboardHeight {
    private val subject  = BehaviorSubject.create<Parameters>()
    private var keyboardHeight : ApiKeyboardHeight? = null

    fun observeKeyboardHeight(activity: Activity) : Observable<Parameters> {
        keyboardHeight = null
        keyboardHeight = ApiKeyboardHeight(activity, object : KeyboardListener {
            override fun onKeyboardChanged(parameters: Parameters) = subject.onNext(parameters)
        })
        return subject.distinctUntilChanged().subscribeOn(Schedulers.computation())
    }
}