package com.rompermission.requester.impl.miui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class MIUI5Requester : MIUIRequester() {
    override fun applyAlterPermission(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?) {
        val packageName = activity.packageName
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (isIntentAvailable(intent, activity)) {
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                block?.invoke(it.resultCode == Activity.RESULT_OK)
            }.launch(intent)
        }
    }
}