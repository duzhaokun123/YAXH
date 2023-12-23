package io.github.duzhaokun123.yaxh.utils

// 不建议使用 仅用测试想法

/**
 * name 必须以 . 开头
 * name 以 ( 结尾则为方法调用
 *
 * @param name .xxx( or .xxx
 * @return 成员或方法返回值
 */
operator fun Any.invoke(name: String, vararg args: Any?): Any? {
    if (name.startsWith(".").not()) throw IllegalArgumentException("name must start with .")
    return if (name.endsWith("(")) {
        this.invokeMethodAuto(name.substring(1, name.length - 1), *args)
    } else {
        if (args.isNotEmpty()) throw IllegalArgumentException("args must be empty for field")
        this.getObject(name.substring(1))
    }
}