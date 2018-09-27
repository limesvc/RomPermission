package com.rompermission.requester.impl.flyme

import android.content.Context
import com.rompermission.requester.impl.RomRequester

abstract class FlymeRequester : RomRequester() {
    override fun doAlertWindowRequest(host: Any): Boolean {
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
