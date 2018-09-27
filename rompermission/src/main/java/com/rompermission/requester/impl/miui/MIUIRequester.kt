package com.example.rompermission.requester.impl.miui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.example.rompermission.requester.impl.RomRequester


abstract class MIUIRequester : RomRequester() {

    override fun doAlertWindowRequest(host: Any): Boolean {
        val context = getContext(host)
        return if (!hasAlertWindowPermission(context)) {
            applyAlterPermission(context)
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

    abstract fun applyAlterPermission(context: Context)
}