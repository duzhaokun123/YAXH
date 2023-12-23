package io.github.duzhaokun123.yaxh.utils.logger

abstract class ILog {
    enum class Level {
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }

    var tag: String = "YAXH"
    var level: Level = Level.VERBOSE

    fun v(msg: String, t: Throwable? = null) = print(Level.VERBOSE, msg, t)
    fun d(msg: String, t: Throwable? = null) = print(Level.DEBUG, msg, t)
    fun i(msg: String, t: Throwable? = null) = print(Level.INFO, msg, t)
    fun w(msg: String, t: Throwable? = null) = print(Level.WARN, msg, t)
    fun e(msg: String, t: Throwable? = null) = print(Level.ERROR, msg, t)

    fun print(level: Level, msg: String, t: Throwable? = null) {
        if (level < this.level) return
        printInternal(level, msg, t)
    }

    open fun printInternal(level: Level, msg: String, t: Throwable?) {
        println("$tag/$level: $msg")
        t?.printStackTrace()
    }
}