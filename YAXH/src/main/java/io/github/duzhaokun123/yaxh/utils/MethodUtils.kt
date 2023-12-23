package io.github.duzhaokun123.yaxh.utils

import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import io.github.duzhaokun123.yaxh.init.YAXHContext
import java.lang.reflect.Constructor
import java.lang.reflect.Method

typealias MethodFilter = Method.() -> Boolean

fun Class<*>.findMethodOrNull(
    findSuper: Boolean = false,
    filter: MethodFilter,
): Method? {
    var clazz = this
    clazz.declaredMethods.firstOrNull(filter)?.let { it.isAccessible = true; return it }
    if (findSuper) {
        while (clazz.superclass != null) {
            clazz = clazz.superclass
            clazz.declaredMethods.firstOrNull(filter)?.let { it.isAccessible = true; return it }
        }
    }
    return null
}

fun Class<*>.findMethod(
    findSuper: Boolean = false,
    filter: MethodFilter,
): Method
    = findMethodOrNull(findSuper, filter) ?: throw NoSuchMethodException()

fun Class<*>.findAllMethod(
    findSuper: Boolean = false,
    filter: MethodFilter,
): List<Method> {
    val methods = mutableListOf<Method>()
    var clazz = this
    clazz.declaredMethods.filter(filter).forEach { it.isAccessible = true; methods.add(it) }
    if (findSuper) {
        while (clazz.superclass != null) {
            clazz = clazz.superclass
            clazz.declaredMethods.filter(filter).forEach { it.isAccessible = true; methods.add(it) }
        }
    }
    return methods
}

typealias ConstructorFilter = Constructor<*>.() -> Boolean

fun Class<*>.findConstructor(
    filter: ConstructorFilter,
): Constructor<*>
    = findConstructorOrNull(filter) ?: throw NoSuchMethodException("constructor not found")

fun Class<*>.findConstructorOrNull(
    filter: ConstructorFilter,
): Constructor<*>? {
    return declaredConstructors.firstOrNull(filter)?.apply { isAccessible = true }
}

fun Class<*>.findAllConstructor(
    filter: ConstructorFilter,
): List<Constructor<*>> {
    val constructors = mutableListOf<Constructor<*>>()
    declaredConstructors.filter(filter).forEach { it.isAccessible = true; constructors.add(it) }
    return constructors
}

@JvmInline
value class Args(val args: Array<out Any?>)

@JvmInline
value class ArgTypes(val argTypes: Array<out Class<*>>)

inline fun args(vararg args: Any?) = Args(args)

inline fun argTypes(vararg argTypes: Class<*>) = ArgTypes(argTypes)

fun Any.invokeMethod(
    name: String,
    args: Args = args(),
    argTypes: ArgTypes = argTypes(),
): Any? {
    if (args.args.size != argTypes.argTypes.size) {
        throw IllegalArgumentException("args and argTypes size not match")
    }
    val method = javaClass.findMethodOrNull(true) {
        this.name == name && this.parameterTypes contentEquals argTypes.argTypes
    } ?: throw NoSuchMethodException("$name not found")
    return method.invoke(this, *args.args)
}

fun Any.invokeMethodAuto(
    name: String,
    vararg args: Any?,
): Any? {
    return XposedHelpers.callMethod(this, name, *args)
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.invokeMethodAutoAs(
    name: String,
    vararg args: Any?,
) = invokeMethodAuto(name, *args) as T