package io.github.duzhaokun123.yaxh.utils.invokeas

import io.github.duzhaokun123.yaxh.utils.getObjectAs
import io.github.duzhaokun123.yaxh.utils.invokeMethodAutoAs

/**
 * same as [io.github.duzhaokun123.yaxh.utils.invoke] but return [T]
 */
operator fun <T> Any.invoke(name: String, vararg args: Any?): T {
    if (name.startsWith(".").not()) throw IllegalArgumentException("name must start with .")
    return if (name.endsWith("(")) {
        this.invokeMethodAutoAs(name.substring(1, name.length - 1), *args)
    } else {
        if (args.isNotEmpty()) throw IllegalArgumentException("args must be empty for field")
        this.getObjectAs(name.substring(1))
    }
}