package com.rompermission.requester.impl.vivo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Binder
import androidx.activity.ComponentActivity
import com.rompermission.requester.impl.RomRequester

/**
 * VIVO Android_8.1/FunTouch4.0
 * log或dump查看权限管理activity
 * DeviceFileExploler 导出PermissionManager.apk进行分析
 */
class VIVO4Requester : RomRequester() {
    override fun doAlertWindowRequest(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?): Boolean {
        val context = getContext(activity)
        return if (!hasAlertWindowPermission(context)) {
            val intent = Intent("permission.intent.action.softPermissionDetail")
            val componentName = ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity")
            intent.component = componentName
            intent.putExtra("packagename", context.packageName)
            context.startActivity(intent)
            false
        } else {
            true
        }
    }

    protected fun isIntentAvailable(intent: Intent?, context: Context): Boolean {
        return if (intent == null) {
            false
        } else {
            if (context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size > 0) {
                true
            } else {
                throw IllegalArgumentException("intent is not available!")
            }
        }
    }

    override fun hasAlertWindowPermission(context: Context): Boolean {
        val uri = Uri.parse("content://com.vivo.permissionmanager.provider.permission/float_window_apps/#/".plus(Binder.getCallingUid()))

//        "CREATE TABLE float_window_apps (_id INTEGER PRIMARY KEY AUTOINCREMENT,pkgname TEXT NOT NULL,pkguid INTEGER DEFAULT 0,setbyuser INTEGER DEFAULT 0,currentmode INTEGER DEFAULT 0,hasshowed INTEGER DEFAULT 0);"

        val resolver = context.contentResolver
        val cursor = resolver.query(uri, arrayOf("_id", "pkgname", "pkguid", "setbyuser", "currentmode", "hasshowed"), " pkgname = ? ", arrayOf(context.packageName), null)
        return if (cursor.moveToNext()) {
//            val count = cursor.columnCount
//            val names = cursor.columnNames
//            val id = cursor.getString(0)
//            val pkgname = cursor.getString(1)
//            val pkguid = cursor.getString(2)
//            val setbyuser = cursor.getString(3)
            val currentmode = cursor.getString(4)
//            val hasshowed = cursor.getString(5)
            "0".equals(currentmode)
        } else {
            false
        }
    }
}