package io.github.duzhaokun123.yaxh.utils

import java.lang.reflect.Field

typealias FieldFilter = Field.() -> Boolean

fun Class<*>.findFieldOrNull(
    findSuper: Boolean = false,
    filter: FieldFilter,
): Field? {
    var clazz = this
    clazz.declaredFields.firstOrNull(filter)?.let { it.isAccessible = true; return it }
    if (findSuper) {
        while (clazz.superclass != null) {
            clazz = clazz.superclass
            clazz.declaredFields.firstOrNull(filter)?.let { it.isAccessible = true; return it }
        }
    }
    return null
}

fun Class<*>.findField(
    findSuper: Boolean = false,
    filter: FieldFilter,
): Field
    = findFieldOrNull(findSuper, filter) ?: throw NoSuchFieldException()

fun Class<*>.findAllField(
    findSuper: Boolean = false,
    filter: FieldFilter,
): List<Field> {
    val fields = mutableListOf<Field>()
    var clazz = this
    clazz.declaredFields.filter(filter).forEach { it.isAccessible = true; fields.add(it) }
    if (findSuper) {
        while (clazz.superclass != null) {
            clazz = clazz.superclass
            clazz.declaredFields.filter(filter).forEach { it.isAccessible = true; fields.add(it) }
        }
    }
    return fields
}

fun Any.getObject(
    name: String,
    type: Class<*>? = null,
): Any? {
    return this.javaClass.findField(true) {
        this.name == name && (type == null || this.type == type)
    }.get(this)
}

fun Any.getObjectOrNull(
    name: String,
    type: Class<*>? = null,
): Any? {
    return runCatching { getObject(name, type) }.getOrNull()
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.getObjectAs(
    name: String,
    type: Class<T>? = null,
): T {
    return getObject(name, type) as T
}

fun Any.setObject(
    name: String,
    value: Any?,
    type: Class<*>? = null,
) {
    this.javaClass.findField(true) {
        this.name == name && (type == null || this.type == type)
    }.set(this, value)
}

fun Any.setObjectSafe(
    name: String,
    value: Any?,
    type: Class<*>? = null,
) {
    runCatching { setObject(name, value, type) }
}