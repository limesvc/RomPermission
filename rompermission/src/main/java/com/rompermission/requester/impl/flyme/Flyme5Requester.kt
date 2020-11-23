package com.rompermission.requester.impl.flyme

import android.content.Context
import android.content.Intent
import com.rompermission.requester.impl.RomRequester

class Flyme5Requester : FlymeRequester() {
    override fun applyAlterPermission(context: Context) {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity")
        intent.putExtra("packageName", context.packageName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}
