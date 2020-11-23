package com.rompermission.rompermission

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import com.rompermission.RomPermission
import com.rompermission.result.Permission
import com.rompermission.util.RomUtil.getPhoneInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

/**
 * @author wuxi
 * @since 2018/7/12
 */
class MainActivity : ComponentActivity() {
    private val permissions = arrayOf(
            Permission(Manifest.permission.SYSTEM_ALERT_WINDOW, "授权悬浮窗失败"),
            Permission(Manifest.permission.ACCESS_FINE_LOCATION, "授权定位失败"),
            Permission(Manifest.permission.CAMERA, "授权摄像头失败"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPhoneInfo()
    }

    @InternalCoroutinesApi
    fun onCheckOnly(view: View?) {
        RomPermission.check(this@MainActivity, permissions)
        for (p in permissions) {
            Log.d("onRequestAsync", "${p.permission}: ${p.permitted}")
        }
    }

    fun onRequest(view: View?) {
        RomPermission.checkAndRequest(this, permissions) {
            for (p in it) {
                Log.d("onRequest", "${p.permission}: ${p.permitted}")
            }
        }
    }

    fun onRequestAsync(view: View?) {
        GlobalScope.launch {
            val async = RomPermission.checkAndRequestAsync(this@MainActivity, permissions)
            val result = async.await()
            for (p in result) {
                Log.d("onRequestAsync", "${p.permission}: ${p.permitted}")
            }
        }
    }
}