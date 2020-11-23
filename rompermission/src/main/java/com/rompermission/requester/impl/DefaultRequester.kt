package com.rompermission.requester.impl

import android.Manifest
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rompermission.PermissionCallback
import com.rompermission.activity.PermissionRequestActivity
import com.rompermission.ext.getContext
import com.rompermission.requester.IRomPermissionRequester
import com.rompermission.result.Permission
import java.util.concurrent.BlockingQueue


open class DefaultRequester : IRomPermissionRequester {
    override fun check(host: Any, permissions: Array<Permission>) {
        val context = getContext(host)
        for (p in permissions) {
            if (Manifest.permission.SYSTEM_ALERT_WINDOW == p.permission) {
                p.permitted = hasAlertWindowPermission(context)
            } else {
                p.permitted =hasPermission(context, p.permission)
            }
        }
    }

    private fun hasPermission(context: Context, permission: String): Boolean {
        return when {
            Manifest.permission.SYSTEM_ALERT_WINDOW == permission -> {
                hasAlertWindowPermission(context)
            }
            Manifest.permission.REQUEST_INSTALL_PACKAGES == permission -> {
                hasInstallPackagePermission(context)
            }
            else -> {
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    open fun hasAlertWindowPermission(context: Context): Boolean {
        return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context))
    }

    open fun hasInstallPackagePermission(context: Context): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                context.packageManager.canRequestPackageInstalls()
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                ContextCompat.checkSelfPermission(context, Manifest.permission.REQUEST_INSTALL_PACKAGES) == PackageManager.PERMISSION_GRANTED
            }
            else -> true
        }
    }

    override fun checkAndRequest(host: Any, permissions: Array<Permission>, callback: (Array<Permission>) -> Unit) {
        val context = getContext(host)
        val unPermitted = mutableListOf<String>()
        var floatPermission: Permission? = null

        for (p in permissions) {
            p.permitted = hasPermission(context, p.permission)
            if (!p.permitted) {
                if (Manifest.permission.SYSTEM_ALERT_WINDOW == p.permission) {
                    floatPermission = p
                } else {
                    unPermitted.add(p.permission)
                }
            }
        }

        if (unPermitted.isEmpty() && floatPermission == null) {
            callback.invoke(permissions)
            return
        }

        var total = 0
        var count = 0
        fun verifyDone(activity: ComponentActivity) {
            if (count >= total) {
                callback.invoke(permissions)
            }
            activity.finish()
        }

        PermissionRequestActivity.request(context) { activity ->
            if (floatPermission != null) {
                total++
                doAlertWindowRequest(activity) { permitted ->
                    count++
                    floatPermission.permitted = permitted
                    if (!permitted) floatPermission.toastError(activity)
                    verifyDone(activity)
                }
            }

            if (unPermitted.isNotEmpty()) {
                total++
                activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                    var notToast = true
                    for (p in permissions) {
                        val permitted = it[p.permission]
                        if (permitted != null) {
                            p.permitted = permitted
                            if (notToast && !permitted) {
                                p.toastError(activity)
                                notToast = false
                            }
                        }

                    }
                    count++
                    verifyDone(activity)
                }.launch(unPermitted.toTypedArray())
            }
        }
    }

    protected open fun doCalendarRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    protected open fun doCameraRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    protected open fun doContactsRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    protected open fun doPhoneRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    protected open fun doLocationRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    protected open fun doSensorsRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    protected open fun doSMSRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    protected open fun doStorageRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    /**
     * https://blog.csdn.net/self_study/article/details/52859790
     */
    protected open fun doAlertWindowRequest(activity: ComponentActivity, block:((permitted:Boolean) -> Unit)? = null): Boolean {
        //没有悬浮窗权限,跳转申请
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            block?.invoke(it.resultCode == Activity.RESULT_OK)
        }.launch(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        return true
    }

    private fun doRequest(host: Any, permission: String, requestCode: Int): Boolean {
        if (host is Activity) {
            if (ContextCompat.checkSelfPermission(host, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(host, arrayOf(permission), requestCode)
                return false
            }
        } else if (host is Fragment && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(host.activity, permission) != PackageManager.PERMISSION_GRANTED) {
                host.requestPermissions(arrayOf(permission), requestCode)
                return false
            }
        } else {
            Log.e("DefaultRequester", "Unknown host!!! ")
            return false
        }
        return true
    }

    protected fun getContext(host: Any): Context {
        return host.getContext() ?: throw IllegalArgumentException("Unknown host!!!")
    }
}