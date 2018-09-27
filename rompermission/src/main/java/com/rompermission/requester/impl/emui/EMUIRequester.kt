package com.rompermission.requester.impl.emui

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.rompermission.requester.impl.RomRequester


/**
 * EMUI4 之后就是 6.0 版本了，按照下面介绍的 6.0 适配方案即可
 */
abstract class EMUIRequester : RomRequester() {
    private val TAG = "EMUIRequester"

    override fun doAlertWindowRequest(host: Any): Boolean {
        val context = getContext(host)
        return if (!hasAlertWindowPermission(context)) {
            try {
                val intent = Intent()
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                val comp = getComponentName()//悬浮窗管理页面
                intent.setComponent(comp)
                context.startActivity(intent)
            } catch (e: SecurityException) {
                val intent = Intent()
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val comp = ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity")//华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
                intent.setComponent(comp)
                context.startActivity(intent)
                Log.e(TAG, Log.getStackTraceString(e))
            } catch (e: ActivityNotFoundException) {
                /**
                 * 手机管家版本较低 HUAWEI SC-UL10
                 */
                val intent = Intent()
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val comp = ComponentName("com.Android.settings", "com.android.settings.permission.TabItem")//权限管理页面 android4.4
                intent.setComponent(comp)
                context.startActivity(intent)
                e.printStackTrace()
                Log.e(TAG, Log.getStackTraceString(e))
            } catch (e: Exception) {
                //抛出异常时提示信息
                Toast.makeText(context, "进入设置页面失败，请手动设置", Toast.LENGTH_LONG).show()
                Log.e(TAG, Log.getStackTraceString(e))
            }
            false
        } else {
            true
        }
    }

    abstract protected fun getComponentName(): ComponentName
}
