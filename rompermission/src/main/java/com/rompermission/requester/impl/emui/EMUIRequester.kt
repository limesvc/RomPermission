package com.rompermission.requester.impl.emui

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.rompermission.R
import com.rompermission.requester.impl.RomRequester


/**
 * EMUI4 之后就是 6.0 版本了，按照下面介绍的 6.0 适配方案即可
 */
abstract class EMUIRequester : RomRequester() {
    private val tag = "EMUIRequester"

    override fun doAlertWindowRequest(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?): Boolean {
        return if (!hasAlertWindowPermission(activity)) {
            try {
                val intent = Intent()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                val comp = getComponentName()//悬浮窗管理页面
                intent.component = comp
                activity.startActivity(intent)
            } catch (e: SecurityException) {
                val intent = Intent()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val comp = ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity")//华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
                intent.component = comp
                activity.startActivity(intent)
                Log.e(tag, Log.getStackTraceString(e))
            } catch (e: ActivityNotFoundException) {
                /**
                 * 手机管家版本较低 HUAWEI SC-UL10
                 */
                val intent = Intent()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val comp = ComponentName("com.Android.settings", "com.android.settings.permission.TabItem")//权限管理页面 android4.4
                intent.component = comp
                activity.startActivity(intent)
                e.printStackTrace()
                Log.e(tag, Log.getStackTraceString(e))
            } catch (e: Exception) {
                //抛出异常时提示信息
                Toast.makeText(activity, R.string.rom_permission_fail_setting, Toast.LENGTH_LONG).show()
                Log.e(tag, Log.getStackTraceString(e))
            }
            false
        } else {
            true
        }
    }

    abstract protected fun getComponentName(): ComponentName
}
