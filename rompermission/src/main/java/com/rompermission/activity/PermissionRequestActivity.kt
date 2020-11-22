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
//    private val PERMISSION_CODE = 10000
    private var mPermissions: Array<Permission>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback?.invoke(this)
//        requestPermission()
    }

//    private fun requestPermission() {
//        val uncheckPermissions: MutableList<String> = ArrayList()
//        for (p in mPermissions!!) {
//            //权限检测如果未允许，添加至待申请列表
//            if (!p.permitted) {
//                if (p.permission != Manifest.permission.SYSTEM_ALERT_WINDOW) {
//                    uncheckPermissions.add(p.permission)
//                } else {
//
//                }
//            }
//        }
//        if (uncheckPermissions.isNotEmpty()) {
//            val permissions = uncheckPermissions.toTypedArray()
//            callback?.let {
//                registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), it).launch(permissions)
//            }
//        } else {
//            callback?.onActivityResult(mutableMapOf())
//            finish()
//        }
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == PERMISSION_CODE) {
//            var denied = false
//            for (result in grantResults) {
//                // 只要有一个是不通过的, 这一批申请都当做不通过处理
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    denied = true
//                    break
//                }
//            }
//            if (denied) {
//                if (mCallback != null) {
//                    mCallback!!.onResult(false)
//                }
//            } else {
//                if (mCallback != null) {
//                    mCallback!!.onResult(true)
//                }
//            }
//        }
//        finish()
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PERMISSION_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                if (mCallback != null) {
//                    mCallback!!.onResult(true)
//                }
//            } else {
//                if (mCallback != null) {
//                    mCallback!!.onResult(false)
//                }
//            }
//        }
//        finish()
//    }

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
            Log.e("2333333333333333", "startActivity")
            context.startActivity(i)
            if (context is Activity) {
                context.overridePendingTransition(0, 0)
            }
        }
    }
}