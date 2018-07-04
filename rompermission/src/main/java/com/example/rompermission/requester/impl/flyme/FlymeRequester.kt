package com.example.rompermission.requester.impl.flyme

import android.content.Context
import com.example.rompermission.requester.impl.RomRequester

abstract class FlymeRequester : RomRequester() {
    override fun doAlertWindowRequest(host: Any, permission: String, requestCode: Int): Boolean {
        val context = getContext(host)
        return if (!hasAlertWindowPermission(context)) {
            applyAlterPermission(context)
            false
        } else {
            true
        }
    }

    abstract fun applyAlterPermission(context: Context)
}
