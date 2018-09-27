package com.example.rompermission.requester.impl.miui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class MIUI5Requester : MIUIRequester() {
    override fun applyAlterPermission(context: Context) {
        var intent: Intent? = null
        val packageName = context.packageName
        intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent!!.setData(uri)
        intent!!.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent)
        }
    }
}