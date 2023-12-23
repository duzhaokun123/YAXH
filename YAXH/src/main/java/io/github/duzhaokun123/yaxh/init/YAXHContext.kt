package io.github.duzhaokun123.yaxh.init

import android.content.res.Resources

object YAXHContext {
    lateinit var classLoader: ClassLoader
        internal set

    lateinit var hostPackageName: String
        internal set

    lateinit var modulePath: String
        internal set

    lateinit var moduleRes: Resources
        internal set
}