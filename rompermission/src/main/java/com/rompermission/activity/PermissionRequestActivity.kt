package com.rompermission.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.rompermission.result.Permission

/**
 * @author yuansui
 * @since 2018/6/6
 */
internal class PermissionRequestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback?.invoke(this)
    }

    override fun finish() {
        super.finish()
        callback = null
        overridePendingTransition(0, 0)
    }

    companion object {
        private var callback: ((ComponentActivity) -> Unit)? = null

        fun request(context: Context, callback: (ComponentActivity) -> Unit) {
            this.callback = callback
            val i = Intent(context, PermissionRequestActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(i)
            if (context is Activity) {
                context.overridePendingTransition(0, 0)
            }
        }
    }
}