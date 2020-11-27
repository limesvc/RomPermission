package com.rompermission.requester.impl.flyme

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class Flyme5Requester : FlymeRequester() {
    override fun applyAlterPermission(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?) {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity")
        intent.putExtra("packageName", activity.packageName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            block?.invoke(it.resultCode == Activity.RESULT_OK)
        }.launch(intent)
    }
}
