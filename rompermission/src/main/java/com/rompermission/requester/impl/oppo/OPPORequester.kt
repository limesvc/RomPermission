package com.rompermission.requester.impl.oppo

import android.content.Intent
import androidx.activity.ComponentActivity
import com.rompermission.requester.impl.RomRequester

class OPPORequester: RomRequester() {
    override fun doAlertWindowRequest(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?): Boolean {
        val context = getContext(activity)
        return if (!hasAlertWindowPermission(context)) {
            val intent = Intent("com.meizu.safe.security.SHOW_APPSEC");
            false
        } else {
            true
        }
    }
}