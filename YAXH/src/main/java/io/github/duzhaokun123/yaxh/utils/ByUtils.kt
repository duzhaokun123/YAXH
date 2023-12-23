package io.github.duzhaokun123.yaxh.utils

import de.robv.android.xposed.XC_MethodHook
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class ArgBy<T>(
    private val param: XC_MethodHook.MethodHookParam,
    private val index: Int
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return param.args[index] as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        param.args[index] = value
    }
}

@Suppress("UNCHECKED_CAST")
class ResultBy<T>(
    private val param: XC_MethodHook.MethodHookParam
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return param.result as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        param.result = value
    }
}

fun <T> XC_MethodHook.MethodHookParam.argBy(index: Int): ArgBy<T> {
    return ArgBy(this, index)
}

fun <T> XC_MethodHook.MethodHookParam.resultBy(): ResultBy<T> {
    return ResultBy(this)
}