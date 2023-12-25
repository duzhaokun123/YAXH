package io.github.duzhaokun123.yaxh.utils

import io.github.duzhaokun123.yaxh.init.YAXHContext
import java.lang.reflect.Modifier

fun loadClass(name: String, classLoader: ClassLoader = YAXHContext.classLoader): Class<*> {
    return classLoader.loadClass(name)
}

fun loadClassOrNull(name: String, classLoader: ClassLoader = YAXHContext.classLoader): Class<*>? {
    return runCatching { loadClass(name, classLoader) }.getOrNull()
}

infix fun Class<*>.isSubclassOf(clazz: Class<*>): Boolean {
    return clazz.isAssignableFrom(this)
}

infix fun Class<*>.isSuperclassOf(clazz: Class<*>): Boolean {
    return this.isAssignableFrom(clazz)
}

fun Class<*>.makePublic() {
    this.setObject("accessFlags", this.getObjectAs<Int>("accessFlags") and Modifier.PUBLIC.inv() or Modifier.PUBLIC)
}

fun Class<*>.makeAccessible() {
    this.makePublic()
    this.declaredFields.forEach { it.isAccessible = true }
    this.declaredMethods.forEach { it.isAccessible = true }
    this.declaredConstructors.forEach { it.isAccessible = true }
}