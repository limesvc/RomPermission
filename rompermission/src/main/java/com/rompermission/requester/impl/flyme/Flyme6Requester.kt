package com.rompermission.requester.impl.flyme

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class Flyme6Requester : FlymeRequester() {
    override fun applyAlterPermission(activity: ComponentActivity, block: ((permitted: Boolean) -> Unit)?) {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", activity.getPackageName());
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            block?.invoke(it.resultCode == Activity.RESULT_OK)
        }.launch(intent)
    }
}
