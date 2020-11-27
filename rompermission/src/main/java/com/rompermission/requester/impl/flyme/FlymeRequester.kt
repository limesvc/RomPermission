package com.rompermission.requester.impl.flyme

import androidx.activity.ComponentActivity
import com.rompermission.requester.impl.RomRequester

abstract class FlymeRequester : RomRequester() {
    override fun doAlertWindowRequest(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?): Boolean {
        return if (!hasAlertWindowPermission(activity)) {
            applyAlterPermission(activity, block)
            false
        } else {
            true
        }
    }

    abstract fun applyAlterPermission(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?)
}
