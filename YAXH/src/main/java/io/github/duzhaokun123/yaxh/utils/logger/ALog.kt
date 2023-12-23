package io.github.duzhaokun123.yaxh.utils.logger

import android.util.Log

object ALog: ILog() {
    override fun printInternal(level: Level, msg: String, t: Throwable?) {
        if (t != null) {
            when (level) {
                Level.VERBOSE -> Log.v(tag, msg, t)
                Level.DEBUG -> Log.d(tag, msg, t)
                Level.INFO -> Log.i(tag, msg, t)
                Level.WARN -> Log.w(tag, msg, t)
                Level.ERROR -> Log.e(tag, msg, t)
            }
        } else {
            when (level) {
                Level.VERBOSE -> Log.v(tag, msg)
                Level.DEBUG -> Log.d(tag, msg)
                Level.INFO -> Log.i(tag, msg)
                Level.WARN -> Log.w(tag, msg)
                Level.ERROR -> Log.e(tag, msg)
            }
        }
    }
}