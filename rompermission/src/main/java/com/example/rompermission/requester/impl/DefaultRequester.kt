package com.example.rompermission.requester.impl

import android.Manifest
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.example.rompermission.PermissionCallback
import com.example.rompermission.activity.PermissionRequestActivity

import com.example.rompermission.requester.IRomPermissionRequester
import javax.security.auth.callback.Callback


open class DefaultRequester : IRomPermissionRequester {
    override fun check(host: Any, permissions: Array<String>): Boolean {
        val context = getContext(host)
        for (permission in permissions) {
            if (Manifest.permission.SYSTEM_ALERT_WINDOW == permission) {
                if (!hasAlertWindowPermission(context)) {
                    return false
                }
            } else {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    private fun hasPermission(host: Any, permission: String): Boolean {
        val context = getContext(host)
        if (Manifest.permission.SYSTEM_ALERT_WINDOW == permission) {
            return hasAlertWindowPermission(context)
        } else {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    open fun hasAlertWindowPermission(context: Context): Boolean {
        return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context))
    }

    override fun checkAndRequest(host: Any, permissions: Array<String>, messageResId: Int, callback: PermissionCallback?): Boolean {
        val context = getContext(host)
        val message = context.getString(messageResId)
        return checkAndRequest(host, permissions, message, callback)
    }

    override fun checkAndRequest(host: Any, permissions: Array<String>, message: String, callback: PermissionCallback?): Boolean {
        val dynamicPermission = mutableListOf<String>()

        var needAlertWindow = false;

        for (permission in permissions) {
            if (!Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission)) {
                val granted = hasPermission(host, permission)
                if (!granted) {
                    dynamicPermission.add(permission)
                }
            } else if (!hasPermission(host, Manifest.permission.SYSTEM_ALERT_WINDOW)){
                needAlertWindow = true
            }
        }

        if (dynamicPermission.isEmpty() && !needAlertWindow) {
            callback?.onResult(true)
            return true
        }

        if (dynamicPermission.isEmpty() && needAlertWindow) {
            doAlertWindowRequest(host)
            return false
        }

        val context = getContext(host)
        PermissionRequestActivity.permissionRequest(context, dynamicPermission.toTypedArray(), object : PermissionCallback {
            override fun onResult(success: Boolean) {
                PermissionRequestActivity.clearCallback()

                try {
                    callback?.onResult(success)
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }

                if (!success) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                if (needAlertWindow) {
                    doAlertWindowRequest(host)
                }
            }
        })
        return false
    }

    open protected fun doCalendarRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    open protected fun doCameraRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    open protected fun doContactsRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    open protected fun doPhoneRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    open protected fun doLocationRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    open protected fun doSensorsRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    open protected fun doSMSRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    open protected fun doStorageRequest(host: Any, permission: String, requestCode: Int): Boolean {
        return doRequest(host, permission, requestCode)
    }

    /**
     * https://blog.csdn.net/self_study/article/details/52859790
     */
    open protected fun doAlertWindowRequest(host: Any): Boolean {
        val context = getContext(host)
        if (!hasAlertWindowPermission(context)) {
            //没有悬浮窗权限,跳转申请
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            context.startActivity(intent)
            return false;
        }
        return true
    }

    protected fun doRequest(host: Any, permission: String, requestCode: Int): Boolean {
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
        if (host is Activity) {
            return host
        } else if (host is Fragment) {
            return host.activity
        } else {
            throw IllegalArgumentException("Unknown host!!!")
        }
    }
}