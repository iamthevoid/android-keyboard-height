package thevoid.iam.keyboardsize

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun getActivity(context: Context): Activity {
    return when (context) {
        is Activity -> context
        is ContextWrapper -> getActivity(context.baseContext)
        else -> throw IllegalStateException("Context $context NOT contains activity!")
    }
}
//((ContextWrapper) context).getBaseContext()