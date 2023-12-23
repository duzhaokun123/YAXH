package io.github.duzhaokun123.yaxh.demo

import android.app.Activity
import de.robv.android.xposed.IXposedHookInitPackageResources
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.duzhaokun123.yaxh.init.YAXHInit
import io.github.duzhaokun123.yaxh.utils.argBy
import io.github.duzhaokun123.yaxh.utils.findMethod
import io.github.duzhaokun123.yaxh.utils.getObjectAs
import io.github.duzhaokun123.yaxh.utils.hookAfter
import io.github.duzhaokun123.yaxh.utils.hookBefore
import io.github.duzhaokun123.yaxh.utils.hookBeforeAfter
import io.github.duzhaokun123.yaxh.utils.hookReplace
import io.github.duzhaokun123.yaxh.utils.invoke
import io.github.duzhaokun123.yaxh.utils.invokeMethodAutoAs
import io.github.duzhaokun123.yaxh.utils.loadClass
import io.github.duzhaokun123.yaxh.utils.logger.ALog
import io.github.duzhaokun123.yaxh.utils.logger.XLog
import io.github.duzhaokun123.yaxh.utils.resultBy
import io.github.duzhaokun123.yaxh.utils.setObject
import io.github.duzhaokun123.yaxh.utils.showToast

class XposedInit: IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        YAXHInit.handleLoadPackage(lpparam)
        XLog.tag = "YAXHDemo"
        XLog.v("xlog verbose")
        XLog.d("xlog debug")
        XLog.i("xlog info")
        XLog.w("xlog warn")
        XLog.e("xlog error")

        ALog.tag = "YAXHDemo"
        ALog.v("alog verbose")
        ALog.d("alog debug")
        ALog.i("alog info")
        ALog.w("alog warn")
        ALog.e("alog error")

        val class_MainActivity = loadClass("io.github.duzhaokun123.yaxh.demo.MainActivity")
        class_MainActivity
            .findMethod { name == "hookme" }
            .hookReplace {
                ALog.d("hookme called")
                true
            }
        class_MainActivity
            .findMethod { name == "hookme2" }
            .hookAfter {
                val thiz = it.thisObject as Activity
                val s = thiz.invokeMethodAutoAs<String?>("getSomethingMaybeNull")
                thiz.showToast("hookme2 $s")
            }
        class_MainActivity
            .findMethod { name == "hookme3" }
            .hookBefore {
                val thiz = it.thisObject as Activity
                val str1 = thiz.getObjectAs<String?>("str1")
                val str2 = thiz.getObjectAs<String>("str2")
                thiz.showToast("hookme3 $str1 $str2")
                thiz.setObject("str1", "other")
            }
        class_MainActivity
            .findMethod { name == "hookme4" }
            .hookAfter {
                val thiz = it.thisObject as Activity
                val str1 = thiz(".str2")
                val int1 = thiz(".sum(", 1, 2)
                thiz.showToast("hookme4 $str1 $int1")
            }
        class_MainActivity
            .findMethod { name == "sum" }
            .hookBeforeAfter {
                before {
                    var int1 by it.argBy<Int>(0)
                    var int2 by it.argBy<Int>(1)
                    int1 += 1
                    int2 = int2 * int2
                }
                after {
                    var result by it.resultBy<Int>()
                    result /= 2
                }
            }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {

    }

    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam) {

    }
}