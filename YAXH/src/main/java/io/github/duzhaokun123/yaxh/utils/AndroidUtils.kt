package io.github.duzhaokun123.yaxh.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

val mainHandler: Handler by lazy {
    Handler(Looper.getMainLooper())
}

val runtimeProcess: Runtime by lazy {
    Runtime.getRuntime()
}

fun Runnable.postOnMainThread() {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        this.run()
    } else {
        mainHandler.post(this)
    }
}

fun runOnMainThread(runnable: Runnable) {
    runnable.postOnMainThread()
}

fun Context.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) =
    runOnMainThread {
        Toast.makeText(this, msg, length).show()
    }

fun Context.showToast(msg: String, vararg args: Any?, length: Int = Toast.LENGTH_SHORT) =
    runOnMainThread {
        Toast.makeText(this, msg.format(args), length).show()
    }