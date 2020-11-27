package com.rompermission.requester.impl.miui

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class MIUI7Requester : MIUIRequester() {
    override fun applyAlterPermission(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?) {
        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
        intent.putExtra("extra_pkgname", activity.packageName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (isIntentAvailable(intent, activity)) {
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                block?.invoke(it.resultCode == Activity.RESULT_OK)
            }.launch(intent)
        }
    }
}