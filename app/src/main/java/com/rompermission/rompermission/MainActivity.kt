package com.rompermission.rompermission

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import com.rompermission.RomPermission
import com.rompermission.RomPermission.checkAndRequest
import com.rompermission.ext.onComplete
import com.rompermission.result.Permission
import com.rompermission.util.RomUtil.getPhoneInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @author wuxi
 * @since 2018/7/12
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPhoneInfo()
    }

    @InternalCoroutinesApi
    fun onCheckOnly(view: View?) {
        val permissions = arrayOf(Permission(Manifest.permission.SYSTEM_ALERT_WINDOW, "授权悬浮窗失败"))
//        RomPermission.checkAndRequestAsync(this@MainActivity, permissions).onComplete {
//            Log.e("2333333333333", "2333333333333  onComplete $it")
//        }
        val sync = RomPermission.checkAndRequest(this@MainActivity, permissions)
        Log.e("2333333333333", "2333333333333  onComplete $sync")

//        GlobalScope.launch {
//            val async = RomPermission.checkAndRequestAsync(this@MainActivity, permissions)
//            val result = async.await()
//            Log.e("2333333333333", "2333333333333  $result")
//        }
//        Test().testSuspend()
        //        if (RomPermission.check(this, new String[]{permission.CAMERA})) {
//            Log.e("onCheckOnly", "success____233333333");
//        } else {
//            Log.e("onCheckOnly", "failure____233333333");
//        }
    }

    fun onRequest(view: View?) {
        checkAndRequest(this, arrayOf(Manifest.permission.CAMERA), R.string.error_permission) { success: Boolean ->
            if (success) {
                Log.e("onRequest", "success____233333333")
                //                    installMeizuApk();
            } else {
                Log.e("onRequest", "failure____233333333")
            }
            null
        }
    }
}