package com.example.rompermission.requester.impl.oppo

import android.content.Intent
import com.example.rompermission.requester.impl.RomRequester

class OPPORequester: RomRequester() {
    override fun doAlertWindowRequest(host: Any): Boolean {
        val context = getContext(host)
        return if (!hasAlertWindowPermission(context)) {
            val intent = Intent("com.meizu.safe.security.SHOW_APPSEC");
            false
        } else {
            true
        }
    }
}