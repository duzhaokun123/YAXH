package io.github.duzhaokun123.yaxh.init

import android.content.res.XModuleResources
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.duzhaokun123.yaxh.utils.logger.ALog
import io.github.duzhaokun123.yaxh.utils.logger.XLog

object YAXHInit {
    fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        YAXHContext.classLoader = lpparam.classLoader
        YAXHContext.hostPackageName = lpparam.packageName
    }

    fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        YAXHContext.modulePath = startupParam.modulePath
        YAXHContext.moduleRes = XModuleResources.createInstance(startupParam.modulePath, null)
    }

    fun setLogTag(tag: String) {
        ALog.tag = tag
        XLog.tag = tag
    }
}