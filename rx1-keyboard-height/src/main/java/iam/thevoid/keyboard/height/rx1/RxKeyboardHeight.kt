package iam.thevoid.keyboard.height.rx1

import android.app.Activity
import iam.thevoid.keyboard.height.KeyboardHeight
import iam.thevoid.keyboard.height.KeyboardListener
import iam.thevoid.keyboard.height.Parameters
import rx.Observable
import rx.schedulers.Schedulers
import rx.subjects.BehaviorSubject

object RxKeyboardHeight {
    private val subject  = BehaviorSubject.create<Parameters>()
    private var keyboardHeight : KeyboardHeight? = null

    fun observeKeyboardHeight(activity: Activity) : Observable<Parameters> {
        keyboardHeight = null
        keyboardHeight = KeyboardHeight(activity, object : KeyboardListener {
            override fun onKeyboardChanged(parameters: Parameters) = subject.onNext(parameters)
        })
        return subject.distinctUntilChanged().subscribeOn(Schedulers.computation())
    }
}