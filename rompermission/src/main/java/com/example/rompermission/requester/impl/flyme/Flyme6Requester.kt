package com.example.rompermission.requester.impl.flyme

import android.content.Context
import android.content.Intent
import com.example.rompermission.requester.impl.RomRequester

class Flyme6Requester : FlymeRequester() {
    override fun applyAlterPermission(context: Context) {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", context.getPackageName());
        context.startActivity(intent);
    }
}
