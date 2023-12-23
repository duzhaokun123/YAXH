package io.github.duzhaokun123.yaxh.utils.logger

import de.robv.android.xposed.XposedBridge

object XLog : ILog() {
    override fun printInternal(level: Level, msg: String, t: Throwable?) {
        XposedBridge.log("$tag/$level: $msg")
        t?.let { XposedBridge.log(it) }
    }
}