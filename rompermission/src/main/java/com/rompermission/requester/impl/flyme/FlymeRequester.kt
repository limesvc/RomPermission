package com.rompermission.requester.impl.flyme

import android.content.Context
import androidx.activity.ComponentActivity
import com.rompermission.requester.impl.RomRequester

abstract class FlymeRequester : RomRequester() {
    override fun doAlertWindowRequest(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?): Boolean {
        return if (!hasAlertWindowPermission(activity)) {
            applyAlterPermission(activity)
            false
        } else {
            true
        }
    }

    abstract fun applyAlterPermission(context: Context)
}
