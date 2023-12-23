package io.github.duzhaokun123.yaxh.utils

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member

typealias Hook = (param: XC_MethodHook.MethodHookParam) -> Unit
typealias ReplaceHook = (param: XC_MethodHook.MethodHookParam) -> Any?

// 污染了 Class 和 Field 的命名空间

fun Member.hook(
    hook: XC_MethodHook,
): XC_MethodHook.Unhook {
    return XposedBridge.hookMethod(this, hook)
}

fun Member.hookBefore(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: Hook,
): XC_MethodHook.Unhook {
    return hook(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam) {
            hook(param)
        }
    })
}

fun Member.hookAfter(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: Hook,
): XC_MethodHook.Unhook {
    return hook(object : XC_MethodHook(priority) {
        override fun afterHookedMethod(param: MethodHookParam) {
            hook(param)
        }
    })
}

fun Member.hookReplace(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: ReplaceHook,
): XC_MethodHook.Unhook {
    return hook(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam) {
            param.result = hook(param)
        }
    })
}

class BeforeAfterHook {
    internal var beforeHook: Hook? = null
    internal var afterHook: Hook? = null

    fun before(hook: Hook) {
        beforeHook = hook
    }

    fun after(hook: Hook) {
        afterHook = hook
    }
}

fun Member.hookBeforeAfter(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: BeforeAfterHook.() -> Unit,
): XC_MethodHook.Unhook {
    val beforeAfterHook = BeforeAfterHook().apply(hook)
    return hook(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam) {
            beforeAfterHook.beforeHook?.invoke(param)
        }

        override fun afterHookedMethod(param: MethodHookParam) {
            beforeAfterHook.afterHook?.invoke(param)
        }
    })
}

fun Iterable<Member>.hook(
    hook: XC_MethodHook,
): List<XC_MethodHook.Unhook> {
    return map { it.hook(hook) }
}

fun Iterable<Member>.hookBefore(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: Hook,
): List<XC_MethodHook.Unhook> {
    return map { it.hookBefore(priority, hook) }
}

fun Iterable<Member>.hookAfter(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: Hook,
): List<XC_MethodHook.Unhook> {
    return map { it.hookAfter(priority, hook) }
}

fun Iterable<Member>.hookReplace(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: ReplaceHook,
): List<XC_MethodHook.Unhook> {
    return map { it.hookReplace(priority, hook) }
}

fun Iterable<Member>.hookBeforeAfter(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: BeforeAfterHook.() -> Unit,
): List<XC_MethodHook.Unhook> {
    return map { it.hookBeforeAfter(priority, hook) }
}

fun Class<*>.hookAllConstructor(
    hook: XC_MethodHook,
): List<XC_MethodHook.Unhook> {
    return declaredConstructors.map { it.hook(hook) }
}

fun Class<*>.hookAllConstructorBefore(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: Hook,
): List<XC_MethodHook.Unhook> {
    return declaredConstructors.map { it.hookBefore(priority, hook) }
}

fun Class<*>.hookAllConstructorAfter(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: Hook,
): List<XC_MethodHook.Unhook> {
    return declaredConstructors.map { it.hookAfter(priority, hook) }
}

fun Class<*>.hookAllConstructorReplace(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: ReplaceHook,
): List<XC_MethodHook.Unhook> {
    return declaredConstructors.map { it.hookReplace(priority, hook) }
}

fun Class<*>.hookAllConstructorBeforeAfter(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: BeforeAfterHook.() -> Unit,
): List<XC_MethodHook.Unhook> {
    return declaredConstructors.map { it.hookBeforeAfter(priority, hook) }
}