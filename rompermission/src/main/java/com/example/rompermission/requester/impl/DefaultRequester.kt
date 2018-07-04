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

import com.example.rompermission.requester.IRomPermissionRequester


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

    open fun hasAlertWindowPermission(context: Context): Boolean {
        return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context))
    }

    override fun checkAndRequest(host: Any, permissions: Array<String>, requestCode: Int): Boolean {
        var success = true
        for (permission in permissions) {
            when (permission) {
            //  Calendar
                Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR -> success = success and doCalendarRequest(host, permission, requestCode)

            //  Camera
                Manifest.permission.CAMERA -> success = success and doCameraRequest(host, permission, requestCode)

            //  Contacts
                Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS -> success = success and doContactsRequest(host, permission, requestCode)

            //  Location
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> success = success and doLocationRequest(host, permission, requestCode)

            //  Phone
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.ADD_VOICEMAIL, Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS -> success = success and doPhoneRequest(host, permission, requestCode)

            //  Sensors
                Manifest.permission.BODY_SENSORS -> success = success and doSensorsRequest(host, permission, requestCode)

            //  SMS
                Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS -> success = success and doSMSRequest(host, permission, requestCode)

            //  Storage
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE -> success = success and doStorageRequest(host, permission, requestCode)

            //  FloatWindow
                Manifest.permission.SYSTEM_ALERT_WINDOW -> success = success and doAlertWindowRequest(host, permission, requestCode)
            }

        }

        return success
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
    open protected fun doAlertWindowRequest(host: Any, permission: String, requestCode: Int): Boolean {
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