package com.example.rompermission

import com.example.rompermission.factory.PermissionRequesterFactory
import com.example.rompermission.util.RomUtil

object RomPermission {
    @JvmStatic
    fun check(host: Any, permissions: Array<String>): Boolean {
        val romInfo = RomUtil.romInfo
        val requester = PermissionRequesterFactory.instance.getPermissionRequester(romInfo)
        return requester!!.check(host, permissions)
    }

    @JvmStatic
    fun checkAndRequest(host: Any, permissions: Array<String>, messageResId: Int, callback: PermissionCallback?): Boolean {
        val romInfo = RomUtil.romInfo
        val requester = PermissionRequesterFactory.instance.getPermissionRequester(romInfo)
        return requester!!.checkAndRequest(host, permissions, messageResId, callback)
    }

    @JvmStatic
    fun checkAndRequest(host: Any, permissions: Array<String>, message: String, callback: PermissionCallback?): Boolean {
        val romInfo = RomUtil.romInfo
        val requester = PermissionRequesterFactory.instance.getPermissionRequester(romInfo)
        return requester!!.checkAndRequest(host, permissions, message, callback)
    }
}
