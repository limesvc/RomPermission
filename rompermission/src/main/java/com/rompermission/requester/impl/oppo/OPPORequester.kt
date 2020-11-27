package com.rompermission.requester.impl.oppo

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.rompermission.requester.impl.RomRequester

class OPPORequester : RomRequester() {
    override fun doAlertWindowRequest(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?): Boolean {
        val context = getContext(activity)
        return if (!hasAlertWindowPermission(context)) {
            val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                block?.invoke(it.resultCode == Activity.RESULT_OK)
            }.launch(intent)
            false
        } else {
            true
        }
    }
}