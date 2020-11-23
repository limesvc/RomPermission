package com.rompermission.requester.impl.miui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class MIUI5Requester : MIUIRequester() {
    override fun applyAlterPermission(context: Context) {
        val packageName = context.packageName
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent)
        }
    }
}