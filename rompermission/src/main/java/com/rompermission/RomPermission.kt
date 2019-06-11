package com.rompermission

import com.rompermission.ext.getContext
import com.rompermission.factory.PermissionRequesterFactory
import com.rompermission.util.RomUtil

object RomPermission {
    @JvmStatic
    fun check(host: Any, permissions: Array<String>): Boolean {
        val romInfo = RomUtil.romInfo
        val requester = PermissionRequesterFactory.instance.getPermissionRequester(romInfo)
        return requester?.check(host, permissions) ?: false
    }

    @JvmStatic
    fun checkAndRequest(host: Any, permissions: Array<String>, messageResId: Int = 0, block: ((success: Boolean) -> Unit)?): Boolean {
        val message = if (messageResId != 0) host.getContext()?.getString(messageResId) else ""
        return checkAndRequest(host, permissions, message, block)
    }

    @JvmStatic
    fun checkAndRequest(host: Any, permissions: Array<String>, message: String?, block: ((success: Boolean) -> Unit)?): Boolean {
        val romInfo = RomUtil.romInfo
        val requester = PermissionRequesterFactory.instance.getPermissionRequester(romInfo)
        var callback: PermissionCallback? = null
        if (block != null) {
            callback = object : PermissionCallback {
                override fun onResult(success: Boolean) {
                    block(success)
                }
            }
        }
        return requester?.checkAndRequest(host, permissions, message, callback) ?: false
    }
}
