package io.github.duzhaokun123.yaxh.utils

import android.content.Context
import io.github.duzhaokun123.yaxh.init.YAXHContext

fun Context.addModuleAssetPath() {
    resources.assets.invokeMethod(
        "addAssetPath",
        args(YAXHContext.modulePath),
        argTypes(String::class.java)
    )
}